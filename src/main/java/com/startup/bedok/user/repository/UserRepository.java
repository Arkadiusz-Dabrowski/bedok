package com.startup.bedok.user.repository;

import com.startup.bedok.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {
    boolean existsById(UUID id);

    Optional<ApplicationUser> findByEmail(String email);

    Optional<ApplicationUser> findByPhone(String email);

}
