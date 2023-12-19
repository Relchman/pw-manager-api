package com.aplicacao.pw.manager.pwmanagerapi.api.controller;

import com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.request.EmployeeRequest;
import com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.response.EmployeeResponse;
import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import com.aplicacao.pw.manager.pwmanagerapi.domain.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/employee")
@Tag(name = "Employee", description = "recursos relacionados ao employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ModelMapper modelMapper;


    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }


   @GetMapping
    @ResponseBody
    @Operation(description = "Buscar employee sem paginação", summary = "Buscar employee sem paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeResponse.class)))
            ),
    })
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        List<Employee> employeesList = employeeService.findAll();
        List<EmployeeResponse> content = employeesList.stream()
                .map(Employee::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(content);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deleta um employee", summary = "Deleta um employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Employee não encontrado")
    })
    public ResponseEntity<List<EmployeeResponse>> delete(@Parameter(description = "Id do employee", required = true)
                                                              @PathVariable(name = "id") Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(description = "Criar employee", summary = "Criar employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest,
                                                           UriComponentsBuilder uriBuilder) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);

        // Se employeeSuperiorId não for nulo, atribua o Employee correspondente
        if (employeeRequest.getEmployeeSuperior() != null) {
            Employee superior = employeeService.findById(employeeRequest.getEmployeeSuperior());
            employee.setEmployeeSuperior(superior);
        }

        Employee employeeSaved = employeeService.create(employee);

        URI uri = uriBuilder.path("/employee/{id}")
                .buildAndExpand(employeeSaved.getId())
                .toUri();

        EmployeeResponse employeeResponse = employeeSaved.toDTOWithoutSubordinados(); // Usando o método ajustado
        return ResponseEntity.created(uri).body(employeeResponse);
    }


    @PutMapping(value = "/{id}")
    @Operation(description = "Editar Employee", summary = "Editar Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<EmployeeResponse> update(@Parameter(description = "Id employee", required = true)
                                                                            @PathVariable(name = "id") Long id,
                                                          @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        // Se employeeSuperiorId não for nulo, atribua o Employee correspondente
        if (employeeRequest.getEmployeeSuperior() != null) {
            Employee superior = employeeService.findById(employeeRequest.getEmployeeSuperior());
            employee.setEmployeeSuperior(superior);
        }
        Employee update = employeeService.update(id, employee);
        EmployeeResponse employeeResponse = update.toDTOWithoutSubordinados(); // Usando o método ajustado
        return ResponseEntity.ok(employeeResponse);
    }

}
