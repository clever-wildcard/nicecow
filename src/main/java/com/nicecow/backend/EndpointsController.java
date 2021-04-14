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
    public CommunicationRepository communicationRepository;

    @Autowired
    public EndpointsController(UserRepository userRepository, PostRepository postRepository, CommunicationRepository communicationRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.communicationRepository = communicationRepository;
    }

    @DeleteMapping("/api/users/{userId}")
    public JSONObject removeUser(@PathVariable Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " + userId)
        );
        this.userRepository.delete(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deletionStatus", "User formerly associated with id " + userId +  " was deleted.");
        return jsonObject;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/api/users")
    public ResponseEntity<HashMap> getUsers() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.getUsername());
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

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/api/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return user;
    }

    @PostMapping("/api//users")
    public User createAccount(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @PutMapping("/api/users/{userId}")
    public User editUserInfo(@PathVariable Long userId, @RequestBody JSONObject edittedUserInfo) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id " + edittedUserInfo.get("userId") + " not found."));

        if (edittedUserInfo.get("editingUsername").equals("True") && !userRepository.findByUsername(edittedUserInfo.getAsString("username"))) {
           user.setUsername(edittedUserInfo.getAsString("username"));
        }
        else {
            throw new ResourceTakenException("So sorry, that username is taken, please try another one.");
        }

        if (edittedUserInfo.get("editingPrimaryPhoneNumber").equals("True") && !userRepository.findByPrimaryPhoneNumber(edittedUserInfo.getAsString("primaryPhoneNumber"))) {
            user.setPrimaryPhoneNumber(edittedUserInfo.getAsString("primaryPhoneNumber"));
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

        if (edittedUserInfo.get("editingBackupPhoneNumber").equals("True") && !userRepository.findByBackupPhoneNumber(edittedUserInfo.getAsString("backupPhoneNumber"))) {
          user.setBackupPhoneNumber(edittedUserInfo.getAsString("backupPhoneNumber"));
        }
        else {
            throw new ResourceTakenException("So sorry, that phone number is listed as backup on another account. " +
                    "You have some options. You can: " +
                    "0) log into that other account, change that backup phone number, come back here and change this backup phone number like you wanted to do this time; " +
                    "1) use another backup phone number for this account." +
                    "If you're interested in knowing why we're enforcing a one-backup-number-one-account rule, the answer is that we're hoping it makes it harder for scammers to scam the good people using this site. " +
                    "That does not mean we can guarantee you a scammer-free experience. Please continue to be careful :). ");
        }
          return this.userRepository.save(user);
    }

    // Posts

    @DeleteMapping("/api/posts/{postId}")
    public JSONObject deletePost(@PathVariable Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("The requested postId could not be found."));
        this.postRepository.delete(post);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deletionStatus", "Post formerly associated with id " + postId +  " was deleted.");
        return jsonObject;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/api/posts")
    public JSONObject posts() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("posts", this.postRepository.findAll());
        return jsonObject;
    }

//    @CrossOrigin(origins = "http://localhost:63342")
//    @GetMapping("/api/posts/{postId}")

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post) {
        return this.postRepository.save(post);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @PutMapping("/api/posts/{postId}")
    public Post editPost(@PathVariable Long postId, @RequestBody Post edittedPost) {
        return this.postRepository.findById(postId)
                .map(post -> {
                    post.setPostContent(edittedPost.getPostContent());
                    post.setPostTitle(edittedPost.getPostTitle());
                    return postRepository.save(post);
                }).orElseThrow(() -> new com.nicecow.backend.ResourceNotFoundException("The requested postId could not be found."));
    }

    // Communications

    @PostMapping("/api/communications")
    public Communication communicate(Communication communication) {
        return this.communicationRepository.save(communication);
    }
}
