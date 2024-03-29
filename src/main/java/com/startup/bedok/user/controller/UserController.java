package com.startup.bedok.user.controller;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.user.model.LoginDTO;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.notification.Notification;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("register")
    private ResponseEntity<UUID> registerUser(@Valid @RequestBody UserDTO userDTO) throws IOException {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("notifications")
    public ResponseEntity<List<Notification>> getNotifications(@RequestHeader("Authorization") String token){
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return ResponseEntity.ok(userService.getNotificationsByUser(userId));
    }

    @GetMapping
    private UserResponse getUser(@RequestParam UUID id) {
        return userService.getUserResponseByID(id);
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomHost(){
        userService.createSomeRandomUsers();
        return ResponseEntity.ok("ok");
    }

}
