package br.com.loja.loja.service;


import br.com.loja.loja.entity.Produto;
import br.com.loja.loja.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> todosProdutos(){
        return repository.findAll();
    }

    public Optional<Produto> porId(Long id){

        return repository.findById(id);
    }

    public List<Produto> produtoPorNome(String nome){
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> produtoPorCategoria(String categoria){
        return repository.findByCategoria(categoria);
    }
    public Produto criar(Produto produto){

        return repository.save(produto);

    }

    public void apagar(Long id){

        repository.deleteById(id);

    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return repository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoAtualizado.getNome());
                    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                    produtoExistente.setQtdEstoque(produtoAtualizado.getQtdEstoque());
                    produtoExistente.setCategoria(produtoAtualizado.getCategoria());
                    produtoExistente.setPreco(produtoAtualizado.getPreco());
                    if (produtoAtualizado.getImagemBase64() != null) {
                        produtoExistente.setImagemBase64(produtoAtualizado.getImagemBase64());
                    }
                    return repository.save(produtoExistente);
                })
                .orElse(null);
    }

}
