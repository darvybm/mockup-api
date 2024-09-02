package com.pucmm.eict.mockupapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(length = 1000) // Ajusta el tamaño según tus necesidades
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}