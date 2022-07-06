package com.startup.bedok.host.repository;

import com.startup.bedok.host.model.HostPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HostPhotoRepository extends MongoRepository<HostPhoto, String> {
}