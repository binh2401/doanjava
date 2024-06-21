package com.example.nhac.controller;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import com.example.nhac.dbo.request.nhacupdaterequest;
import com.example.nhac.service.nhacservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/nhac")
public class nhaccontroller {
    @Autowired
    private nhacservice nhacservice;

    @PostMapping("/add")
    public nhac createnhac(@ModelAttribute nhaccreaterequest request) {
        return nhacservice.createNhac(request);
    }

    @GetMapping
    public List<nhac> getnhac() {
        return nhacservice.getNhacList();
    }

    @GetMapping("/{nhacId}")
    public nhac getNhacById(@PathVariable("nhacId") String nhacId) {
        return nhacservice.getNhacById(nhacId);
    }

    @PutMapping("/{nhacId}")
    public nhac updateNhac(@PathVariable("nhacId") String nhacId,
                           @RequestBody nhacupdaterequest request) {
        return nhacservice.updateNhac(nhacId, request);
    }

    @DeleteMapping("/{nhacId}")
    public String deleteNhac(@PathVariable("nhacId") String nhacId) {
        nhacservice.deleteNhac(nhacId);
        return "Nhac with id " + nhacId + " has been deleted";
    }
}
