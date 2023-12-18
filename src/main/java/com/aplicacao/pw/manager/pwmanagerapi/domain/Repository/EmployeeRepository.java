package com.aplicacao.pw.manager.pwmanagerapi.domain.Repository;

import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
