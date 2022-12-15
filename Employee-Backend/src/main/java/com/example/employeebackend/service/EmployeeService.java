package com.example.employeebackend.service;

import com.example.employeebackend.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee create(Employee employee);

    Employee update(long idEmployee, Employee employee);

    Employee searchById(long idEmployee);

    void delete(long idEmployee);

}
