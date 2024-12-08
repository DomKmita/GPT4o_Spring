package com.example.petshouseholds.controller;

import com.example.petshouseholds.dto.UserDTO;
import com.example.petshouseholds.entity.AppUser;
import com.example.petshouseholds.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // Create a new user - ADMIN ONLY
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok(new UserDTO(user.getUsername(), user.getRole(), user.isUnlocked()));
    }

    // Change password - USERS can change their own password
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String username,
                                                 @RequestParam String newPassword) {
        userService.changePassword(username, passwordEncoder.encode(newPassword));
        return ResponseEntity.ok("Password updated successfully");
    }

    // Lock or unlock user - ADMIN ONLY
    @Secured("ROLE_ADMIN")
    @PutMapping("/lock-unlock")
    public ResponseEntity<String> lockUnlockUser(@RequestParam String username,
                                                 @RequestParam boolean unlock) {
        userService.lockUnlockUser(username, unlock);
        return ResponseEntity.ok(unlock ? "User unlocked successfully" : "User locked successfully");
    }

    // Delete user - ADMIN ONLY
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}
