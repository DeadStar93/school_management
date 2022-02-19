package com.greenart.school_management.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.greenart.school_management.data.StudentHistoryVO;
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

        StudentHistoryVO history = new StudentHistoryVO();
        history.setSih_type("new");
        history.setSih_si_content(data.makeAddHistoryStr());
        history.setSih_si_seq(mapper.getRecnetAddedStudentSeq());
        mapper.insertStudentHistory(history);

        resultMap.put("status", true);
        resultMap.put("message", "학생 정보가 등록되었습니다.");

        return resultMap;
    }

    public Map<String, Object> getStudentList(String keyword, String type, Integer offset) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(keyword == null) {
            resultMap.put("keyword", keyword);
            keyword = "%%";
        }
        else{
            resultMap.put("keyword", keyword);
            keyword = "%"+keyword+"%";
        }
        resultMap.put("type", type);
        if(offset == null) offset=0;

        List<StudentVO> list = mapper.getStudentList(type, keyword, offset);
        Integer cnt = mapper.getStudentCnt(type, keyword);
        Integer page = (cnt / 10)+ (cnt % 10 > 0 ? 1 : 0);
        resultMap.put("status", true);
        resultMap.put("page", page);
        resultMap.put("list", list);
            
        return resultMap;
    }

    public StudentVO getStudentBySeq(Integer seq) {
        return mapper.getStudentBySeq(seq);
    }

    public Map<String, Object> updateStudenet(StudentVO data) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        mapper.updateStudentInfo(data);

        resultMap.put("status", true);
        resultMap.put("message", "학생 정보가 수정되었습니다.");

        StudentHistoryVO history = new StudentHistoryVO();
        history.setSih_type("update");
        history.setSih_si_content(data.makeAddHistoryStr());
        history.setSih_si_seq(data.getSi_seq());
        mapper.insertStudentHistory(history);

        return resultMap;
    }

    public Map<String, Object> deleteStudent(Integer seq) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(mapper.isExistStudent(seq) == 0) {
            resultMap.put("status", false);
            resultMap.put("message", "존재하지 않는 학생 정보입니다.");
            return resultMap;
        }
        else {
            mapper.deleteStudentInfo(seq);
            resultMap.put("status", true);
            resultMap.put("message", "학생 정보가 삭제되었습니다.");

            StudentHistoryVO history = new StudentHistoryVO();
            history.setSih_type("delete");
            history.setSih_si_seq(seq);
            mapper.insertStudentHistory(history);

            return resultMap;
        }
    }

}
