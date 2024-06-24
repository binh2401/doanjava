package com.example.nhac.service;

import com.example.nhac.Model.user;
import com.example.nhac.dbo.request.usercreaterequest;
import com.example.nhac.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class uerservice {
    @Autowired
    private userrepository  userrepository;

    public user createuser(usercreaterequest request) {
        user User = new user();
        User.setName(request.getName());
        User.setUsername(request.getUsername());
        User.setPass(request.getPass());
        return userrepository.save(User);
    }

    public List<user> getuserlist() {
        return userrepository.findAll();
    }

    public  user getuserid(String id){
        return userrepository.findById(id).orElseThrow(() -> new RuntimeException("user not found with id: " + id));
    }
    public user updateuser(String id,usercreaterequest request){
        user User = getuserid(id);
        User.setName(request.getName());
        User.setUsername(request.getUsername());
        User.setPass(request.getPass());
        return  userrepository.save(User);
    }
    public  void deleteuser(String id){
        userrepository.deleteById(id);
        }


}
