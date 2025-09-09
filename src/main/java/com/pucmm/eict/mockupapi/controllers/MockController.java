package com.pucmm.eict.mockupapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.models.MockResponse;
import com.pucmm.eict.mockupapi.payload.request.MockRequest;
import com.pucmm.eict.mockupapi.services.MockService;
import com.pucmm.eict.mockupapi.services.ProjectService;
import com.pucmm.eict.mockupapi.services.UserService;
import com.pucmm.eict.mockupapi.utils.HashGenerator;
import com.pucmm.eict.mockupapi.utils.TokenGenerator;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/mocks")
public class MockController {

    private final MockService mockService;
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public MockController(MockService mockService, ProjectService projectService, UserService userService) {
        this.mockService = mockService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllMocks(Model model) {
        List<Mock> mocks = mockService.getAllMocksByUserId(userService.getAuthenticatedUser().getId());
        model.addAttribute("mocks", mocks);
        model.addAttribute("isAdmin", false);
        model.addAttribute("activePage", "mock");
        return "mock/list";
    }

    @GetMapping("/{id}")
    public String getMockById(@PathVariable UUID id, Model model) {
        Mock mock = mockService.getMockById(id);
        model.addAttribute("mock", mock);
        model.addAttribute("activePage", "mock");
        if(mock != null) {
            model.addAttribute("responseList", mock.getResponses());
        } else {
            model.addAttribute("responseList", new ArrayList<MockResponse>());
        }
        return "mock/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Mock mock = mockService.getMockById(id);
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("mock", mock);
        model.addAttribute("edit", true);
        model.addAttribute("activePage", "mock");
        model.addAttribute("responseList", mock.getResponses());

        List<Map.Entry<String, String>> headersList = parseHeaders(mock.getHeaders());
        model.addAttribute("headersList", headersList);

        return "mock/create";
    }

    private List<Map.Entry<String, String>> parseHeaders(String headersJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserializar el JSON a un Map<String, String>
            Map<String, String> headersMap = objectMapper.readValue(headersJson, new TypeReference<Map<String, String>>() {});
            // Convertir el Map a una lista de entradas (clave, valor)
            return new ArrayList<>(headersMap.entrySet());
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción si hay un problema al parsear los headers
            return Collections.emptyList();
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateMock(@PathVariable UUID id, @Valid @RequestBody MockRequest mockRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        try {
            Mock mock = mockService.getMockById(id);
            mock.setName(mockRequest.getName());
            mock.setDescription(mockRequest.getDescription());
            mock.setEndpoint(mockRequest.getEndpoint());
            mock.setMethod(mockRequest.getMethod().toUpperCase());
            mock.setHeaders(mockRequest.getHeaders());
            mock.setStatusCode(mockRequest.getStatusCode());
            mock.setContentType(mockRequest.getContentType());
            mock.setBody(mockRequest.getBody());
            mock.setExpirationDate(getExpiratonDate(mockRequest));
            mock.setDelay(mockRequest.getDelay());

            if (mock.getProject() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar el proyecto correspondiente");
            }
            
            try
            {
                ObjectMapper objectMapper = new ObjectMapper();
                List<MockResponse> responses = objectMapper.readValue(mockRequest.getResponses(), new TypeReference<List<MockResponse>>(){});
                mock.setResponses(responses);
            } catch(Exception e) {
            }
            
            mockService.createMock(mock);
            System.out.println("MOCK ACTUALIZADO EXITOSAMENTE " + mock);
            System.out.println(ResponseEntity.ok("Mock Actualizado exitosamente"));
            return ResponseEntity.status(HttpStatus.OK).body(mock);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al Actualizar el mock");
        }
    }

    private LocalDateTime getExpiratonDate(@RequestBody @Valid MockRequest mockRequest) {
        Map<String, ChronoUnit> unitMap = new HashMap<>();
        unitMap.put("year", ChronoUnit.YEARS);
        unitMap.put("month", ChronoUnit.MONTHS);
        unitMap.put("week", ChronoUnit.WEEKS);
        unitMap.put("day", ChronoUnit.DAYS);
        unitMap.put("hour", ChronoUnit.HOURS);

        return LocalDateTime.now().plus(1, unitMap.get(mockRequest.getExpirationDate()));
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Locale locale) {
        model.addAttribute("title", messageSource.getMessage("title", null, locale));
        model.addAttribute("projects", projectService.getAllProjectsByUser(userService.getAuthenticatedUser().getId()));
        model.addAttribute("mock", new Mock());
        model.addAttribute("edit", false);
        model.addAttribute("activePage", "mock");
        return "mock/create";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMock(@Valid @RequestBody MockRequest mockRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        try {
            Mock mock = convertToMock(mockRequest);
            if (mock.getProject() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar el proyecto correspondiente");
            }
            if (mock.isValidateJWT()) {
                mock.setToken(TokenGenerator.createToken(mock));
            }
            mockService.createMock(mock);
            System.out.println("MOCK CREADO EXITOSAMENTE " + mock);
            System.out.println(ResponseEntity.ok("Mock creado exitosamente"));
            return ResponseEntity.status(HttpStatus.OK).body(mock);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el mock");
        }
    }

    public Mock convertToMock(MockRequest mockRequest) {
        Mock mock = new Mock();
        mock.setName(mockRequest.getName());
        mock.setHash(HashGenerator.generarHash());
        mock.setDescription(mockRequest.getDescription());
        mock.setEndpoint(mockRequest.getEndpoint());
        mock.setMethod(mockRequest.getMethod().toUpperCase());
        mock.setHeaders(mockRequest.getHeaders());
        mock.setStatusCode(mockRequest.getStatusCode());
        mock.setContentType(mockRequest.getContentType());
        mock.setBody(mockRequest.getBody());
        mock.setExpirationDate(getExpiratonDate(mockRequest));
        mock.setDelay(mockRequest.getDelay());
        mock.setValidateJWT(mockRequest.isValidateJWT());
        mock.setUser(userService.getAuthenticatedUser());
        mock.setProject(projectService.getProjectById(UUID.fromString(mockRequest.getProjectId())));

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            List<MockResponse> responses = objectMapper.readValue(mockRequest.getResponses(), new TypeReference<List<MockResponse>>(){});
            mock.setResponses(responses);
        } catch(Exception e) {
        }

        return mock;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteMock(@PathVariable UUID id) {
        try {

            System.out.println("ID: " + id);
            mockService.deleteMockByID(id);
            System.out.println("MOCK ELIMINADO EXITOSAMENTE");

            return ResponseEntity.ok("Mock eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el mock: " + e.getMessage());
        }
    }

}
