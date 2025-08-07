package com.example.demo.user;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @NotBlank(message = "Username cannot be empty or null")
    private String username;
    @Column( nullable = false)
    @NotBlank(message = "Password cannot be empty or null")
    private String password;
    @Column(unique = true,nullable = false)
    @NotBlank(message = "email cannot be empty or null")
    private String email;
    @Column
    private String  category;
    @Column
    private String role;



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = "USER";

    }
    public User(String username, String password, String email,String Role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role;
    }

    public User() {
        this.role = "User";
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Email='" + email + '\'' +
                ", Category='" + category + '\'' +
                ", Role='" + role + '\'' +
                '}';
    }
}

