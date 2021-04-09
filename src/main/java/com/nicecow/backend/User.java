package com.nicecow.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String primaryPhoneNumber;
    private String backupPhoneNumber;
    public User(String username, String primaryPhoneNumber, String backupPhoneNumber) {
        this.username = username;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.backupPhoneNumber = backupPhoneNumber;
    }
//    String phone;
//    String verificationCode;
}
