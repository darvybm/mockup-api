package com.pucmm.eict.mockupapi.controllers;

import com.pucmm.eict.mockupapi.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
                case 400:
                    return "error/400";
                case 401:
                    return "error/401";
                case 403:
                    return "error/403";
                case 404:
                    return "error/404";
                case 500:
                    return "error/500";
                default:
                    return "error/error";
            }
        }
        return "error/error";
    }

    @GetMapping("/400")
    public String getError400() {

        return "error/400";
    }

    @GetMapping("/401")
    public String getError401() {

        return "error/401";
    }

    @GetMapping("/403")
    public String getError403() {

        return "error/403";
    }

    @GetMapping("/404")
    public String getError404() {

        return "error/404";
    }

    @GetMapping("/500")
    public String getError500() {

        return "error/500";
    }
}
