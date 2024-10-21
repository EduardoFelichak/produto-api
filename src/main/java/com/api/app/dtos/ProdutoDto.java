package com.api.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoDto {
    private UUID id;

    @NotBlank(message = "Nome precisa ser informado")
    @Size(min = 2, max = 50)
    private String nome;

    @NotBlank(message = "Descrição precisa ser informada")
    private String descricao;

    @NotNull(message = "Preço precisa ser informado")
    @Positive(message = "Preço deve ter valor positivo")
    private Double preco;
}
