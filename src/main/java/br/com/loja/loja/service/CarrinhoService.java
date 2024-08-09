package br.com.loja.loja.service;

import br.com.loja.loja.entity.Carrinho;
import br.com.loja.loja.entity.Produto;
import br.com.loja.loja.entity.Usuario;
import br.com.loja.loja.repository.CarrinhoRepository;
import br.com.loja.loja.repository.ProdutoRepository;
import br.com.loja.loja.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Carrinho getCarrinho(Usuario usuario) {
        return carrinhoRepository.findByUsuario(usuario).orElseGet(() -> {
            Carrinho novoCarrinho = new Carrinho();
            novoCarrinho.setUsuario(usuario);
            return carrinhoRepository.save(novoCarrinho);
        });
    }

    public Carrinho adicionarProduto(Usuario usuario, Long produtoId) {
        Carrinho carrinho = getCarrinho(usuario);
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        carrinho.getProdutos().add(produto);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerProduto(Usuario usuario, Long produtoId) {
        Carrinho carrinho = getCarrinho(usuario);
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        carrinho.getProdutos().remove(produto);
        return carrinhoRepository.save(carrinho);
    }
}
