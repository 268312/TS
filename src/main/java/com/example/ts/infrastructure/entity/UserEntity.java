package com.example.ts.infrastructure.entity;

import com.example.ts.roles.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user", schema = "ksiazki")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "user")
    private List<LoanEntity> loans;

//    @OneToOne
//    private LoginEntity login;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  //  public UserRole getRole() {return role;}

  //  public void setRole(UserRole role) {this.role = role;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
