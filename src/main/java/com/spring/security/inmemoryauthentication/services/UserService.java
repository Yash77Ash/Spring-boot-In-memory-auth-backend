package com.spring.security.inmemoryauthentication.services;

import com.spring.security.inmemoryauthentication.dtos.CredentialsDto;
import com.spring.security.inmemoryauthentication.dtos.SignUpDto;
import com.spring.security.inmemoryauthentication.dtos.UserDto;
import com.spring.security.inmemoryauthentication.entites.User;
import com.spring.security.inmemoryauthentication.exceptions.AppException;
import com.spring.security.inmemoryauthentication.mappers.UserMapper;
import com.spring.security.inmemoryauthentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        System.out.println("Login Attempt: " + credentialsDto.getLogin());

        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {

        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        User user= userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }


    public String getLogin(Long id) {
        return userRepository.findById(id).map(User::getLogin).orElse(null);
    }

    public UserDto findById(Long userId) {
        System.out.println("return by findById");
        return userRepository.findById(userId)
                .map(user1-> new UserDto(user1.getId(), user1.getLogin() ))  // Create and return UserDto
                .orElse(null); // Or handle user not found appropriately
    }
}
