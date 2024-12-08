package com.example.petshouseholds.service;

import com.example.petshouseholds.entity.AppUser;
import com.example.petshouseholds.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Use SimpleGrantedAuthority to convert role to Spring Security-compatible authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole());

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(Collections.singletonList(authority)) // Set roles as authorities
                .disabled(!appUser.isUnlocked()) // Disabled if unlocked is false
                .build();
    }
}
