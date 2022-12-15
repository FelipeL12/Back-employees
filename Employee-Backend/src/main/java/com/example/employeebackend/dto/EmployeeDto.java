package com.example.employeebackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class EmployeeDto {
    private long id;

    @NotNull(message = "This field cant not be empty")

    private String firstName;

    @NotNull(message = "This field cant not be empty")
    private String lastName;

    @NotNull(message = "This field cant not be empty")
    private String email;

    @NotNull(message = "This field cant not be empty")
    private int age;
}
