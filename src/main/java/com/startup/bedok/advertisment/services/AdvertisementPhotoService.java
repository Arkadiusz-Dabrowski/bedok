package com.startup.bedok.advertisment.services;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementPhotoService {

//    private final AdvertisementPhotoRepository advertisementPhotoRepository;

    @Transactional
    public List<RoomPhoto> saveAdvertisementPhotos(List<MultipartFile> roomPhotos, Advertisement advertisement) {
//        Iterable<AdvertisementPhoto> advertisementPhotos = roomPhotos.stream().map(photo -> {
//            try {
//                return new AdvertisementPhoto(
//                        new Binary(BsonBinarySubType.BINARY, photo.getBytes()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toList());
//
//            List<String> photosID = advertisementPhotoRepository.saveAll(advertisementPhotos)
//                    .stream()
//                    .map(AdvertisementPhoto::getId)
//                    .toList();
//
//            return photosID.stream()
//                    .map(id -> new RoomPhoto(id, advertisement))
//                    .toList();
        return null;
    }
//
//    public List<AdvertisementPhoto> getPhotosByIds(List<String> ids) {
//        List<AdvertisementPhoto> photos = new ArrayList<>();
//        ids.forEach(id -> photos.add(advertisementPhotoRepository
//                .findById(id)
//                .orElseThrow(() -> new AdvertisementPhotoNoExistsException(id))));
//        return photos;
//    }
//
//    public AdvertisementPhoto getPhotoById(String id) {
//        return advertisementPhotoRepository.findById(id).orElseThrow(() -> new AdvertisementPhotoNoExistsException(id));
//    }
//
//    public void deletePhotoFromAdvertisement(RoomPhoto roomPhoto) {
//        advertisementPhotoRepository.deleteById(roomPhoto.getPhotoId());
//    }
//
//    public void deletePhotoFromAdvertisement(String id) {
//        advertisementPhotoRepository.deleteById(id);
//    }
//
//    public List<AdvertisementPhoto> getPhotos(List<RoomPhoto> photos) {
//        return photos.stream().map(photo -> {
//            return advertisementPhotoRepository.findById(photo.getPhotoId()).orElseThrow(() -> new AdvertisementPhotoNoExistsException(photo.getPhotoId()));
//        }).toList();
//    }
}
