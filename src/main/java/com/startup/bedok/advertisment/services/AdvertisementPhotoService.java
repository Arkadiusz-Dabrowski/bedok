package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.AdvertisementPhoto;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import com.startup.bedok.advertisment.repository.AdvertisementPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementPhotoService {

    private final AdvertisementPhotoRepository advertisementPhotoRepository;

    @Transactional
    public List<RoomPhoto> saveAdvertisementPhotos(List<MultipartFile> roomPhotos) {
        Iterable<AdvertisementPhoto> advertisementPhotos = roomPhotos.stream().map(photo -> {
            try {
                return new AdvertisementPhoto(
                      new Binary(BsonBinarySubType.BINARY, photo.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).collect(Collectors.toList());
             List<String> photosID = advertisementPhotoRepository.saveAll(advertisementPhotos)
                     .stream()
                     .map(AdvertisementPhoto::getId)
                     .toList();

        return photosID.stream()
                .map(RoomPhoto::new)
                .toList();
    }

    @Transactional
    public List<RoomPhoto> saveAdvertisementPhotosFromBinary(List<byte[]> roomPhotos) {
        Iterable<AdvertisementPhoto> advertisementPhotos = roomPhotos.stream().map(photo -> {
            return new AdvertisementPhoto(
                    new Binary(BsonBinarySubType.BINARY, photo));

        }).collect(Collectors.toList());
        List<String> photosID = advertisementPhotoRepository.saveAll(advertisementPhotos)
                .stream()
                .map(AdvertisementPhoto::getId)
                .toList();

        return photosID.stream()
                .map(RoomPhoto::new)
                .toList();
    }
//    @Transactional
//    public List<RoomPhoto> saveAdvertisementPhotos(byte[] roomPhotos) {
//        Iterable<AdvertisementPhoto> advertisementPhotos =
//            try {
//                 new AdvertisementPhoto(
//                        new Binary(roomPhotos));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        List<String> photosID = advertisementPhotoRepository.saveAll(advertisementPhotos)
//                .stream()
//                .map(AdvertisementPhoto::getId)
//                .toList();
//
//        return photosID.stream()
//                .map(RoomPhoto::new)
//                .toList();
//    }

    public List<AdvertisementPhoto> getPhotosByIds(List<String> ids) {
        List<AdvertisementPhoto> photos = new ArrayList<>();
        ids.forEach(id -> photos.add(advertisementPhotoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("error during retrieving advertisement photo"))));
        return photos;
    }

    public AdvertisementPhoto getPhotoById(String id) {
        return advertisementPhotoRepository.findById(id).orElseThrow(() -> new RuntimeException("error during retriving advertisement photo"));
    }
}
