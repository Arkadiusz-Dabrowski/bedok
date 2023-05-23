package com.startup.bedok.user.controller;

import com.startup.bedok.security.JwtTokenUtil;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.LoginDTO;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.service.UserPhotoService;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserPhotoService userPhotoService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("register")
    private ResponseEntity<UUID> registerUser(@Validated UserDTO userDTO) throws IOException {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        ApplicationUser user = userService.getUserByEmail(loginDTO.getEmail());
        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(token);
    }

    @GetMapping
    private UserResponse getUser(UUID id) {
        return userService.getUserResponseByID(id);
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomHost(){
        userService.createSomeRandomUsers();
        return ResponseEntity.ok("ok");
    }

}
