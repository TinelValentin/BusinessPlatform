package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.constant.Role;
import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.service.BusinessValidatorService;
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
@RequestMapping("/registerBusiness")
@RequiredArgsConstructor
public class RegisterBusinessController {
    private final UserValidatorService userValidatorService;
    private final BusinessValidatorService businessValidatorService;

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping()
    public String open(Model model){
        model.addAttribute("businessForm",  new BusinessEntity());
        model.addAttribute("userForm", new UserEntity());

        return "registerBusiness";
    }

    @PostMapping()
    public String registerBusiness(@ModelAttribute("businessForm") BusinessEntity businessForm,@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult){
        userValidatorService.validate(userForm, bindingResult);
        businessValidatorService.validate(businessForm, bindingResult);

        if (bindingResult.hasErrors())
            return "registerBusiness";

        String formRole = Objects.equals(businessForm.getBusinessType(), "BB") ? Role.ROLE_BB.toString() : Role.ROLE_BC.toString();
        Collection<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> userRole = roleRepository.findByName(formRole);
        userRole.ifPresent(roles::add);

        userForm.setRoles(roles);

        userService.saveBusiness(userForm,businessForm);
        return "redirect:/";
    }
}
