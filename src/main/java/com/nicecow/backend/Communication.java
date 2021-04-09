package com.nicecow.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Communication {
    @Id
    @GeneratedValue
    private Long communicationId;
    private Long senderId;
    private Long receiverId;
    private String words;
    private boolean privateMessage; // if this is true, the words go in a private message. If this is false, the words will be accompanied by, in additoin to a senderId and receiver Id, a postId.
    private Long postId;
    private LocalTime timestamp;
    private String reaction;

    public Communication(String words, Long postId, Long senderId, Long receiverId) {
        this.words = words;
        this.postId = postId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = LocalTime.now();
    }
    public Communication(String words, boolean privateMessage, Long senderId, Long receiverId) {
        this.words = words;
        this.privateMessage = privateMessage;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = LocalTime.now();
    }
    public Communication(String reaction, Long senderId, Long receiverId) {
        this.reaction = reaction;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = LocalTime.now();
    }



//    https://dev.to/piczmar_0/java-optional-in-class-fields-why-not-40df
//    public Optional<String> setPrivateMessage() {
//        return Optional.ofNullable();
//    }
}
