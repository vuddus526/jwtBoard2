package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long id);
    List<Comment> findByUserEmail(String userEmail);
}
