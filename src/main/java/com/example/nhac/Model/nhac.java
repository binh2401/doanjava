package com.example.nhac.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class nhac {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ten;
    private String tacgia;
    private String theloai;
    private String audioFile;  // Đường dẫn đến file âm thanh
    private String imageFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getAudioPath() {
        return audioFile;
    }

    public void setAudioPath(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getImagePath() {
        return imageFile;
    }

    public void setImagePath(String imageFile) {
        this.imageFile = imageFile;
    }
}
