package com.startup.bedok.advertisment.repository;

import com.startup.bedok.advertisment.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {
   List<Advertisement> findAllByHostId(UUID hostId);
}
