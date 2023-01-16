package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.JWT.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    public String generateToken(String username, String password) {
        UserDetails userDetail = userService.loadUserByUsername(username);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));
        jwtToken = jwtUtils.generateJwtToken(SecurityContextHolder.getContext().getAuthentication());

        return jwtToken;

    }

    public String getAuthentificationToken()
    {
        if(jwtToken==null|| jwtToken.equals(""))
        {
            return "";
        }
        return "Bearer "+jwtToken;
    }

    public void logout() {
        jwtToken = "";
        SecurityContextHolder.clearContext();
    }

    public String getUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
