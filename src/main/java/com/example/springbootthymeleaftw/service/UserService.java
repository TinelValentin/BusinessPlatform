package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.constant.Role;
import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.BusinessRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        Optional<Boolean> isAccountApproved = userRepository.isTheAccountApproved(email);
        if(isAccountApproved.isPresent())
        {
            if(!isAccountApproved.get())
                throw new UsernameNotFoundException(email);
        }

        if (optUser.isPresent()) {
            var test = userRepository.findRoleByEmail(email);
            UserEntity appUser = optUser.get();
            test.ifPresent(System.out::print);
            return new User(
                    appUser.getUsername(), appUser.getPassword(), true, true, true, true,
                    /* User Roles */
                    Objects.isNull(appUser.getRoles()) ?
                            new ArrayList<>(List.of(new SimpleGrantedAuthority("default")))
                            : appUser.getRoles()
                                .stream()
                                .map(RoleEntity::getName)
                                .map(SimpleGrantedAuthority::new)
                                .toList()
            );
        }
        throw new UsernameNotFoundException(email);
    }


    public UserDetails loadUserByName(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);

        if (optUser.isPresent()) {
            var test = userRepository.findRoleByEmail(username);
            UserEntity appUser = optUser.get();
            test.ifPresent(System.out::print);
            return new User(
                    appUser.getEmail(), appUser.getPassword(), true, true, true, true,
                    /* User Roles */
                    Objects.isNull(appUser.getRoles()) ?
                            new ArrayList<>(List.of(new SimpleGrantedAuthority("default")))
                            : appUser.getRoles()
                            .stream()
                            .map(RoleEntity::getName)
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );
        }
        throw new UsernameNotFoundException(username);
    }


    public void save(UserEntity user) {
        BusinessEntity userBusiness = businessRepository.findNonExistentBusiness();
        user.setBusiness(userBusiness);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void saveBusiness(UserEntity user,BusinessEntity business) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBusiness(business);
        businessRepository.save(business);
        userRepository.save(user);
    }

    public boolean existsUser(UserEntity user)
    {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }
}
