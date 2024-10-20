package com.startup.bedok.user.controller;

import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.user.model.*;
import com.startup.bedok.user.notification.NotificationDTO;
import com.startup.bedok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("host")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    private ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody UserDTO userDTO) throws IOException {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PutMapping()
    private ResponseEntity<SimpleResponse> updateUser(@Valid @RequestBody UserUpdateDTO userDTO,
                                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUser(userDTO, token));
    }
    @PostMapping("photo")
    private ResponseEntity<SimpleResponse> addUserPhoto(@RequestHeader("Authorization") String token,
                                                @RequestParam MultipartFile file) {
        return ResponseEntity.ok(userService.addPhotoToUser(token, file));
    }

    @DeleteMapping("photo/delete")
    private ResponseEntity<SimpleResponse> addUserPhoto(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.deleteUserPhoto(token));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping("notifications")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getNotificationsByUser(token));
    }

    @GetMapping
    private ResponseEntity<UserResponse> getUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserResponseFromToken(token));
    }

    @PostMapping("random")
    private ResponseEntity<String> createSomeRandomHost(){
        userService.createSomeRandomUsers();
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/reset-password")
    public SimpleResponse resetPassword(@RequestParam String email) {
        return userService.generateNewPasswordAndSendToUser(email);
    }

    @PostMapping("/change-password")
    public SimpleResponse changePassword(@RequestHeader("Authorization") String token,
                                         @RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(changePasswordDTO, token);
    }

}
