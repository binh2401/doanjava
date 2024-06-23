package com.example.nhac.service;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import com.example.nhac.dbo.request.nhacupdaterequest;
import com.example.nhac.repository.nhacrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class nhacservice {
    @Autowired
    private nhacrepository nhacrepository;

    @Autowired
    private FileStorageService fileStorageService;

    public nhac createNhac(nhaccreaterequest request) {
        nhac Nhac = new nhac();

        Nhac.setTen(request.getTen());
        Nhac.setTacgia(request.getTacgia());
        Nhac.setTheloai(request.getTheloai());

        try {
            MultipartFile audioFile = request.getAudioFile();
            MultipartFile imageFile = request.getImageFile();

            if (audioFile != null && !audioFile.isEmpty()) {
                String audioPath = fileStorageService.saveFile(audioFile);
                Nhac.setAudioPath(audioPath);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileStorageService.saveFile(imageFile);
                Nhac.setImagePath(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving files", e);
        }

        return nhacrepository.save(Nhac);
    }

    public List<nhac> getNhacList() {
        return nhacrepository.findAll();
    }

    public nhac getNhacById(String id) {
        return nhacrepository.findById(id).orElseThrow(() -> new RuntimeException("Nhac not found with id: " + id));
    }

    public nhac updateNhac(String nhacId, nhacupdaterequest request) {
        nhac Nhac = getNhacById(nhacId);

        Nhac.setTen(request.getTen());
        Nhac.setTacgia(request.getTacgia());
        Nhac.setTheloai(request.getTheloai());

        try {
            MultipartFile audioFile = request.getAudioFile();
            MultipartFile imageFile = request.getImageFile();

            if (audioFile != null && !audioFile.isEmpty()) {
                String audioPath = fileStorageService.saveFile(audioFile);
                Nhac.setAudioPath(audioPath);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileStorageService.saveFile(imageFile);
                Nhac.setImagePath(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving files", e);
        }

        return nhacrepository.save(Nhac);
    }

    public void deleteNhac(String nhacId) {
        nhacrepository.deleteById(nhacId);
    }

    public Resource loadAudioFile(String audioFilePath) {
        try {
            Path file = Paths.get(audioFilePath);
            Resource resource = new FileSystemResource(file);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + audioFilePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not load file: " + audioFilePath, e);
        }
    }

}

