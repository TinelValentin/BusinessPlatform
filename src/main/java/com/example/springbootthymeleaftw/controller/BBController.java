package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
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

import java.util.Collections;
import java.util.Objects;

@Controller
@RequestMapping("/bb")
@RequiredArgsConstructor
public class BBController {

    @Autowired
    private final SecurityService securityService;
    private final ProductService productService;

   @PreAuthorize("hasAuthority('ROLE_BB')")
    @GetMapping()
    public String open(Model model) {

        addAttributes(model);
        model.addAttribute("addProduct", false);
        return "bb";
    }

    @PreAuthorize("hasAuthority('ROLE_BB')")
    @GetMapping("/error")
    public String error(Model model) {

        addAttributes(model);
        model.addAttribute("addProduct", false);
        model.addAttribute("error", true);
        return "bb";
    }

    @PreAuthorize("hasAuthority('ROLE_BB')")
    @PostMapping()
    public String addProductForm(Model model, @ModelAttribute("Product") ProductEntity productForm) {
        model.addAttribute("addProduct", true);
        if (productForm.getName() != null && productForm.getDescription() != null) {
            if (!productService.ValidateProduct(productForm)) {
                return "redirect:/bb/error";
            }
            productService.addProduct(productForm, securityService.getUsername());
            model.addAttribute("addProduct", false);
        }

        addAttributes(model);
        model.addAttribute("Product", new ProductEntity());
        return "bb";
    }

    @PreAuthorize("hasAuthority('ROLE_BB')")
    @PostMapping(value = "/add/{id}")
    public String restock(@PathVariable Long id, Model model, int stock) {
        productService.updateStock(id, stock);
        addAttributes(model);
        return "redirect:/bb";
    }

    private void addAttributes(Model model) {
        var name = securityService.getUsername();
        var allProducts = productService.getAllProductsofUserApproved(name);

        model.addAttribute("Product", null);
        model.addAttribute("products", Objects.requireNonNullElse(allProducts, Collections.emptyList()));
        model.addAttribute("user", name);
    }
}