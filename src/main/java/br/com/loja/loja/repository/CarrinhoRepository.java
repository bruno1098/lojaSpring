package br.com.loja.loja.repository;

import br.com.loja.loja.entity.Carrinho;
import br.com.loja.loja.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuario(Usuario usuario);
}
