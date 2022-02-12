package com.greenart.school_management.controller;

import com.greenart.school_management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @Autowired StudentService service;
    @GetMapping("/student")
    public String getStudentInfo() {
        return "/student/list";
    }
}