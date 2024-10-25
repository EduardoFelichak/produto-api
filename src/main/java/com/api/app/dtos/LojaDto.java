package com.api.app.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;
@Data
public class LojaDto {

    private UUID id;

    @NotBlank(message = "Nome da loja é obrigatória")
    private String nome;

    private UUID userId;
}