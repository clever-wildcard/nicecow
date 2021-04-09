package com.nicecow.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean findByUsername(String username);
    boolean findByPrimaryPhoneNumber(String phoneNumber);
    boolean findByBackupPhoneNumber(String phoneNumber);
}
