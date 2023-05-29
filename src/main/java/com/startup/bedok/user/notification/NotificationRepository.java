package com.startup.bedok.user.notification;

import com.startup.bedok.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByUser(ApplicationUser user);

    List<Notification> findAllByUserId(UUID userId);
}
