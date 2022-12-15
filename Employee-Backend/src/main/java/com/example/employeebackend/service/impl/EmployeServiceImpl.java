package com.example.employeebackend.service.impl;

import com.example.employeebackend.entities.Employee;
import com.example.employeebackend.exceptions.BadRequestException;
import com.example.employeebackend.exceptions.ObjectNotExistsException;
import com.example.employeebackend.repository.EmployeeRepository;
import com.example.employeebackend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class EmployeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private Validator validator;

    public EmployeServiceImpl(EmployeeRepository employeeRepository, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    private void validate(Employee employee) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        if (!violations.isEmpty()) {
            var sb = new StringBuilder();

            for (ConstraintViolation<Employee> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb, violations);
        }
    }


    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        this.validate(employee);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee update(long idEmployee, Employee employee) {
        this.validate(employee);
        this.searchById(idEmployee);

        if (idEmployee == employee.getIdEmployee()) {
            return this.employeeRepository.save(employee);
        }

        throw new BadRequestException("Employee id does not correspond to the pad");
    }

    @Override
    public Employee searchById(long idEmployee) {
        return this.employeeRepository.findById(idEmployee).orElseThrow(() -> new ObjectNotExistsException("There is no employee with the id: " + idEmployee));
    }

    @Override
    public void delete(long idEmployee) {
        var employee = this.searchById(idEmployee);
        employeeRepository.deleteById(idEmployee);
    }
}
