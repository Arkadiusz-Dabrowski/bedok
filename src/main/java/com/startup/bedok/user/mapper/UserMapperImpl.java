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
                userDTO.getTypeOfUser(),
                userDTO.getName(),
                encryptPassword(userDTO.getPassword()),
                userDTO.getEmail(),
                userDTO.getPhone(),
                photoId
        );
    }

    static public UserResponse userToUserResponse(ApplicationUser user, Binary hostPhoto) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                hostPhoto
        );
    }

    private static String encryptPassword(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}
