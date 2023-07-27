package com.itbcafrica.springtest.repository;

import com.itbcafrica.springtest.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests{
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee=Employee.builder().firstName("Bouendeu").lastName("Raphael").email("raphael.bouendeu@objective-partner.com").build();
    }

    //
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given -precondiction or setup

        // when -action or the behavior that we are going test
        Employee savedEmployee=employeeRepository.save(employee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for get all employees operation
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        // given-precondition or setup
        Employee employee1=Employee.builder().firstName("John").lastName("Berthol").email("raphael2.bouendeu2@objective-partner.com").build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        // when -action or the behavior that we are going test
        List<Employee> employeeList=employeeRepository.findAll();
        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // Junit test for get employee by id operation
    @DisplayName("Junit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findById(employee.getId()).get();
        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getId()).isNotNull();
    }

    // Junit test for get employee by email operation
    @DisplayName("unit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenEmployeeObject(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findByEmail(employee.getEmail()).get();
        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for for update employee operation
    @DisplayName("Junit test for for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdateEmployee(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employee1DB=employeeRepository.findById(employee.getId()).get();
        employee1DB.setEmail("sergio@yahoo.com");
        employeeRepository.save(employee1DB);
        Employee employee1DBUpdate=employeeRepository.findById(employee.getId()).get();
        // then - verify the output
        assertThat(employee1DBUpdate.getEmail()).isEqualTo(employee1DB.getEmail());
    }

    // Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenReturnEmployee(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeDB=employeeRepository.findById(employee.getId());
        // then - verify the output
        assertThat(employeeDB).isEmpty();
    }

    // Junit test for custom query using JPQL with index
    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenEmployeeObject(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo(employee.getFirstName());
    }

    // Junit test for custom query using JPQL with Named Params
    @DisplayName("Junit test for custom query using JPQL with Named Params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenEmployeeObject(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo(employee.getFirstName());
    }

    // Junit test for custom query using native SQL  WITH index
    @DisplayName("Junit test for custom query using native SQL  WITH index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLIndexParams_thenEmployeeObject(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo(employee.getFirstName());
    }

    // Junit test for custom query using native SQL  WITH named params
    @DisplayName("Junit test for custom query using native SQL  WITH named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenEmployeeObject(){
        // given-precondition or setup
        employeeRepository.save(employee);
        // when -action or the behavior that we are going test
        Employee employeeDB=employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());
        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo(employee.getFirstName());
    }

}
