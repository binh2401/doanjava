package com.example.nhac.controller.UI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class nhacuicontrller {
    @GetMapping("/")
    public String Index(){
        return  "nhac/nhac-list";
    }
    @GetMapping("/add")
    public String add(){
        return "nhac/add-nhac";
    }
    @GetMapping("/sua")
    public String sua(){
        return "nhac/nhac-sua";
    }
    @GetMapping("/dk")
    public String dk(){
        return "dangnhap";
    }
    @GetMapping("/dn")
    public String dn(){
        return "login";
    }


}
