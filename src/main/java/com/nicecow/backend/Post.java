package com.nicecow.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@NamedQuery(name = "Post.findByUserId",
//query = "select p from Post p where p.userId = ?1")
public class Post {
    @Id
    @GeneratedValue
    Long postId;
    Long userId;
    String postContent;
    String postTitle;
    LocalTime timestamp;


    public Post(Long userId, String postContent, String postTitle) {
        this.userId = userId;
        this.postContent = postContent;
        this.postTitle = postTitle;
        this.timestamp = LocalTime.now();
    }
}
