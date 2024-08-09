package br.com.loja.loja.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name="invss", sequenceName = "sq_produto", allocationSize = 1)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "qtd_estoque")
    private Long qtdEstoque;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "preco")
    private Double preco;

    @Lob
    @Column(name = "imagem_base64", columnDefinition = "TEXT")
    private String imagemBase64;

}
