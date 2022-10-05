package com.startup.bedok.host.service;

import com.startup.bedok.host.model.HostPhoto;
import com.startup.bedok.host.repository.HostPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostPhotoService {

    HostPhotoRepository hostPhotoRepository;

    @Autowired
    public HostPhotoService(HostPhotoRepository hostPhotoRepository) {
        this.hostPhotoRepository = hostPhotoRepository;
    }

    public String savePhoto(byte[] file, String hostName)  {
        HostPhoto photo = new HostPhoto(hostName);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file));
        photo = hostPhotoRepository.insert(photo);
        return photo.getId();
    }

    public Binary getHostPhoto(String id) {
       if (hostPhotoRepository.findById(id).isPresent()) {
          return hostPhotoRepository.findById(id).get().getImage();
        }
       else
           return null;
    }
}
