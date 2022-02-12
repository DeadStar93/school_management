package com.greenart.school_management.api;

import java.util.Map;

import com.greenart.school_management.data.TeacherVO;
import com.greenart.school_management.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherAPIController {
    @Autowired TeacherService service;

    @PostMapping("/add") // 생성
    public Map<String, Object> postTeacherAdd(@RequestBody TeacherVO data) throws Exception {

        return service.addTeacherInfo(data);
    }

    @DeleteMapping("/delete")  //   삭제
    public ResponseEntity<Map<String, Object>> deleteTeacherInfo(@RequestParam Integer seq) {

        return service.deleteTeacherList(seq);
    }

    @GetMapping("/get") // 수정값 조회
    public TeacherVO getTeacherBySeq(@RequestParam Integer seq) {
    
        return service.getTeacherListBySeq(seq);
    }

    @PatchMapping("/update")    // 수정
    public Map<String, Object> updateTeacherInfo(@RequestBody TeacherVO data) {

        return service.updateTeacherList(data);
    }
}

