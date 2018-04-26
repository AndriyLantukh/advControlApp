package com.al.advControlApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE", unique = true)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "USER_APPS")
    @ElementCollection(targetClass=App.class)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserId")
    private Set<App> apps;

    public static final UserRole ADMIN = UserRole.ADMIN;
    public static final UserRole ADOPS = UserRole.ADOPS;
    public static final UserRole PUBLISHER = UserRole.PUBLISHER;

    public enum UserRole {ADMIN, ADOPS , PUBLISHER}

   public User(String username, String email, String password, UserRole userRole) {
        this.name = username;
        this.email = email;
        this.password = password;
        this.role = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", apps=" + apps +
                '}';
    }
}



