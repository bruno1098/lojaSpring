package br.com.loja.loja.controller;

import br.com.loja.loja.entity.Produto;
import br.com.loja.loja.entity.Usuario;
import br.com.loja.loja.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);


    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = service.todosProdutos();
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterProdutoId(@PathVariable Long id) {
        Produto produto = service.porId(id).orElseThrow(()-> new IllegalArgumentException("Produto nao existente"));;
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        System.out.println("Parametro de busca: " + nome);
        List<Produto> produtos = service.produtoPorNome(nome);
        System.out.println("Produtos encontrados: " + produtos.size());
        produtos.forEach(produto -> System.out.println(produto.getNome()));
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscarPorCategoria")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@RequestParam String categoria) {
        List<Produto> produtos = service.produtoPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("qtdEstoque") Long qtdEstoque,
            @RequestParam("categoria") String categoria,
            @RequestParam("preco") String preco,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setQtdEstoque(qtdEstoque);
        produto.setCategoria(categoria);
        try {
            produto.setPreco(Double.parseDouble(preco));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (imagem != null) {
            try {
                String imagemBase64 = Base64.getEncoder().encodeToString(imagem.getBytes());
                produto.setImagemBase64(imagemBase64);
                logger.info("Imagem recebida: tamanho = " + imagem.getBytes().length + " bytes");
            } catch (IOException e) {
                logger.error("Erro ao processar imagem", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Produto novoProduto = service.criar(produto);
        return ResponseEntity.ok(novoProduto);
    }




    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = service.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarProduto(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }


}
