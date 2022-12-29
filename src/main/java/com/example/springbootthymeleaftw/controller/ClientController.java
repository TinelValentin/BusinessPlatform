package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.helper.Helper;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.BusinessService;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ProductService productService;

    private final BusinessService businessService;

    @GetMapping()
    public String open(Model model) {
        var products = productService.getAllBCProducts();
        var businesses = businessService.findAllBuinessesBB();
        List<Boolean> checkedFilters=new ArrayList<>(Arrays.asList(new Boolean[businesses.size()]));
        Collections.fill(checkedFilters, Boolean.TRUE);
        Map<String, Boolean> checkboxData = Helper.createMapFromLists(businesses,checkedFilters);
        model.addAttribute("products", products);
        model.addAttribute("businesses", checkboxData);
        return "client";
    }

    @PostMapping("/filter")
    public String filter(Model model, Map<String, Boolean> businesses)
    {
        var a = 10;
        return "client";
    }


}