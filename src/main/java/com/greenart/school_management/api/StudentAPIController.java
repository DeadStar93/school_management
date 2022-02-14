package com.greenart.school_management.api;

import java.util.Map;

import com.greenart.school_management.data.StudentVO;
import com.greenart.school_management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/student")
public class StudentAPIController {
    @Autowired StudentService service;
    
    @PostMapping("/add")
    public Map<String, Object> insertStudent(@RequestBody StudentVO data) throws Exception {
        return service.addStudentInfo(data);
    }
    
    @DeleteMapping("/delete")
    public Map<String, Object> deleteStudent(@RequestParam Integer seq) {
        return service.deleteStudent(seq);
    }

    @GetMapping("/get")
    public StudentVO getStudentBySeq(@RequestParam Integer seq) {
        return service.getStudentBySeq(seq);
    }

    @PatchMapping("/update")
    public Map<String, Object> updateStudent(@RequestBody StudentVO data) {
        return service.updateStudenet(data);
    }
    
}
