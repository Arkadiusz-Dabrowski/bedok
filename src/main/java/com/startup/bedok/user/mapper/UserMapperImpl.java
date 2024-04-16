package com.startup.bedok.user.mapper;

import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import org.bson.types.Binary;
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

    static public UserResponse userToUserResponse(ApplicationUser user, Binary hostPhoto) {
        return new UserResponse(
                user.getName(),
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                hostPhoto,
                user.getGender()
        );
    }

    public static String encryptPassword(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}
