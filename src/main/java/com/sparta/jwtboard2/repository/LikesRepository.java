package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostIdAndUserEmail(Long postId, String userEmail);
    List<Likes> findAllByUserEmail(String userEmail);
}
