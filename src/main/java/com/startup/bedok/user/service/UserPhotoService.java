package com.startup.bedok.user.service;

import com.startup.bedok.user.model.UserPhoto;
import com.startup.bedok.user.repository.UserPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPhotoService {

    UserPhotoRepository userPhotoRepository;

    @Autowired
    public UserPhotoService(UserPhotoRepository userPhotoRepository) {
        this.userPhotoRepository = userPhotoRepository;
    }

    public String savePhoto(byte[] file, String hostName)  {
        UserPhoto photo = new UserPhoto(hostName);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file));
        photo = userPhotoRepository.insert(photo);
        return photo.getId();
    }

    public Binary getPhoto(String id) {
       if (userPhotoRepository.findById(id).isPresent()) {
          return userPhotoRepository.findById(id).get().getImage();
        }
       else
           return null;
    }
}
