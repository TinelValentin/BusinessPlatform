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
@RequestMapping("/bc")
@RequiredArgsConstructor
public class BCController {

    @Autowired
    private final SecurityService securityService;
    private final ProductService productService;

    @GetMapping()
    public String open(Model model) {
        var bbProducts = productService.getAllBBProducts();
        model.addAttribute("bbProducts", bbProducts);
        model.addAttribute("showInventory", false);
        return "bc";
    }

    @PostMapping("/showInventory")
    public String showInventory(Model model) {
        var inventoryProducts = productService.getAllProductsofUser(securityService.getUsername());
        model.addAttribute("inventoryProducts", inventoryProducts);
        model.addAttribute("showInventory", true);
        return "bc";
    }

    @PostMapping("/bc/showStores")
    public String showStores(Model model) {

        return "redirect:/bc";
    }

    @PostMapping(value = "/add/{id}")
    public String restock(@PathVariable Long id, Model model, int stock) {
        productService.buyFromBBToBC(id, stock,securityService.getUsername());
        return "redirect:/bc";
    }

}