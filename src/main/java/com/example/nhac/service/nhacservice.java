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
import java.util.Random;
import java.util.stream.Collectors;

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
    // Chuyển danh sách nhạc thành danh sách nhạc ngẫu nhiên
    public List<nhac> getRandomNhacList() {
        List<nhac> allNhac = nhacrepository.findAll();
        Random random = new Random();
        return random.ints(0, allNhac.size())
                .distinct()
                .limit(5) // Số lượng nhạc ngẫu nhiên muốn lấy
                .mapToObj(allNhac::get)
                .collect(Collectors.toList());
    }
    // Phương thức tìm kiếm
    public List<nhac> searchNhacByTen(String ten) {
        return nhacrepository.findByTenContainingIgnoreCase(ten);
    }

    public List<nhac> searchNhacByTacgia(String tacgia) {
        return nhacrepository.findByTacgiaContainingIgnoreCase(tacgia);
    }

    public List<nhac> searchNhacByTheloai(String theloai) {
        return nhacrepository.findByTheloaiContainingIgnoreCase(theloai);
    }
    public List<nhac> searchNhac(String ten, String tacgia, String theloai) {
        if (ten != null && tacgia != null && theloai != null) {
            return nhacrepository.findByTenContainingAndTacgiaContainingAndTheloaiContaining(ten, tacgia, theloai);
        } else if (ten != null && tacgia != null) {
            return nhacrepository.findByTenContainingAndTacgiaContaining(ten, tacgia);
        } else if (ten != null && theloai != null) {
            return nhacrepository.findByTenContainingAndTheloaiContaining(ten, theloai);
        } else if (tacgia != null && theloai != null) {
            return nhacrepository.findByTacgiaContainingAndTheloaiContaining(tacgia, theloai);
        } else if (ten != null) {
            return nhacrepository.findByTenContaining(ten);
        } else if (tacgia != null) {
            return nhacrepository.findByTacgiaContaining(tacgia);
        } else if (theloai != null) {
            return nhacrepository.findByTheloaiContaining(theloai);
        } else {
            return getNhacList();
        }
    }
}

