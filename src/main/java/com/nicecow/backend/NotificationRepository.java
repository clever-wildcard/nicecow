package com.nicecow.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    ArrayList<Notification> findByUserId(Long userId);

}
