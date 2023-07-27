package com.itbcafrica.springtest.service.impl;

import com.itbcafrica.springtest.exception.ResourceNotFoundException;
import com.itbcafrica.springtest.model.Employee;
import com.itbcafrica.springtest.repository.EmployeeRepository;
import com.itbcafrica.springtest.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee){
        Optional<Employee> savedEmployee=employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee already exist with given email: "+employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee){
        return employeeRepository.save(updateEmployee);
    }

    @Override
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
