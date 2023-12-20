package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import com.aplicacao.pw.manager.pwmanagerapi.domain.Repository.EmployeeRepository;
import com.aplicacao.pw.manager.pwmanagerapi.domain.exception.NotFoundException;
import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import jakarta.transaction.Transactional;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService{


    private final EmployeeRepository employeeRepository;
    private final PasswordService passwordService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordService passwordService) {
        this.employeeRepository = employeeRepository;
        this.passwordService = passwordService;
    }

    @Override
    @Transactional
    public Employee create(Employee employee) {
        employee.setScore(passwordService.calculatePasswordScore(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee não encontrado."));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee employeeModel = findById(id);
        employeeModel.setName(employee.getName());
        employeeModel.setScore(passwordService.calculatePasswordScore(employee.getPassword()));
        employeeModel.setPassword(employee.getPassword());
        employeeModel.setEmployeeSuperior(employee.getEmployeeSuperior());

        return employeeRepository.save(employeeModel);
    }

    @Override
    public List<Employee> findByEmployeeSuperiorIsNull() {
        return employeeRepository.findByEmployeeSuperiorIsNull();
    }
}
