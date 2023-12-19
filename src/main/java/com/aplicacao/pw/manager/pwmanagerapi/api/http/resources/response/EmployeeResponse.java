package com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.response;

import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    @Schema(description = "id do employee")
    private Long id;

    @Schema(description = "nome do employee")
    private String name;

    @Schema(description = "employee superior")
    private Long employeeSuperiorId;

    @Schema(description = "employee superior")
    private List<EmployeeResponse> subordinados;
}
