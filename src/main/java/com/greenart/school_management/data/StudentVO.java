package com.greenart.school_management.data;

import java.util.Date;

import lombok.Data;

@Data
public class StudentVO {
    private Integer si_seq;
    private Integer si_di_seq;
    private String si_number;
    private String si_pwd;
    private String si_name;
    private Date si_reg_dt;
    private Date si_mod_dt;
    private Integer si_status;
    private String si_phone_num;
    private String si_email;
    private String si_birth;
    private String si_img_url;

    private String department_name;

    public String makeAddHistoryStr() {
        return si_di_seq+"|"+si_number+"|"+si_name+"|"+si_status+"|"+si_phone_num+"|"+si_email+"|"+si_birth;
    }

}
