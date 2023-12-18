package com.aplicacao.pw.manager.pwmanagerapi.domain.model;

import jakarta.persistence.*;
import lombok.*;


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

}
