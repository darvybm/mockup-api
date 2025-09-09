package com.pucmm.eict.mockupapi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.models.MockResponse;
import com.pucmm.eict.mockupapi.services.MockService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RestController
public class APIMockupController {

    private final MockService mockService;

    @Autowired
    public APIMockupController(MockService mockService) {
        this.mockService = mockService;
    }

    @RequestMapping(value = "/{hash}/api/{projectName}/{endpoint}", method = {
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.PATCH,
            RequestMethod.DELETE})

    public Object handleMockRequest(
            @PathVariable String hash,
            @PathVariable String projectName,
            @PathVariable String endpoint,
            @RequestParam(value = "token", required = false) String token,
            HttpServletRequest request
    ) throws IOException {

        Mock mock = mockService.getMockByHash(hash);
        if (mock == null) {
            return new ResponseEntity<>("Mock no encontrado", HttpStatus.NOT_FOUND);
        }

        System.out.println("METHOD: " + request.getMethod());
        System.out.println("MOOOOOCCCKKK:  " + mock);

        if (!mock.getMethod().equalsIgnoreCase(request.getMethod())) {
            return new ResponseEntity<>("Petición No válida", HttpStatus.METHOD_NOT_ALLOWED);

        }

        System.out.println("METODO VALIDO");

        if (isMockExpired(mock)) {
            return new ResponseEntity<>("Mock expirado", HttpStatus.GONE);
        }

        if (!isValidToken(token, mock.getToken()) && mock.isValidateJWT()) {
            return new ResponseEntity<>("Token inválido", HttpStatus.UNAUTHORIZED);
        }

        System.out.println("PASO LA VALIDACION");

        simulateDelay(mock);

        System.out.println("PASO EL DELAY");

        HttpHeaders headers = createHeadersFromJson(mock.getHeaders());
        String responseBody = mock.getBody();
        int statusCode = mock.getStatusCode();

        if(!mock.getResponses().isEmpty()) {

            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                for(MockResponse response : mock.getResponses()){
                    if(requestBody.toLowerCase().contains(response.getRuleTrigger().toLowerCase())){
                        responseBody = response.getBody();
                        statusCode = response.getStatusCode();
                        break;
                    }
                }
            }

            if ("GET".equalsIgnoreCase(request.getMethod())) {
                String requestQueryString = request.getQueryString();
                for(MockResponse response : mock.getResponses()){
                    if(requestQueryString.toLowerCase().contains(response.getRuleTrigger().toLowerCase())){
                        responseBody = response.getBody();
                        statusCode = response.getStatusCode();
                        break;
                    }
                }
            }

        }

        return new ResponseEntity<>(responseBody, headers, HttpStatus.valueOf(statusCode));
    }

    private boolean isMockExpired(Mock mock) {
        return mock.getExpirationDate().isBefore(LocalDateTime.now());
    }

    private void simulateDelay(Mock mock) {
        try {
            Thread.sleep(mock.getDelay() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private HttpHeaders createHeadersFromJson(String headersJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> headersMap = objectMapper.readValue(headersJson, new TypeReference<HashMap<String, String>>() {});
            HttpHeaders headers = new HttpHeaders();
            headers.setAll(headersMap);
            return headers;
        } catch (IOException e) {
            return new HttpHeaders();
        }
    }

    private boolean isValidToken(String providedToken, String expectedToken) {
        return providedToken != null && providedToken.equals(expectedToken);
    }
}

