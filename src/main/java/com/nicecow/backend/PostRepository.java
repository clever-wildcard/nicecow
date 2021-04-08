package com.nicecow.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
//import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    ArrayList<Post> findByUserId(Long userId);
}
