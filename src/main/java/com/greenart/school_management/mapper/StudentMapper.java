package com.greenart.school_management.mapper;

import java.util.List;

import com.greenart.school_management.data.StudentVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    public List<StudentVO> getStudentList(String type, String keyword, Integer offset);
    public Integer getStudentCnt(String type, String keyword);

    public void insertStudentInfo(StudentVO data);
    
    public void deleteStudentInfo(Integer seq);


    public void updateStudentInfo(StudentVO data);
    
}
