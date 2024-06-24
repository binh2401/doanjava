package com.example.nhac.controller;

import com.example.nhac.Model.user;
import com.example.nhac.dbo.request.usercreaterequest;
import com.example.nhac.service.uerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class usercontroller {
    @Autowired
    private uerservice uerservice;

    @PostMapping("/add")
    public user createUser(@RequestBody usercreaterequest request) {
        return uerservice.createuser(request);
    }

    @GetMapping
    public List<user> getAllUsers() {
        return uerservice.getuserlist();
    }

    @GetMapping("/{id}")
    public user getUserById(@PathVariable String id) {
        return uerservice.getuserid(id);
    }

    @PutMapping("/update/{id}")
    public user updateUser(@PathVariable String id, @RequestBody usercreaterequest request) {
        return uerservice.updateuser(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        uerservice.deleteuser(id);
    }
}
