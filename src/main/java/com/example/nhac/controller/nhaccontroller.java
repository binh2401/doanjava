package com.example.nhac.controller;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import com.example.nhac.dbo.request.nhacupdaterequest;
import com.example.nhac.service.nhacservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/nhac")
public class nhaccontroller {
    @Autowired
    private nhacservice nhacService;

    @GetMapping
    public List<nhac> getAllNhac() {
        return nhacService.getNhacList();
    }

    @GetMapping("/{nhacId}")
    public nhac getNhacById(@PathVariable String nhacId) {
        return nhacService.getNhacById(nhacId);
    }

    @PostMapping("/add")
    public nhac createNhac(@ModelAttribute nhaccreaterequest request) {
        return nhacService.createNhac(request);
    }

    @PutMapping("/update/{nhacId}")
    public nhac updateNhac(@PathVariable String nhacId, @ModelAttribute nhacupdaterequest request) {
        return nhacService.updateNhac(nhacId, request);
    }

    @DeleteMapping("/delete/{nhacId}")
    public void deleteNhac(@PathVariable String nhacId) {
        nhacService.deleteNhac(nhacId);
    }

    @GetMapping("/audio/{nhacId}")
    public ResponseEntity<Resource> getAudioFile(@PathVariable String nhacId) {
        nhac nhac = nhacService.getNhacById(nhacId);
        String audioFilePath = nhac.getAudioPath();  // Sử dụng filePath thay vì audioPath

        Resource audioFile = nhacService.loadAudioFile(audioFilePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.getFilename() + "\"")
                .body(audioFile);
    }
}