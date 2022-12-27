package com.example.springbootthymeleaftw.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RouterService {
    public String loginRoute()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var firstRole = authentication.getAuthorities().stream().findFirst();
        String role = "";

        if(firstRole.isPresent())
        {
            role = firstRole.get().toString();
        }

        return switch (role) {
            case "ROLE_BB" -> "redirect:/bb";
            case "ROLE_BC" -> "redirect:/bc";
            case "ROLE_ADMIN" -> "redirect:/admin";
            default -> "redirect:/client";
        };
    }
}
