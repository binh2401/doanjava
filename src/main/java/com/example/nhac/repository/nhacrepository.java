package com.example.nhac.repository;

import com.example.nhac.Model.nhac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nhacrepository extends JpaRepository<nhac, String> {

}
