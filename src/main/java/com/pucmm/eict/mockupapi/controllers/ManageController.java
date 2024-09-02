package com.pucmm.eict.mockupapi.controllers;

import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.models.Project;
import com.pucmm.eict.mockupapi.models.User;
import com.pucmm.eict.mockupapi.services.MockService;
import com.pucmm.eict.mockupapi.services.ProjectService;
import com.pucmm.eict.mockupapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManageController {
    private final MockService mockService;
    private final ProjectService projectService;
    private final UserService userService;


    @Autowired
    public ManageController(MockService mockService, ProjectService projectService, UserService userService) {
        this.mockService = mockService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("activePage", "projectManage");
        return "project/listManage";
    }

    @GetMapping("/mocks")
    public String getAllMocks(Model model) {
        List<Mock> mocks = mockService.getAllMocks();
        model.addAttribute("mocks", mocks);
        model.addAttribute("isAdmin", true);
        model.addAttribute("activePage", "mockManage");
        return "mock/list";
    }

}
