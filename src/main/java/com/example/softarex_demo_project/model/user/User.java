package com.example.softarex_demo_project.model.user;

import com.example.softarex_demo_project.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * This class describes user entities.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Cloneable {
    @Column(name = "username", unique = true)
    @NonNull
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @ElementCollection
    private List<String> roles;
}
