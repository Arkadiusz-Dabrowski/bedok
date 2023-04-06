package com.startup.bedok.guest.repository;

import com.startup.bedok.guest.model.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {
}
