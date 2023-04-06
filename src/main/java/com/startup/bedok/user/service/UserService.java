package com.startup.bedok.user.service;

import com.startup.bedok.datahelper.DataGenerator;
import com.startup.bedok.user.entity.TypeOfUser;
import com.startup.bedok.user.exception.HostNoExistsException;
import com.startup.bedok.user.mapper.UserMapperImpl;
import com.startup.bedok.user.model.ApplicationUser;
import com.startup.bedok.user.model.UserDTO;
import com.startup.bedok.user.model.UserResponse;
import com.startup.bedok.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPhotoService userPhotoService;
    private final DataGenerator dataGenerator;

    @Transactional
    public UUID registerUser(UserDTO userDTO) throws IOException {
        try {
            String photoId = null;
            if(userDTO.getHostPhoto() != null) {
                photoId = userPhotoService.savePhoto(userDTO.getHostPhoto().getBytes(),
                        userDTO.getName());
            }
            ApplicationUser user = UserMapperImpl.hostDTOtoHost(userDTO, photoId);
            return userRepository.save(user).getId();
        } catch (IOException ioException) {
            throw new IOException("Error during host creation", ioException.getCause());
        }
    }

    public UserResponse getUserByID(UUID id) {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("there is no user with uuid: '%s'", id)));
        Binary userPhoto = null;
        String photoId = user.getPhotoId();
        if(photoId !=null)
        userPhoto = userPhotoService.getPhoto(photoId);

        return UserMapperImpl.userToUserResponse(user, userPhoto);
    }

    public void checkIfHostExists(UUID id) {
        if(!userRepository.existsByIdAndTypeOfUser(id, TypeOfUser.HOST))
        throw new HostNoExistsException(id.toString());
    }

    public void createSomeRandomUsers() {
        dataGenerator.createSomeHostData();
    }



}
