package com.example.petshouseholds.service;

import com.example.petshouseholds.entity.AppUser;
import com.example.petshouseholds.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    public void changePassword(String username, String newPassword) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void lockUnlockUser(String username, boolean unlock) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setUnlocked(unlock);
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        userRepository.delete(user);
    }
}
