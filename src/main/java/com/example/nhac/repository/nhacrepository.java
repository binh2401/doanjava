package com.example.nhac.repository;

import com.example.nhac.Model.nhac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface nhacrepository extends JpaRepository<nhac, String> {
    List<nhac> findByTenContainingIgnoreCase(String ten);
    List<nhac> findByTacgiaContainingIgnoreCase(String tacgia);
    List<nhac> findByTheloaiContainingIgnoreCase(String theloai);
    List<nhac> findByTenContaining(String ten);
    List<nhac> findByTacgiaContaining(String tacgia);
    List<nhac> findByTheloaiContaining(String theloai);

    List<nhac> findByTenContainingAndTacgiaContaining(String ten, String tacgia);
    List<nhac> findByTenContainingAndTheloaiContaining(String ten, String theloai);
    List<nhac> findByTacgiaContainingAndTheloaiContaining(String tacgia, String theloai);

    List<nhac> findByTenContainingAndTacgiaContainingAndTheloaiContaining(String ten, String tacgia, String theloai);
}
