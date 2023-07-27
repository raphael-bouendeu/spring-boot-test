package com.itbcafrica.springtest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbcafrica.springtest.model.Employee;
import com.itbcafrica.springtest.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTests{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }

    // Junit test for create Employee REST API INTEGRATION
    @DisplayName("Junit test for create Employee REST API INTEGRATION  ")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        // given-precondition or setup
        Employee employee=Employee.builder().firstName("Bouendeu").lastName("Raphael").email("Raphael@yahoo.fr").build();
        // when -action or the behavior that we are going test
        ResultActions response=mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));
        // then - verify the output
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName()))).andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName()))).andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }
}
