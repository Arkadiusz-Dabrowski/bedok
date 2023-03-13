package com.startup.bedok.user.repository;

import com.startup.bedok.user.model.UserPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPhotoRepository extends MongoRepository<UserPhoto, String> {
}