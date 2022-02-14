package com.greenart.school_management.controller;

import com.greenart.school_management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    @Autowired StudentService service;
    @GetMapping("/student")
    public String getStudentInfo(Model model,
    @RequestParam @Nullable String keyword,
    @RequestParam @Nullable String type,
    @RequestParam @Nullable Integer offset
    ) {
        model.addAttribute("data",service.getStudentList(keyword, type, offset));
        return "/student/list";
    }
}
