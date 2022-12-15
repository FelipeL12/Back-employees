package com.example.employeebackend.controller;

import com.example.employeebackend.dto.EmployeeDto;
import com.example.employeebackend.entities.Employee;
import com.example.employeebackend.exceptions.BadRequestException;
import com.example.employeebackend.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4201")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRestController {

    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ModelMapper modelMapper) {

        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    private EmployeeDto modelMapperTransform(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    //    CREATE EMPLOYEE
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {

        if (employeeDto == null) {
            throw new BadRequestException("The employee can not be empty");
        }
        var employee = this.modelMapper.map(employeeDto, Employee.class);
        employee = this.employeeService.create(employee);

        return new ResponseEntity<>(this.modelMapperTransform(employee), HttpStatus.CREATED);
    }

    // GET ALL EMPLOYEES
    @GetMapping("/employees")

    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {

        var listEmployee = this.employeeService.getAllEmployees()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listEmployee, HttpStatus.OK);
    }

    //    GET EMPLOYEE BY ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {

        var employee = this.employeeService.searchById(id);
        return new ResponseEntity<>(this.modelMapperTransform(employee), HttpStatus.OK);
    }

    //    UPDATE EMPLOYEE
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {

        var employee = this.modelMapper.map(employeeDto, Employee.class);
        employee = this.employeeService.update(id, employee);

        return new ResponseEntity<>(this.modelMapperTransform(employee), HttpStatus.OK);
    }

    //    DELETE EMPLOYEE
    @DeleteMapping("/employees/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        this.employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
