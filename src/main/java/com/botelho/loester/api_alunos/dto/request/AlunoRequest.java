package com.botelho.loester.api_alunos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
        @Size(
                min = 5,
                max = 50,
                message = "E-mail deve possuir entre 5 e 50 caracteres"
        )
        @Email(message = "E-mail inválido")
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
        Double media,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(
                regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
                message = "CPF deve estar no formato 000.000.000-00"
        )
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "^\\(\\d{2}\\) 9\\d{4}-\\d{4}$",
                message = "Telefone deve estar no formato (00) 90000-0000"
        )
        String telefone,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(
                regexp = "^\\d{5}-\\d{3}$",
                message = "CEP deve estar no formato 00000-000"
        )
        String cep,

        @NotNull(message = "Idade é obrigatória")
        @Min(
                value = 0,
                message = "Idade não pode ser menor que 0"
        )
        @Max(
                value = 120,
                message = "Idade não pode ser maior que 120"
        )
        Integer idade,

        @NotNull(message = "Altura é obrigatória")
        @DecimalMin(
                value = "0.5",
                message = "Altura deve ser no mínimo 0.50 metro"
        )
        @DecimalMax(
                value = "2.5",
                message = "Altura deve ser no máximo 2.50 metros"
        )
        @Digits(
                integer = 1,
                fraction = 2,
                message = "Altura deve possuir no máximo 2 casas decimais"
        )
        Double altura,

        @NotNull(message = "Peso é obrigatório")
        @DecimalMin(
                value = "1.0",
                message = "Peso deve ser maior que 0"
        )
        @DecimalMax(
                value = "500.0",
                message = "Peso não pode ser maior que 500 kg"
        )
        @Digits(
                integer = 3,
                fraction = 2,
                message = "Peso deve possuir no máximo 2 casas decimais"
        )
        Double peso,

        @NotNull(message = "Quantidade de dependentes é obrigatória")
        @PositiveOrZero(
                message = "Quantidade de dependentes não pode ser negativa"
        )
        @Max(
                value = 20,
                message = "Quantidade de dependentes não pode ser maior que 20"
        )
        Integer quantidadeDependentes,

        @NotNull(message = "Data de matrícula é obrigatória")
        @PastOrPresent(
                message = "Data de matrícula não pode ser futura"
        )
        LocalDate dataMatricula,

        @Future(
                message = "Data de conclusão deve ser futura"
        )
        LocalDate dataConclusao,

        @Size(
                max = 100,
                message = "Site deve possuir no máximo 100 caracteres"
        )
        @Pattern(
                regexp = "^(https?://).+",
                message = "Site deve iniciar com http:// ou https://"
        )
        String site,

        @Size(
                max = 500,
                message = "Observação deve possuir no máximo 500 caracteres"
        )
        String observacao,

        @NotNull(message = "Campo ativo é obrigatório")
        Boolean ativo

) {
}
