package com.pucmm.eict.mockupapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mocks")
public class Mock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(length = 1000) // Ajusta el tamaño según tus necesidades
    private String description;

    private String endpoint;
    private String method;

    @Column(length = 60000)
    private String headers;

    private Integer statusCode;
    private String contentType;

    @Column(length = 60000)
    private String body;

    private String hash;
    private LocalDateTime expirationDate;
    private int delay;
    private boolean validateJWT;
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mock_id")
    private List<MockResponse> responses;

}
