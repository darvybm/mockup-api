package com.pucmm.eict.mockupapi.payload.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class MockRequest {

    @NotBlank(message = "El nombre del mock no puede estar en blanco")
    private String name;

    @NotBlank(message = "La descripción del mock no puede estar en blanco")
    private String description;

    @NotBlank(message = "La ruta del endpoint no puede estar en blanco")
    @Pattern(regexp = "^[\\p{L}'-]*$", message = "El endpoint no puede contener espacios ni otros símbolos")
    private String endpoint;

    @NotBlank(message = "El método del mock no puede estar en blanco")
    private String method;

    @Size(max = 60000, message = "La longitud de los headers no puede exceder los 60000 caracteres")
    private String headers;

    @NotNull(message = "El código de estado no puede estar vacío")
    private int statusCode;

    @NotBlank(message = "El tipo de contenido no puede estar en blanco")
    private String contentType;

    @Size(max = 60000, message = "La longitud del cuerpo (body) no puede exceder los 60000 caracteres")
    private String body;

    @NotBlank(message = "El mock debe estar asociado a un proyecto")
    @NotNull(message = "El mock debe estar asociado a un proyecto")
    String projectId;

    private String expirationDate = "year";

    @Min(value = 0, message = "El tiempo de demora debe ser igual o mayor que 0")
    private int delay = 0;

    @NotNull(message = "No se está enviando correctamente la validación para JWT")
    private boolean validateJWT = false;
}
