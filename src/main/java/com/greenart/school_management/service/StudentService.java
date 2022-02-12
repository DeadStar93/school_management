package com.greenart.school_management.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.greenart.school_management.data.StudentVO;
import com.greenart.school_management.mapper.StudentMapper;
import com.greenart.school_management.utils.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired StudentMapper mapper;

    public Map<String, Object> addStudentInfo(StudentVO data) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(data.getSi_name().equals("") || data.getSi_name() == null) {
            resultMap.put("status", false);
            resultMap.put("reason", "name");
            resultMap.put("message", "이름을 입력해주세요.");
            return resultMap;
        }

        if(data.getSi_number().equals("") || data.getSi_number() == null) {
            resultMap.put("status", false);
            resultMap.put("reason", "number");
            resultMap.put("message", "학생 번호를 입력해주세요.");
            return resultMap;
        }

        if(data.getSi_birth().equals("") || data.getSi_birth() == null) {
            resultMap.put("status", false);
            resultMap.put("reason", "birth");
            resultMap.put("message", "생년월일을 입력해주세요.");
            return resultMap;
        }

        if(data.getSi_phone_num().equals("") || data.getSi_phone_num() == null) {
            resultMap.put("status", false);
            resultMap.put("reason", "phone");
            resultMap.put("message", "전화번호를 입력해주세요.");
            return resultMap;
        }

        if(data.getSi_email().equals("") || data.getSi_email() == null) {
            resultMap.put("status", false);
            resultMap.put("reason", "email");
            resultMap.put("message", "이메일을 입력해주세요.");
            return resultMap;
        }

        data.setSi_pwd(AESAlgorithm.Encrypt(data.getSi_pwd()));
        
        mapper.insertStudentInfo(data);

        resultMap.put("status", true);
        resultMap.put("message", "학생 정보가 등록되었습니다.");

        return resultMap;
    }
}
