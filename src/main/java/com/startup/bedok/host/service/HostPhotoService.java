package com.startup.bedok.host.service;

import com.startup.bedok.host.model.HostPhoto;
import com.startup.bedok.host.repository.HostPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class HostPhotoService {

    HostPhotoRepository hostPhotoRepository;

    @Autowired
    public HostPhotoService(HostPhotoRepository hostPhotoRepository) {
        this.hostPhotoRepository = hostPhotoRepository;
    }

    public String savePhoto(MultipartFile file, String hostName)  {
        HostPhoto photo = new HostPhoto(hostName);
        try {
            photo.setImage(
                    new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } catch (IOException e) {

        }
        photo = hostPhotoRepository.insert(photo);
        return photo.getId();
    }

    public MultipartFile getHostPhoto(String id) {
       if (hostPhotoRepository.findById(id).isPresent()) {
          return (MultipartFile) hostPhotoRepository.findById(id).get().getImage();
        }
       else
           return null;
    }
}
