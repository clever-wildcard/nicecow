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
public class UserController {
    private UserRepository userRepository;
    public PostRepository postRepository;

    @Autowired
    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/create-account")
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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(value = "userId") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User newUser, @PathVariable(value = "userId") Long userId) {
        return this.userRepository.findById(userId)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setSurname(newUser.getSurname());
                    user.setEmail(newUser.getEmail());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return this.userRepository.save(user);

                }).orElseGet(() -> {
                    newUser.setUserId(userId);
                    return this.userRepository.save(newUser);
                });
    }

    @DeleteMapping("/{userId")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "userId") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " + userId)
        );
        this.userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    // User posts go under UserController?
    @PostMapping("/create-post")
    public Post createPost(@RequestBody Post post) {
        return this.postRepository.save(post);
    }

    @GetMapping("/posts")
    public List<Post> posts() {
        return this.postRepository.findAll();
    }
}
