package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.AdvertisementPhoto;
import com.startup.bedok.advertisment.repository.AdvertisementPhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertisementPhotoService {

    private AdvertisementPhotoRepository advertisementPhotoRepository;
    public List<String> saveAdvertisementPhotos(List<MultipartFile> roomPhotos) {
        int photoNumber = 1;
        List photosID = new ArrayList<String>();
        roomPhotos.forEach(photo -> {
            try {
                AdvertisementPhoto advertisementPhoto =
                        new AdvertisementPhoto(photoNumber,
                                new Binary(BsonBinarySubType.BINARY, photo.getBytes()));
                photosID.add(advertisementPhotoRepository.save(advertisementPhoto));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return photosID;
    }
}
