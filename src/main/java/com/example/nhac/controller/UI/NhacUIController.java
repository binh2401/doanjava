package com.example.nhac.controller.UI;

import com.example.nhac.Model.nhac;
import com.example.nhac.dbo.request.nhaccreaterequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/nhac-ui")
public class NhacUIController {

    private static final String API_URL = "http://localhost:8080/nhac"; // Đường dẫn API của bạn

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/list")
    public String getNhacList(Model model) {
        ResponseEntity<nhac[]> responseEntity = restTemplate.getForEntity(API_URL, nhac[].class);
        List<nhac> nhacs = Arrays.asList(responseEntity.getBody());
        model.addAttribute("nhacs", nhacs);
        return "nhac/nhac-list"; // Tên của tệp HTML trong thư mục templates
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("nhac", new nhac());
        return "nhac/add-nhac"; // Tên của tệp HTML trong thư mục templates
    }

    @PostMapping("/add")
    public String createNhac(@ModelAttribute nhaccreaterequest request, RedirectAttributes redirectAttributes) {
        HttpEntity<nhaccreaterequest> requestEntity = new HttpEntity<>(request);
        restTemplate.postForObject(API_URL + "/add", requestEntity, nhac.class);
        redirectAttributes.addFlashAttribute("message", "Thêm nhạc thành công");
        return "redirect:/nhac-ui/list"; // Chuyển hướng về trang danh sách nhạc
    }
}
