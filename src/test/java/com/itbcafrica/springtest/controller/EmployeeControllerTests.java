package com.itbcafrica.springtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbcafrica.springtest.model.Employee;
import com.itbcafrica.springtest.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee=Employee.builder().id(1L).firstName("Bouendeu").lastName("Raphael").email("Raphael@yahoo.fr").build();

    }

    // Junit test for create Employee REST API
    @DisplayName("Junit test for create Employee REST API ")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        // given-precondition or setup
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));
        // then - verify the output
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName()))).andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName()))).andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    // Junit test for get all employees REST API
    @DisplayName("Junit test for get all employees REST API")
    @Test
    public void givenlistOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given-precondition or setup
        List<Employee> listOfEmployees=new ArrayList<>();
        listOfEmployees.add(Employee.builder().id(1L).firstName("Bouendeu").lastName("Raphael").email("Raphael@yahoo.fr").build());
        listOfEmployees.add(Employee.builder().id(1L).firstName("Bouendeu2").lastName("Raphael2").email("Raphael2@yahoo.fr").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(get("/api/employees"));
        // then - verify the output
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }

    // positiv scenario  --valid employee id
    // Junit test for employee by id REST API
    @DisplayName("Junit test for employee by id REST API")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenEmployeeObject() throws Exception{
        // given-precondition or setup
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(get("/api/employees/{id}", employee.getId()));
        // then - verify the output
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    // Negativ  scenario  --negativ employee id
    // Junit test for employee by invalid id REST API
    @DisplayName("Junit test for employee by invalid id REST API")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenEmployeeObject() throws Exception{
        // given-precondition or setup
        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.empty());
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(get("/api/employees/{id}", employee.getId()));
        // then - verify the output
        response.andExpect(status().isNotFound()).andDo(print());
    }

    // positive scenario
    // Junit test for update employee REST API
    @DisplayName("Junit test for update employee REST API")
    @Test
    public void givenUpdateEmployee_whenUpdateEmployee_thenReturnUpdateEmployee() throws Exception{
        // given-precondition or setup
        Employee savedEmployee=Employee.builder().id(1L).firstName("Bouendeus").lastName("Raphaels").email("Raphaels@yahoo.fr").build();
        Employee updateEmployee=Employee.builder().firstName("Bouendeu").lastName("Raphael").email("Raphael@yahoo.fr").build();
        given(employeeService.getEmployeeById(savedEmployee.getId())).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0));
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateEmployee)));
        // then - verify the output
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.firstName", CoreMatchers.is(updateEmployee.getFirstName()))).andExpect(jsonPath("$.lastName", CoreMatchers.is(updateEmployee.getLastName()))).andExpect(jsonPath("$.email", CoreMatchers.is(updateEmployee.getEmail())));

    }


    // negativ scenario
    // Junit test for negativ update employee REST API
    @DisplayName("Junit test for negative  update employee REST API")
    @Test
    public void givenInvalidUpdateEmployee_whenUpdateEmployee_thenReturnEmpty() throws Exception{
        // given-precondition or setup
        Employee savedEmployee=Employee.builder().id(1L).firstName("Bouendeus").lastName("Raphaels").email("Raphaels@yahoo.fr").build();
        given(employeeService.getEmployeeById(savedEmployee.getId())).willReturn(Optional.empty());
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(get("/api/employees/{id}", savedEmployee.getId()));
        // then - verify the output
        response.andExpect(status().isNotFound()).andDo(print());

    }

    // Junit test for delete employee REST API
    @DisplayName("Junit test for delete employee REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given-precondition or setup
        willDoNothing().given(employeeService).deleteEmployee(employee.getId());
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }

}
