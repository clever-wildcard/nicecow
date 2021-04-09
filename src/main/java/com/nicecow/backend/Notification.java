package com.nicecow.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue
    Long notificationId;
    Long postOrMessageId;
    Long messageId;

    public Notification(Long postId) {
        this.postId = postId;
    }
    Notification(Long messageId) {
        this.messageId;
    }
}
