package com.example.nhac.repository;


import com.example.nhac.Model.role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface rolerepository extends JpaRepository<role, Long> {
    role findRoleById(Long id);
}
