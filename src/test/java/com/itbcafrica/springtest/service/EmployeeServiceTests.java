package com.itbcafrica.springtest.service;

import com.itbcafrica.springtest.exception.ResourceNotFoundException;
import com.itbcafrica.springtest.model.Employee;
import com.itbcafrica.springtest.repository.EmployeeRepository;
import com.itbcafrica.springtest.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests{

    private Employee employee;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup(){
        employee=Employee.builder().id(1L).firstName("Bouendeu").lastName("Raphael").email("Raphael@yahoo.fr").build();

        //  employeeRepository=Mockito.mock(EmployeeRepository.class);
        // employeeService=new EmployeeServiceImpl(employeeRepository);
    }

    // Junit test for savedEmployee method
    @DisplayName("Junit test for savedEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given-precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        // when -action or the behavior that we are going test
        Employee savedEmployee=employeeService.saveEmployee(employee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit test for savedEmployee method with throw exception
    @DisplayName("Junit test for savedEmployee method with throw exception")
    @Test
    public void givenExistingEmailEmployeeObject_whenSaveEmployee_thenThrowsException(){
        // given-precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // when -action or the behavior that we are going test
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            employeeService.saveEmployee(employee);
        });
        //then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // Junit test for  getAllEmployees method
    @DisplayName("Junit test for  getAllEmployees method")
    @Test
    public void givenEmployeesList_whengetAllEmployees_thenReturnEmployeesList(){
        // given-precondition or setup
        Employee employee2=Employee.builder().id(1L).firstName("Bouendeu1").lastName("Raphael1").email("Raphael1@yahoo.fr").build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));
        // when -action or the behavior that we are going test
        List<Employee> employeeList=employeeService.getAllEmployees();
        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    // Junit test for  getAllEmployees method(negative scenario)
    @DisplayName("Junit test for  getAllEmployees method(negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whengetAllEmployees_thenReturnEmptyEmployeesList(){
        // given-precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.EMPTY_LIST);
        // when -action or the behavior that we are going test
        List<Employee> employeeList=employeeService.getAllEmployees();
        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }

    // Junit test for get employee by Id
    @DisplayName("Junit test for get employee by Id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given-precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeService.getEmployeeById(employee.getId()).get();
        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for update employee method
    @DisplayName("Junit test for update employee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployee(){
        // given-precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("sergio@yahoo.de");
        employee.setFirstName("fernand");
        // when -action or the behavior that we are going test
        Employee updateEmployee=employeeService.updateEmployee(employee);
        // then - verify the output
        assertThat(updateEmployee.getEmail()).isEqualTo(employee.getEmail());
        assertThat(updateEmployee.getFirstName()).isEqualTo(employee.getFirstName());
    }

    // Junit test for delete Employee method
    @DisplayName("Junit test for delete Employee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given-precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());
        // when -action or the behavior that we are going test
        employeeService.deleteEmployee(1L);
        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

}
