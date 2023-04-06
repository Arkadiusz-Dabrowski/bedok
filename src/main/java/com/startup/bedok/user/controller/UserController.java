package com.startup.bedok.user.controller;

import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserPhotoService;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserPhotoService userPhotoService;

    @PostMapping
    private ResponseEntity<UUID> registerUser(@Validated UserDTO userDTO) throws IOException {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @GetMapping
    private UserResponse getUser(UUID id) {
        return userService.getUserByID(id);
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomHost(){
        userService.createSomeRandomUsers();
        return ResponseEntity.ok("ok");
    }

}
