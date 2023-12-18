package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create(Employee employee);

    Employee findById(Long id);

    List<Employee> findAll();

    void delete(Long id);
}
