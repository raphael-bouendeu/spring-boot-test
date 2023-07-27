package com.itbcafrica.springtest.controller;

import com.itbcafrica.springtest.model.Employee;
import com.itbcafrica.springtest.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController{

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee( @RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee>getAllEmployees(){
        return  employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId){
        return  employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee){
        return  employeeService.getEmployeeById(employeeId)
                .map(savedEmployee->{
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setEmail(employee.getEmail());
                    savedEmployee.setLastName(employee.getLastName());
                 Employee updateEmployee=   employeeService.updateEmployee(savedEmployee);
                    return  new ResponseEntity<>(updateEmployee,HttpStatus.OK);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){
        employeeService.deleteEmployee(employeeId);
        return  new ResponseEntity<String>("Employee deleted successfully.",HttpStatus.OK);
    }

}
