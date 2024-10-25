package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loja")
@Data
public class LojaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @OneToMany(mappedBy = "lojaId", cascade = CascadeType.ALL)
    private List<ProdutoModel> produtos;
}
