package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.JWT.JwtResponse;
import com.example.springbootthymeleaftw.JWT.JwtUtils;
import com.example.springbootthymeleaftw.model.entity.UserDetailsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private static String jwtToken;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public void generateToken(String username, String password) {
        UserDetails userDetail = userService.loadUserByUsername(username);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail, password, userDetail.getAuthorities()));
        jwtToken = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());

    }

    public void logout() {
        jwtToken = "";
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
