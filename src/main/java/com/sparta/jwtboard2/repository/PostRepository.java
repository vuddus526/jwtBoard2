package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Post;
import com.sparta.jwtboard2.repository.custom.PostRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByOrderByModifiedAtDesc();
    List<Post> findByUserEmail(String userEmail);
}
