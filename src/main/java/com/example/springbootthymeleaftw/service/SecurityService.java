package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.JWT.JwtResponse;
import com.example.springbootthymeleaftw.JWT.JwtUtils;
import com.example.springbootthymeleaftw.model.entity.UserDetailsEntity;
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

@Service
public class SecurityService {

    private String jwtToken;
    @Autowired
    private  AuthenticationManager authenticationManager;

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

    public void generateToken(String username, String password)
    {
        UserDetails userDetail = userService.loadUserByUsername(username);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail, password,userDetail.getAuthorities()));
        this.jwtToken = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());

//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        return new JwtResponse(jwtToken,
//                userDetails.getUsername(),
//                "userDetails.getEmail()",
//                roles);
    }

}
