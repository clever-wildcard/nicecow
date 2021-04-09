package com.nicecow.backend;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class EndpointsController {
    private UserRepository userRepository;
    public PostRepository postRepository;

    @Autowired
    public EndpointsController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/user")
    public User createAccount(@RequestBody User user) {
        return this.userRepository.save(user);
    }
    
    @GetMapping("/users")
    public ResponseEntity<HashMap> getUsers() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.getName());
            jsonObject.put("id", user.getUserId());
            ArrayList<Post> userPosts = postRepository.findByUserId(user.getUserId());
            ArrayList<Long> userPostIds = new ArrayList<>();
            for (int i = 0; i < userPosts.size(); i++) {
                userPostIds.add(userPosts.get(i).getPostId());
            }
            jsonObject.put("postIds", userPostIds);
            jsonObjects.add(jsonObject);
        }
        HashMap<String, List<JSONObject>> jsonMap = new HashMap<>();
        jsonMap.put("users", jsonObjects);
        return ResponseEntity.ok(jsonMap);
    }

    @GetMapping("/user")
    public User getUser(@RequestBody Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return user;
    }

    @PutMapping("/user")
    public User editUserInfo(@RequestBody JSONObject edittedUserInfo) {

        userRepository.findById((Long) edittedUserInfo.get("userId")).orElseThrow(() -> new ResourceNotFoundException("User id " + edittedUserInfo.get("userId") + " not found."));

        if (edittedUserInfo.get("editingUsername").equals("True") && !userRepository.findByUsername(String.valueOf(edittedUserInfo.get("username")))) {
            this.userRepository.findById(Long.valueOf(String.valueOf(edittedUserInfo.get("userId"))))
                    .map(user -> {
                        user.setUsername(String.valueOf(edittedUserInfo.get("username")));
                        return this.userRepository.save(user);
                    });
        }
        else {
            throw new ResourceTakenException("So sorry, that username is taken, please try another one.");
        }

        if (edittedUserInfo.get("editingPrimaryPhoneNumber").equals("True") && !userRepository.findByPrimaryPhoneNumber(String.valueOf(edittedUserInfo.get("primaryPhoneNumber")))) {
            this.userRepository.findById(Long.valueOf(String.valueOf(edittedUserInfo.get("userId"))))
                    .map(user -> {
                        user.setPrimaryPhoneNumber(String.valueOf(edittedUserInfo.get("primaryPhoneNumber")));
                        return this.userRepository.save(user);
                    });
        }
        else {
            throw new ResourceTakenException("So sorry, that phone number is listed as primary on another account. " +
                    "You have some options. You can: " +
                    "0) use that account instead;" +
                    "1) log into that other account, change that primary phone number, come back here and change this primary phone number like you wanted to do this time; " +
                    "2) use another primary phone number for this account." +
                    "If you're interested in knowing why we're enforcing a one-primary-number-one-account rule, the answer is that we're hoping it makes it harder for scammers to scam the good people using this site. " +
                    "That does not mean we can guarantee you a scammer-free experience. Please continue to be careful :). ");
        }

        if (edittedUserInfo.get("editingBackupPhoneNumber").equals("True") && !userRepository.findByBackupPhoneNumber(String.valueOf(edittedUserInfo.get("backupPhoneNumber")))) {
            this.userRepository.findById(Long.valueOf(String.valueOf(edittedUserInfo.get("userId"))))
                    .map(user -> {
                        user.setBackupPhoneNumber(String.valueOf(edittedUserInfo.get("backupPhoneNumber")));
                        return this.userRepository.save(user);
                    });
        }
        else {
            throw new ResourceTakenException("So sorry, that phone number is listed as backup on another account. " +
                    "You have some options. You can: " +
                    "0) log into that other account, change that backup phone number, come back here and change this backup phone number like you wanted to do this time; " +
                    "1) use another backup phone number for this account." +
                    "If you're interested in knowing why we're enforcing a one-backup-number-one-account rule, the answer is that we're hoping it makes it harder for scammers to scam the good people using this site. " +
                    "That does not mean we can guarantee you a scammer-free experience. Please continue to be careful :). ");
        }

        User user = this.userRepository.findById((Long) edittedUserInfo.get("userId"));
        return user;

    }

    @DeleteMapping("/user")
    public JSONObject removeUser(@RequestBody Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " + userId)
        );
        this.userRepository.delete(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("delete", "User formerly associated with id " + userId +  " was deleted.");
        return jsonObject;
    }

    // Posts (you know, in case you search for 'Posts' instead of 'post'.)
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        return this.postRepository.save(post);
    }

    @GetMapping("/posts")
    public List<Post> posts() {
        return this.postRepository.findAll();
    }

    @PutMapping("/post")
    public Post editPost(@RequestBody Post edittedPost) {
        return this.postRepository.findById(edittedPost.getPostId())
                .map(post -> {
                    post.setPostContent(edittedPost.getPostContent());
                    post.setPostTitle(edittedPost.getPostTitle());
                    return postRepository.save(post);
                }).orElseGet()
    }

    @DeleteMapping("/post")
    public String deletePost(@P)
}
