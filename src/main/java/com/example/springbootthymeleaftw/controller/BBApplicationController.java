package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Objects;

@Controller
@RequestMapping("/bbApp")
@RequiredArgsConstructor
public class BBApplicationController {

    @Autowired
    private final SecurityService securityService;
    private final ProductService productService;

    @GetMapping()
    public String open(Model model) {

        String name = securityService.getUsername();
        var allProducts = productService.getAllProductsToApprove(name);
        model.addAttribute("products",allProducts);
        return "bbApp";
    }

}