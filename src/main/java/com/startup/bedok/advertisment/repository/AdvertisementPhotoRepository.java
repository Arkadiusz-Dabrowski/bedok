package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.AdvertisementPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdvertisementPhotoRepository extends MongoRepository<AdvertisementPhoto, String>{
}
