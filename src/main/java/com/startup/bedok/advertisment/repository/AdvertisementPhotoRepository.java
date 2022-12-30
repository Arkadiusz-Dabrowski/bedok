package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.AdvertisementPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdvertisementPhotoRepository extends MongoRepository<AdvertisementPhoto, String>{
}
