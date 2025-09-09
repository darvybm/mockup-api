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
@Table(name = "mocks_response")
public class MockResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String ruleTrigger;
    private Integer statusCode;

    @Column(length = 60000)
    private String body;

}
