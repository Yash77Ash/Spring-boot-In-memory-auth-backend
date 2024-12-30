package com.spring.security.inmemoryauthentication.controllers;

import com.spring.security.inmemoryauthentication.config.UserAuthenticationProvider;
import com.spring.security.inmemoryauthentication.dtos.CredentialsDto;
import com.spring.security.inmemoryauthentication.dtos.SignUpDto;
import com.spring.security.inmemoryauthentication.dtos.UserDto;
import com.spring.security.inmemoryauthentication.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        // Fetch user from UserService
        UserDto userDto = userService.login(credentialsDto);

        // Ensure userId is available in userDto
        if (userDto != null) {
            // Create token using both userId and login
            userDto.setToken(userAuthenticationProvider.createToken(userDto.getId(), userDto.getLogin()));
            System.out.println("UserDto----------" + userDto);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        System.out.println("I am in register");
        UserDto createdUser = userService.register(user);
        System.out.println("Received SignUpDto: " + user);

        // Create token using both userId and login
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser.getId(), createdUser.getLogin()));
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/api/username/{id}")
    public ResponseEntity<?> getLogin(@PathVariable Long id) {
        System.out.println("I am in api/username");
        String username = userService.getLogin(id);
        System.out.println("Username : "+username);
        System.out.println("id : "+id);

        if (username != null) {
            return ResponseEntity.ok(Collections.singletonMap("username", username));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found"));
        }
    }
}
