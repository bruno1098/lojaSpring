package br.com.loja.loja.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@SequenceGenerator(name="invs", sequenceName = "sq_usuario", allocationSize = 1)
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;
}
