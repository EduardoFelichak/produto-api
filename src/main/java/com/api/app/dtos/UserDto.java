package com.api.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;

    @NotBlank(message = "Nome precisa ser informado")
    @Size(min = 2, max = 50)
    private String nome;

    @NotBlank(message = "E-mail precisa ser informado")
    @Email
    private String email;

    @NotBlank(message = "Senha precisa ser informada")
    private String pass;
}
