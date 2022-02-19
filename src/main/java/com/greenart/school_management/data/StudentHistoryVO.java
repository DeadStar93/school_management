package com.greenart.school_management.data;

import java.util.Date;

import lombok.Data;

@Data
public class StudentHistoryVO {
    private Integer sih_seq;
    private Integer sih_si_seq;
    private String sih_type;
    private String sih_si_content;
    private Date sih_reg_dt;
}
