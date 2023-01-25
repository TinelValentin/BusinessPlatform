package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.helper.Helper;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.BusinessService;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private final SecurityService securityService;

    @Autowired
    private final ProductService productService;

    private final BusinessService businessService;

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @GetMapping()
    public String open(Model model) {
        setModel(model);
        return "client";
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @PostMapping("/filter")
    public String filter(Model model, @RequestParam List<String> selectFilter) {
        if (selectFilter != null) {
            var products = productService.getAllBCProductsWithNames(selectFilter);
            var businesses = businessService.findAllBuinessesBB();
            model.addAttribute("products", products);
            model.addAttribute("businesses", businesses);
        } else {
            setModel(model);
        }
        return "client";
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @PostMapping("/buy/{id}")
    public String buyProduct(Model model, @PathVariable Long id, int quantity) {
        productService.buyStock(id, quantity);
        setModel(model);

        return "redirect:/client";
    }

    private void setModel(Model model) {
        var products = productService.getAllBCProducts();
        var businesses = businessService.findAllBuinessesBB();

        model.addAttribute("products", products);
        model.addAttribute("businesses", businesses);
    }


}