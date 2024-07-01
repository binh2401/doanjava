package com.example.nhac.controller;

import com.example.nhac.Model.user;
import com.example.nhac.controller.Config.JwtUtil;
import com.example.nhac.dbo.request.usercreaterequest;
import com.example.nhac.service.uerservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public ResponseEntity<?> login(@RequestBody usercreaterequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPass();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body("Username or password cannot be null or empty");
        }

        Optional<user> userOptional = uerservice.login(username, password);

        if (userOptional.isPresent()) {
            String token = jwtUtil.generateToken(userOptional.get().getUsername());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                    .body("Invalid username or password");
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        try {
            // Lấy token từ header Authorization
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Bỏ phần "Bearer " để lấy token thô
            } else {
                throw new IllegalArgumentException("Token không hợp lệ");
            }

            // Giải mã token để lấy thông tin người dùng
            String username = jwtUtil.getUsernameFromToken(token);
            if (username == null) {
                throw new IllegalArgumentException("Không thể lấy thông tin người dùng từ token");
            }

            // Tìm người dùng trong cơ sở dữ liệu
            Optional<user> optionalUser = uerservice.findByUsername(username);
            if (optionalUser.isPresent()) {
                user user = optionalUser.get();
                // Trả về thông tin người dùng
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không thể xác thực người dùng");
        }
    }
}