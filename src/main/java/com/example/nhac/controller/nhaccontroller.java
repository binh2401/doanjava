package com.example.nhac.controller;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import com.example.nhac.dbo.request.nhacupdaterequest;
import com.example.nhac.service.nhacservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhac")
public class nhaccontroller {
    @Autowired
    private nhacservice nhacservice;

    @PostMapping("/add")
    nhac createnhac (@RequestBody nhaccreaterequest request){
        return  nhacservice.createRequest(request);
    }

    @GetMapping
    List<nhac> getnhac(){
        return nhacservice.getnhac();
    }
    @GetMapping("/{nhacid}")
    nhac getnhac(@PathVariable("nhacid") String nhacid){
        return  nhacservice.getnhac(nhacid);

    }
    @PutMapping("/{nhacid}")
    nhac updatenhac(@PathVariable String nhacid, @RequestBody nhacupdaterequest request){
    return  nhacservice.updatenhac(nhacid,request);
    }

    @DeleteMapping("/{nhacid}")
    String deletenhac(@PathVariable String nhacid){
        nhacservice.deletenhac(nhacid);
        return "nhac da xoa";
    }
}
