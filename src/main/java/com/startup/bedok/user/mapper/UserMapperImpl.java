package com.startup.bedok.user.mapper;

import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.model.UserShortResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class UserMapperImpl {

    static public ApplicationUser hostDTOtoHost(UserDTO userDTO, String photoId) {
        return new ApplicationUser(
                userDTO.getName(),
                userDTO.getGender(),
                encryptPassword(userDTO.getPassword()),
                userDTO.getEmail(),
                userDTO.getPhone(),
                photoId,
                userDTO.getDateOfBirth(),
                userDTO.getLanguage(),
                userDTO.isViber(),
                userDTO.isSignal(),
                userDTO.isWhatsapp(),
                userDTO.isTelegram()
        );
    }

    static public ApplicationUser hostDTOtoHost(UserDTO userDTO) {
        return new ApplicationUser(
                userDTO.getName(),
                userDTO.getGender(),
                encryptPassword(userDTO.getPassword()),
                userDTO.getEmail(),
                userDTO.getPhone(),
                null,
                userDTO.getDateOfBirth(),
                userDTO.getLanguage(),
                userDTO.isViber(),
                userDTO.isSignal(),
                userDTO.isWhatsapp(),
                userDTO.isTelegram()
        );
    }

    static public UserResponse userToUserResponse(ApplicationUser user, String hostPhoto) {
        return new UserResponse(
                user.getName(),
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                hostPhoto,
                user.getGender()
        );
    }

    static public UserShortResponse userToUserShortResponse(ApplicationUser user) {
        return new UserShortResponse(
                user.getName(),
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                user.getGender()
        );
    }

    public static String encryptPassword(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}
