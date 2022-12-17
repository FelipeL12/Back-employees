package com.example.employeebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    public abstract long getId();

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate created;

    @Column(name = "modified")
    @LastModifiedDate
    private LocalDate modified;

    private boolean deleted = Boolean.FALSE;
}
