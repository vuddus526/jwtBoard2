package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostIdAndUserEmail(Long postId, String userEmail);
}
