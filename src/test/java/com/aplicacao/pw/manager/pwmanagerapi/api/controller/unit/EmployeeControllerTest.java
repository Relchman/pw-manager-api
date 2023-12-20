package com.aplicacao.pw.manager.pwmanagerapi.api.controller.unit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.aplicacao.pw.manager.pwmanagerapi.api.controller.EmployeeController;
import com.aplicacao.pw.manager.pwmanagerapi.api.http.resources.request.EmployeeRequest;
import com.aplicacao.pw.manager.pwmanagerapi.core.config.ModelMapperConfig;
import com.aplicacao.pw.manager.pwmanagerapi.domain.model.Employee;
import com.aplicacao.pw.manager.pwmanagerapi.domain.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
@Import({ ModelMapperConfig.class })
@ContextConfiguration(classes = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ModelMapper mapper;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private EmployeeRequest employeeRequest;

    @BeforeEach
    public void setup() {
        employee1 = Employee.builder()
                .id(1L)
                .name("employee1")
                .password("pw1")
                .build();

        employee2 = Employee.builder()
                .id(2L)
                .name("employee2")
                .password("pw2")
                .build();

        employee3 = Employee.builder()
                .id(3L)
                .name("employee3")
                .password("pw3")
                .build();

        employee4 = Employee.builder()
                .id(4L)
                .name("employee4")
                .password("pw4")
                .build();

        employee2.setEmployeeSuperior(employee1);

        employee3.setEmployeeSuperior(employee1);

        employee4.setEmployeeSuperior(employee2);

        employeeRequest = EmployeeRequest.builder()
                .name("request1")
                .password("pas$w0rd1")
                .employeeSuperior(1L)
                .build();
    }

    @DisplayName("JUnit: EmployeeController.findByEmployeeSuperiorIsNull()")
    @Test
    public void givenEmployees_whenFindEmployeeSuperiorIsNull_thenReturnEmployeeWithoutSuperior() throws Exception {
        // given
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);

        given(employeeService.findByEmployeeSuperiorIsNull()).willReturn(employees);

        // when
        ResultActions response = mockMvc.perform(get("/employee/findByEmployeeSuperiorIsNull"));

        // then
        response
                .andDo(print()) // imprimindo saída
                .andExpect(jsonPath("$.size()", is(employees.size())));

    }

    @DisplayName("JUnit: EmployeeController.findAll()")
    @Test
    public void givenEmployees_whenFindAll_thenReturnEmployeeList() throws Exception {
        // given
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);

        given(employeeService.findAll()).willReturn(employees);

        // when
        ResultActions response = mockMvc.perform(get("/employee"));

        // then
        response
                .andDo(print()) // imprimindo saída
                .andExpect(jsonPath("$.size()", is(employees.size())));

    }

    @DisplayName("JUnit: EmployeeController.createEmployee()")
    @Test
    public void givenEmployees_whenCreateEmployee_thenReturnEmployee() throws Exception {

        // given
        given(employeeService.create(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));
        given(employeeService.findById(any(Long.class))).willReturn(employee4);

        // when
        ResultActions response = mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(employeeRequest.getName())));

    }

    // JUnit: CidadeController.findById(Long)
    @DisplayName("JUnit: CidadeController.findById(Long)")
    @Test
    public void dadoCidadeId_quandoObterCidadePorId_entaoRetornarObjetoCidade() throws Exception {

        // DADO: pré-condição ou setup
        // cidade.setId(10L);
        // given(cidadeService.findById(cidade.getId())).willReturn(cidade);

        // QUANDO: ação ou comportamento a ser testado
        // ResultActions response = mockMvc.perform(get("/cidade/{id}",
        // cidade.getId()));

        // ENTÃO: verificação das saídas
        // response
        // .andDo(print()) // imprimindo saída
        // .andExpect(jsonPath("$.cidade", is(cidade.getCidade())));

    }
}
