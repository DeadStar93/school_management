package com.greenart.school_management.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.greenart.school_management.data.TeacherHistoryVO;
import com.greenart.school_management.data.TeacherVO;
import com.greenart.school_management.mapper.TeacherMapper;
import com.greenart.school_management.utils.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired TeacherMapper mapper;

    public Map<String, Object> addTeacherInfo(TeacherVO data) throws Exception {
        Map<String, Object> resutlMap = new LinkedHashMap<String, Object>();
        
        // 검증
        if(data.getTi_name().equals("") || data.getTi_name() == null) {
            resutlMap.put("status", false);
            resutlMap.put("reason", "name");
            resutlMap.put("message", "이름을 입력해 주세요");
            return resutlMap;
        }

        if(data.getTi_number().equals("") || data.getTi_number() == null) {
            resutlMap.put("status", false);
            resutlMap.put("reason", "number");
            resutlMap.put("message", "교직원 번호을 입력해 주세요");
            return resutlMap;
        }

        if(data.getTi_birth().equals("") || data.getTi_birth() == null) {
            resutlMap.put("status", false);
            resutlMap.put("reason", "birth");
            resutlMap.put("message", "생년월일을 입력해 주세요");
            return resutlMap;
        }
        
        if(data.getTi_phone_num().equals("") || data.getTi_phone_num() == null) {
            resutlMap.put("status", false);
            resutlMap.put("reason", "phone");
            resutlMap.put("message", "전화번호을 입력해 주세요");
            return resutlMap;
        }
        
        if(data.getTi_email().equals("") || data.getTi_email() == null) {
            resutlMap.put("status", false);
            resutlMap.put("reason", "email");
            resutlMap.put("message", "이메일을 입력해 주세요");
            return resutlMap;
        }

        String pwd = data.getTi_pwd();
        String encrypted = AESAlgorithm.Encrypt(pwd);
        data.setTi_pwd(encrypted);

        mapper.addTeacherInfo(data);

        TeacherHistoryVO history = new TeacherHistoryVO();
        history.setTih_type("new");
        history.setTih_ti_content(data.makeAddHistoryStr());
        Integer recent_seq= mapper.getRecentAddedTeacherSeq();
        history.setTih_ti_seq(recent_seq);

        mapper.insertTeacherHistroy(history);

        resutlMap.put("status", true);
        resutlMap.put("message", "교직원 정보가 추가되었습니다.");
        return resutlMap;
    }

    public Map<String, Object> getTeacherList(String type, String keyword, Integer offset) {
        Map<String, Object> resutlMap = new LinkedHashMap<String, Object>();

        if(keyword == null) {
            resutlMap.put("keyword", keyword);
            keyword = "%%";
        }
        else {
            resutlMap.put("keyword", keyword);
            keyword = "%"+keyword+"%";
        }
        
        resutlMap.put("type", type);

        if(offset == null) offset = 0;

        List<TeacherVO> list = mapper.getTeacherList(type, keyword, offset);
        Integer cnt = mapper.getTeacherCnt(type, keyword);

        Integer page = cnt / 10;
        if(cnt % 10 > 0) page++;

        resutlMap.put("stauts", true);
        resutlMap.put("pageCnt", page);
        resutlMap.put("list", list);

        return resutlMap;
    }

    public TeacherVO getTeacherListBySeq(Integer seq) {
        
        return mapper.selectTeacherInfoBySeq(seq);
    }

    public Map<String, Object> updateTeacherList(TeacherVO data) {
        Map<String, Object> resutlMap = new LinkedHashMap<String, Object>();

        mapper.updateTeacherInfo(data);

        resutlMap.put("status", true);
        resutlMap.put("message", "교직원 정보가 수정되었습니다.");

        TeacherHistoryVO history = new TeacherHistoryVO();
        history.setTih_type("update");
        history.setTih_ti_content(data.makeAddHistoryStr());
        history.setTih_ti_seq(data.getTi_seq());

        mapper.insertTeacherHistroy(history);

        return resutlMap;
    }

    public ResponseEntity<Map<String, Object>> deleteTeacherList(Integer seq) {
        Map<String, Object> resutlMap = new LinkedHashMap<String, Object>();

        Integer cnt = mapper.isExistTeacher(seq);
        if(cnt == 0) {
            resutlMap.put("status", false);
            resutlMap.put("message", "삭제에 실패 했습니다.(존재하지 않는 교직원 정보)");
            return new ResponseEntity<Map<String, Object>>(resutlMap, HttpStatus.BAD_REQUEST);
        }
        else {
            mapper.deleteTeacherInfo(seq);
            resutlMap.put("status", true);
            resutlMap.put("message", "교직원 정보가 삭제되었습니다.");
            
            TeacherHistoryVO history = new TeacherHistoryVO();
            history.setTih_type("delete");
            history.setTih_ti_seq(seq);
            mapper.insertTeacherHistroy(history);
            return new ResponseEntity<Map<String, Object>>(resutlMap, HttpStatus.ACCEPTED);
        }
    }
}

