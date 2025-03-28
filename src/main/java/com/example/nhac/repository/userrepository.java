package com.example.nhac.repository;

import com.example.nhac.Model.nhac;
import com.example.nhac.Model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userrepository  extends JpaRepository<user, String> {
    Optional<user> findByUsername(String username);
}
