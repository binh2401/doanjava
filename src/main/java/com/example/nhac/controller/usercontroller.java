package com.example.nhac.controller;

import com.example.nhac.Model.user;
import com.example.nhac.controller.Config.JwtUtil;
import com.example.nhac.dbo.request.usercreaterequest;
import com.example.nhac.service.uerservice;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class usercontroller {
    @Autowired
    private uerservice uerservice;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody usercreaterequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                        } else {
                            return error.getObjectName() + ": " + error.getDefaultMessage();
                        }
                    })
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }

        user user = new user();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPass());
        user.setEmail(request.getEmail()); // Đảm bảo rằng email được thiết lập
        user.setPhone(request.getPhone());
        uerservice.save(user);
        uerservice.setDefaultRole(user.getUsername());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody usercreaterequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                        } else {
                            return error.getObjectName() + ": " + error.getDefaultMessage();
                        }
                    })
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }

        user user = uerservice.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !user.getPassword().equals(request.getPass())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return uerservice.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}