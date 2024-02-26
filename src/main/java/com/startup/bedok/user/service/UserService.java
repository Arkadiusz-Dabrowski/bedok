package com.startup.bedok.user.service;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.exceptions.HostNoExistsException;
import com.startup.bedok.exceptions.UserNoExistsException;
import com.startup.bedok.exceptions.UserWithSelectedEmailAlreadyExistsException;
import com.startup.bedok.exceptions.UserWithSelectedPhoneAlreadyExistsException;
import com.startup.bedok.user.mapper.UserMapperImpl;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.LoginDTO;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.notification.Notification;
import com.startup.bedok.user.notification.NotificationService;
import com.startup.bedok.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPhotoService userPhotoService;
    private final DataGenerator dataGenerator;
    private final NotificationService notificationService;
    private final JwtTokenUtil jwtTokenUtil;

    public UUID registerUser(UserDTO userDTO) throws IOException{
            String photoId = null;
            if(userDTO.getHostPhoto() != null) {
                photoId = userPhotoService.savePhoto(userDTO.getHostPhoto().getBytes(),
                        userDTO.getName());
            }
            ApplicationUser user = UserMapperImpl.hostDTOtoHost(userDTO, photoId);
            try {
                return userRepository.save(user).getId();
            } catch (DataIntegrityViolationException e) {
                if(e.getCause().getCause().getMessage().contains(user.getEmail()))
                throw new UserWithSelectedEmailAlreadyExistsException(user.getEmail());
                else
                    throw new UserWithSelectedPhoneAlreadyExistsException(user.getPhone());
            }
    }

    public UserResponse getUserResponseByID(UUID id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("there is no user with uuid: '%s'", id)));
        Binary userPhoto = null;
        String photoId = user.getPhotoId();
        if(photoId !=null)
        userPhoto = userPhotoService.getPhoto(photoId);

        return UserMapperImpl.userToUserResponse(user, userPhoto);
    }

    public ResponseEntity<String> login(LoginDTO loginDTO) {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            ApplicationUser user = getUserByEmail(loginDTO.getEmail());
            if (user == null || !bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            String token = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(token);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Notification> getNotificationsByUser(UUID userId){
        ApplicationUser user = userRepository.getById(userId);
        return notificationService.getNotificationsByUser(user);
    }

    public ApplicationUser getUserByID(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNoExistsException(id.toString()));
    }

    public void checkIfHostExists(UUID id) {
        if(!userRepository.existsById(id))
        throw new HostNoExistsException(id.toString());
    }

    public void createSomeRandomUsers() {
        dataGenerator.createSomeHostData();
    }


    public ApplicationUser getUserByEmail(String email) {
        return userRepository.findByPhone(email)
                .orElseGet(() -> userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("there is no user with selected email or phone number")));
    }

    private ApplicationUser getUserFromToken(String token) {
        UUID userId = jwtTokenUtil.getUserIdFromToken(token);
        return getUserByID(userId);
    }
}
