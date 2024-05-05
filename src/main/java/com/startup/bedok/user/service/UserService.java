package com.startup.bedok.user.service;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.exceptions.*;
import com.startup.bedok.user.mapper.UserMapperImpl;
import com.startup.bedok.user.model.*;
import com.startup.bedok.user.notification.Notification;
import com.startup.bedok.user.notification.NotificationService;
import com.startup.bedok.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

    public RegistrationResponse registerUser(UserDTO userDTO) throws IOException {
        String photoId = null;
        if (userDTO.getHostPhoto() != null) {
            photoId = userPhotoService.savePhoto(userDTO.getHostPhoto().getBytes(),
                    userDTO.getName());
        }
        ApplicationUser user = UserMapperImpl.hostDTOtoHost(userDTO, photoId);
        try {
            return new RegistrationResponse(userRepository.save(user).getId());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains(user.getEmail()))
                throw new UserWithSelectedEmailAlreadyExistsException(user.getEmail());
            else
                throw new UserWithSelectedPhoneAlreadyExistsException(user.getPhone());
        }
    }

    @Transactional
    public String addPhotoToUser(UUID id, MultipartFile photo) throws IOException {
        ApplicationUser user = userRepository.findById(id).orElseThrow(() -> new UserNoExistsException(id.toString()));
        String photoId;
        photoId = userPhotoService.savePhoto(photo.getBytes(),
                user.getName());
        user.setPhotoId(photoId);
        userRepository.save(user);
        return photoId;
    }

    public UserResponse getUserResponseByID(UUID id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("there is no user with uuid: '%s'", id)));
        Binary userPhoto = null;
        String photoId = user.getPhotoId();
        if (photoId != null)
            userPhoto = userPhotoService.getPhoto(photoId);

        return UserMapperImpl.userToUserResponse(user, userPhoto);
    }

    public LoginResponse login(LoginDTO loginDTO) {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            ApplicationUser user = getUserByEmail(loginDTO.getEmail());
            if (user == null || !bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                throw new AuthenticationException();
            }
            String token = jwtTokenUtil.generateToken(user);
            return new LoginResponse(token);
        } catch (Exception e) {
            throw new AuthenticationException();
        }
    }

    public List<Notification> getNotificationsByUser(UUID userId) {
        ApplicationUser user = userRepository.getById(userId);
        return notificationService.getNotificationsByUser(user);
    }

    public ApplicationUser getUserByID(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNoExistsException(id.toString()));
    }

    public void checkIfHostExists(UUID id) {
        if (!userRepository.existsById(id))
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
