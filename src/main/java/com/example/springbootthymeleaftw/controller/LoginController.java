package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.JWT.JwtResponse;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.RouterService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private final SecurityService securityService;

    private final RouterService routerService;

    @GetMapping()
    public String open(Model model, String error, String logout){
        model.addAttribute("user",new UserEntity());
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @PostMapping("/")
    public String login(Model model, @ModelAttribute("user") UserEntity user, String error, HttpServletResponse response, HttpServletRequest request){
        var email = user.getEmail();
        var password = user.getPassword();

       String token = securityService.generateToken(email,password);
       request.setAttribute("Authorization",token);

       return routerService.loginRoute();
    }


    @GetMapping("/logout")
    public String logout(Model model){
        securityService.logout();
        model.addAttribute("user",new UserEntity());
        model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout){
        model.addAttribute("user",new UserEntity());
        model.addAttribute("error", "Your username and password is invalid.");
        return "login";
    }

}
