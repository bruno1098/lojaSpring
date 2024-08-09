package br.com.loja.loja.controller;

import br.com.loja.loja.entity.Carrinho;
import br.com.loja.loja.entity.Usuario;
import br.com.loja.loja.service.CarrinhoService;
import br.com.loja.loja.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
@CrossOrigin(origins = "*")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Carrinho> getCarrinho(@RequestParam Long usuarioId) {
        Usuario usuario = usuarioService.pegarId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return ResponseEntity.ok(carrinhoService.getCarrinho(usuario));
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Carrinho> adicionarProduto(@RequestParam Long usuarioId, @RequestParam Long produtoId) {
        Usuario usuario = usuarioService.pegarId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return ResponseEntity.ok(carrinhoService.adicionarProduto(usuario, produtoId));
    }

    @DeleteMapping("/remover")
    public ResponseEntity<Carrinho> removerProduto(@RequestParam Long usuarioId, @RequestParam Long produtoId) {
        Usuario usuario = usuarioService.pegarId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return ResponseEntity.ok(carrinhoService.removerProduto(usuario, produtoId));
    }
}
