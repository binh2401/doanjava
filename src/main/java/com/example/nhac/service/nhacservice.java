package com.example.nhac.service;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import com.example.nhac.dbo.request.nhacupdaterequest;
import com.example.nhac.repository.nhacrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class nhacservice {
    @Autowired
    private nhacrepository nhacrepository;

    public nhac createRequest(nhaccreaterequest request){
    nhac Nhac =new nhac();

    Nhac.setTen(request.getTen());
    Nhac.setTacgia(request.getTacgia());
    Nhac.setTheloai(request.getTheloai());

    return nhacrepository.save(Nhac);
    }
    public List<nhac> getnhac(){
        return nhacrepository.findAll();
    }

    public nhac getnhac(String id){
        return nhacrepository.findById(id).orElseThrow(() -> new RuntimeException("NHAC KHONG TON TAI"));
    }

    public  nhac updatenhac(String nhacid,nhacupdaterequest request){
        nhac Nhac =getnhac(nhacid);
        Nhac.setTen(request.getTen());
        Nhac.setTacgia(request.getTacgia());
        Nhac.setTheloai(request.getTheloai());

        return nhacrepository.save(Nhac);
    }

    public void deletenhac(String nhacid){
        nhacrepository.deleteById(nhacid);
    }
}
