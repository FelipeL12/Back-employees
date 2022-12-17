package com.example.employeebackend.entities;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
@Data
@SQLDelete(sql = "UPDATE employee SET deleted = true WHERE  id_employee=?")
@Where(clause = "deleted=false")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private long idEmployee;
    @Column(name = "first_name")
    @NotNull(message = "This field cant not be empty")
    @Size(min = 1, max = 255, message = "This field is not properly sized")
    private String firstName;
    @Column(name = "last_name")
    @NotNull(message = "This field cant not be empty")
    @Size(min = 1, max = 255, message = "This field is not properly sized")
    private String lastName;
    @Column(name = "email")
    @NotNull(message = "This field cant not be empty")
    @Size(min = 1, max = 255, message = "This field is not properly sized")
    private String email;
    @Column(name = "age")
    @NotNull(message = "This field cant not be empty")
    private int age;

    @Override
    public long getId() {
        return 0;
    }
}
