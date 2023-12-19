package com.aplicacao.pw.manager.pwmanagerapi.domain.model;

import com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.response.EmployeeResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "ID_SUPERIOR")
    private Employee employeeSuperior;

    @OneToMany(mappedBy = "employeeSuperior")
    private List<Employee> subordinados;

    public EmployeeResponse toDTO() {
        return EmployeeResponse.builder()
                .id(id)
                .name(name)
                .employeeSuperiorId(employeeSuperior != null ? employeeSuperior.getId() : null)
                .subordinados(subordinados != null ? subordinados.stream().map(Employee::toDTO).toList() : null)
                .build();
    }
    public EmployeeResponse toDTOWithoutSubordinados() {
        return EmployeeResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
