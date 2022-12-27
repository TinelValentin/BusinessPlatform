package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.BusinessService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final SecurityService securityService;
    private final BusinessService businessService;

    @GetMapping()
    public String open(Model model, String error, String logout) {
        var businessesToApprove = businessService.getAllNotApprovedBusinesses();
        var name = securityService.getUsername();

        model.addAttribute("businesses", Objects.requireNonNullElse(businessesToApprove, Collections.emptyList()));
        model.addAttribute("user", name);

        return "admin";
    }


    @PostMapping()
    public String approve(Model model, String id) {
        businessService.approveBusinessWithId(id);
        var businessesToApprove = businessService.getAllNotApprovedBusinesses();
        var name = securityService.getUsername();

        model.addAttribute("businesses", Objects.requireNonNullElse(businessesToApprove, Collections.emptyList()));
        model.addAttribute("user", name);
        return "admin";
    }

    @PostMapping("/logout")
    public String logout() {
        securityService.logout();
        return "login";
    }
}
