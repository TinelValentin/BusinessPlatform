package com.example.springbootthymeleaftw.JWT;

import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    List<String> roles;
}
