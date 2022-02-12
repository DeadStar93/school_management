package com.greenart.school_management.api;

import java.util.Map;

import com.greenart.school_management.data.StudentVO;
import com.greenart.school_management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentAPIController {
    @Autowired StudentService service;
    
    @PostMapping("/add")
    public Map<String, Object> insertStudent(@RequestBody StudentVO data) throws Exception {
        return service.addStudentInfo(data);
    }
}
