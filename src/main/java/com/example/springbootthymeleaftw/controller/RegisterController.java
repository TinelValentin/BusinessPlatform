package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.constant.Role;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.service.UserService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserValidatorService userValidatorService;
    private final UserService userService;

    private final RoleRepository roleRepository;

    @GetMapping()
    public String open(Model model){
        System.out.println(model);
        model.addAttribute("userForm", new UserEntity());

        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult){
        userValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        String formRole =  Role.ROLE_ADMIN.toString();
        Collection<RoleEntity> roles = new HashSet<>();

        Optional<RoleEntity> userRole = roleRepository.findByName(formRole);
        userRole.ifPresent(roles::add);

        userForm.setRoles(roles);
        userService.save(userForm);
        userService.login(userForm.getEmail(), userForm.getPassword());
        return "index";
    }
}
