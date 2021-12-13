package com.greenart.school_management.mapper;

import java.util.List;

import com.greenart.school_management.data.DepartmentVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    public List<DepartmentVO> getDepartmentInfo(Integer offset);
    public Integer getDepartmentCount();   
    public void AddDepartment(DepartmentVO data);
    public void DeleteDepartment(Integer seq);
}
