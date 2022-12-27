package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.service.BusinessService;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Objects;

@Controller
@RequestMapping("/bb")
@RequiredArgsConstructor
public class BBController {

    @Autowired
    private final SecurityService securityService;
    private final ProductService productService;

    @GetMapping()
    public String open(Model model) {

        addAttributes(model);
        model.addAttribute("addProduct", false);
        return "bb";
    }

    @PostMapping()
    public String addProductForm(Model model,@ModelAttribute("Product") ProductEntity productForm) {
        if(productForm.getName()!=null && productForm.getDescription()!=null)
        {
            productService.addProduct(productForm,securityService.getUsername());
        }

        addAttributes(model);
        model.addAttribute("addProduct", true);
        model.addAttribute("Product",new ProductEntity());
        return "bb";
    }

    private void addAttributes(Model model) {
        var name = securityService.getUsername();
        var allProducts = productService.getAllProductsofUser(name);

        model.addAttribute("Product",null);
        model.addAttribute("products", Objects.requireNonNullElse(allProducts, Collections.emptyList()));
        model.addAttribute("user", name);
    }
}