package br.com.progic.desafio_api.dto;

import br.com.progic.desafio_api.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UserDTO(
        @JsonProperty("id")
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        @JsonProperty("nome")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        @JsonProperty("email")
        String email,

        @JsonProperty("dataCriacao")
        LocalDateTime createdAt
) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}