package com.example.softarex_demo_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

/**
 * This class is a base class for entities.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @CreatedDate
    @Column(name = "created")
    @NonNull
    protected Date created;

    @LastModifiedDate
    @Column(name = "updated")
    @NonNull
    protected Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NonNull
    protected Status status;
}
