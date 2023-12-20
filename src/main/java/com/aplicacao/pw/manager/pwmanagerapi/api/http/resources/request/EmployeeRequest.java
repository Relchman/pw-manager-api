package com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.request;

import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @Schema(description = "nome do colaborador")
    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    @Size(min = 3, message = "Nome não pode ter menos que 3 caracteres")
    private String name;

    @Schema(description = "senha do colaborador")
    @NotNull(message = "O senha não pode ser vazio ou nulo")
    private String password;

    @Schema(description = "colaborador superior Id")
    private Long employeeSuperior;

}
