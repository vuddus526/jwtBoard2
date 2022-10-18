package com.sparta.jwtboard2.controller;

import com.sparta.jwtboard2.dto.requestDto.CommentRequestDto;
import com.sparta.jwtboard2.dto.responseDto.CommentResponseDto;
import com.sparta.jwtboard2.security.UserDetailsImpl;
import com.sparta.jwtboard2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("/auth/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.createComment(commentRequestDto, userDetailsImpl.getUser().getEmail());
    }

    //댓글 수정
    @PostMapping("/auth/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IllegalAccessException {
        return commentService.updateComment(id, commentRequestDto, userDetailsImpl.getUser().getEmail());
    }

    //댓글 삭제
    @DeleteMapping("/auth/comments/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(id, userDetailsImpl.getUser().getEmail());
    }


    //댓글 전체목록 보기
    @GetMapping("/comments/{id}")
    public List<CommentResponseDto> getCommentAllOfPost(@PathVariable Long id) {
        return commentService.getCommentAllOfPost(id);
    }
}
