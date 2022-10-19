package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.dto.responseDto.ReplyResponseDto;
import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepositoy extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommentId(Long id);
}
