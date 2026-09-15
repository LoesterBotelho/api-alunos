package com.botelho.loester.api_alunos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlunoRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(
                min = 3,
                max = 50,
                message = "Nome deve possuir entre 3 e 50 caracteres"
        )
        @Pattern(
                regexp = "^[\\p{L}]+(?:[ '-][\\p{L}]+)*$",
                message = "Nome deve conter somente letras, espaços, hífen ou apóstrofo"
        )
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(
                min = 5,
                max = 50,
                message = "E-mail deve possuir entre 5 e 50 caracteres"
        )
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "E-mail deve possuir um formato válido"
        )
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(
                min = 8,
                max = 50,
                message = "Senha deve possuir entre 8 e 50 caracteres"
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])\\S{8,50}$",
                message = "Senha deve conter letra maiúscula, minúscula, número e caractere especial"
        )
        String senha,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser anterior à data atual")
        LocalDate dataNascimento,

        @NotNull(message = "Média é obrigatória")
        @DecimalMin(
                value = "0.0",
                message = "Média não pode ser menor que 0"
        )
        @DecimalMax(
                value = "10.0",
                message = "Média não pode ser maior que 10"
        )
        @Digits(
                integer = 2,
                fraction = 2,
                message = "Média deve possuir no máximo 2 casas decimais"
        )
        Double media

) {
}

