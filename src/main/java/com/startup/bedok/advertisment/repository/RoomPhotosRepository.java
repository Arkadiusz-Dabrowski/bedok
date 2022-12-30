package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.RoomPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomPhotosRepository extends JpaRepository<RoomPhoto, UUID> {

}
