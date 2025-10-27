package com.todo.user.entity;

import jakarta.persistence.*; // Permet d’utiliser les annotations JPA (@Entity, @Table, @Column, etc.)
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;  // Ajoute des comportements automatiques liés à la gestion du temps.
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false,unique=true,length=50)
    private String username;

    @Column(nullable = false,unique= true , length = 100)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(name="first_name", length=50)
    private String firstName;

    @Column(name= "last_name",length = 50)
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive =true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name= "updated_at")
    private  LocalDateTime updatedAt;

    @Column(name= "role")
    private String role = "USER";

}