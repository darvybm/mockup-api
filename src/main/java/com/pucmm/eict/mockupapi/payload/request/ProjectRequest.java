package com.pucmm.eict.mockupapi.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
    @Pattern(regexp = "^[\\p{L}'-]*$", message = "El nombre no puede contener espacios ni otros símbolos")
    private String name;

    @NotBlank(message = "La descripción no puede estár en blanco")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String description;
}