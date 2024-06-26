package com.example.nhac.Component;

import com.example.nhac.Model.role;
import com.example.nhac.repository.rolerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleInitializer implements CommandLineRunner {
    @Autowired
    private rolerepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem có các role trong database chưa
        if (roleRepository.count() == 0) {
            role adminRole = new role();
            adminRole.setId(UUID.randomUUID());
            adminRole.setName("ADMIN");

            role userRole = new role();
            userRole.setId(UUID.randomUUID());
            userRole.setName("USER");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }
}
