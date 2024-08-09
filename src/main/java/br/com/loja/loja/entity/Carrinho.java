package br.com.loja.loja.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tb_carrinho")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name="invss", sequenceName = "sq_carrinho", allocationSize = 1)
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    @ManyToMany
    private List<Produto> produtos;


}
