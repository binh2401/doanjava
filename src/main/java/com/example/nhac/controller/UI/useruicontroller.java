package com.example.nhac.controller.UI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class useruicontroller {
    @GetMapping("/")
    public String Index(){
        return  "admin/user-list";
    }
    @GetMapping("/add")
    public String add(){
        return "admin/user-add";
    }
    @GetMapping("/sua")
    public String sua(){
        return "admin/user-edit";
    }
    @GetMapping("/nhac")
    public String list(){
        return "admin/list-nhac";
    }
}
