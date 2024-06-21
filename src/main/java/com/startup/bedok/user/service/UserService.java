package com.startup.bedok.user.service;

import com.startup.bedok.config.JwtTokenUtil;
import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.email.EmailService;
import com.startup.bedok.exceptions.*;
import com.startup.bedok.global.SimpleResponse;
import com.startup.bedok.user.mapper.UserMapperImpl;
import com.startup.bedok.user.model.*;
import com.startup.bedok.user.notification.NotificationAcceptanceDTO;
import com.startup.bedok.user.notification.NotificationService;
import com.startup.bedok.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.Binary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.startup.bedok.user.mapper.UserMapperImpl.encryptPassword;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPhotoService userPhotoService;
    private final DataGenerator dataGenerator;
    private final NotificationService notificationService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

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
    public SimpleResponse addPhotoToUser(String token, MultipartFile photo)  {
        UUID id = getUserIdFromToken(token);
        ApplicationUser user = userRepository.findById(id).orElseThrow(() -> new UserNoExistsException(id.toString()));
        String photoId;
        try {
            photoId = userPhotoService.savePhoto(photo.getBytes(),
                    user.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setPhotoId(photoId);
        userRepository.save(user);
        return new SimpleResponse("User photo - updated");
    }

    public UserResponse getUserResponseFromToken(String token) {
        UUID id = getUserIdFromToken(token);
        return getUserResponseByID(id);
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

    public List<NotificationAcceptanceDTO> getNotificationsByUser(String token) {
        return notificationService.getUserAcceptanceNotifications(token);
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
                        .orElseThrow(() -> new UserWithEmailNotExistsException(email)));
    }

    private UUID getUserIdFromToken(String token) {
        return jwtTokenUtil.getUserIdFromToken(token);
    }

    @Transactional
    public SimpleResponse generateNewPasswordAndSendToUser(String email){
        ApplicationUser user = getUserByEmail(email);
        String message = "Twoje nowe hasło dateTo: ";
        String upperCaseLetters = RandomStringUtils.random(6, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(6, 97, 122, true, true);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters);

        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c).toList();
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        createNewPassword(password, user);
        userRepository.save(user);
        emailService.sendNewPassword(email, "password-reset-bedok", message + password);
        return new SimpleResponse("password has been send");
    }

    private void createNewPassword(String password, ApplicationUser user){
        String encryptedPassword = encryptPassword(password);
        user.setPassword(encryptedPassword);
    }

    @Transactional
    public SimpleResponse changePassword(ChangePasswordDTO changePasswordDTO, String token) {
        UUID userId = getUserIdFromToken(token);
        ApplicationUser user = userRepository.findById(userId).orElseThrow(() -> new UserNoExistsException(userId.toString()));
        checkIfPasswordIsCorrect(user.getPassword(), changePasswordDTO.oldPassword());
        user.setPassword(changePasswordDTO.newPassword());
        return new SimpleResponse("password has been changed");
    }

    private void checkIfPasswordIsCorrect(String orgilnalPassword, String passwordFromForm) {
        if(!orgilnalPassword.equals(passwordFromForm))
            throw new RuntimeException("password is incorrect");
    }
}
