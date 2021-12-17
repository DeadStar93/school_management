package com.greenart.school_management.controller;

import java.util.Map;

import com.greenart.school_management.service.DepartmentService;
import com.greenart.school_management.utils.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DepartmentController {
@Autowired
DepartmentService service;

    @GetMapping("/department")
    public String getDepartment(Model model, @RequestParam @Nullable Integer offset,
    @RequestParam @Nullable String keyword) throws Exception {
        Map<String, Object> resultMap = service.getDepartmentList(offset, keyword);
        model.addAttribute("data", resultMap);
        /*
        throws Exception 적용 후
        
        // ReBaRzwJEjjjhswTTFlP7A== [키값을 안바궜을때 디버그 콘솔창]
        // 키값이 바뀔때마다 암호화결과값도 바뀜
        // YYnW/ijxT2NARajXnUAUoA== [키값을 바꿧을때 디버그 콘솔창]

        if(keyword != null)
            System.out.println(AESAlgorithm.Encrypt(keyword));
        */
        return "/department/list";
    }
}
