package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long id);
}
