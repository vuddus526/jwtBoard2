package com.sparta.jwtboard2.controller;

import com.sparta.jwtboard2.dto.requestDto.CommentRequestDto;
import com.sparta.jwtboard2.dto.requestDto.ReplyRequestDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.security.UserDetailsImpl;
import com.sparta.jwtboard2.service.CommentService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "댓글 쓰기", notes = "댓글 쓰기 API")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.createComment(commentRequestDto, userDetailsImpl.getUser().getEmail());
    }

    //댓글 수정
    @PostMapping("/auth/comments/{id}")
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정 API")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IllegalAccessException {
        return commentService.updateComment(id, commentRequestDto, userDetailsImpl.getUser().getEmail());
    }

    //댓글 삭제
    @DeleteMapping("/auth/comments/{id}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 API")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(id, userDetailsImpl.getUser().getEmail());
    }

    //댓글 전체목록 보기
    @GetMapping("/comments/{id}")
    @ApiOperation(value = "댓글 전체보기", notes = "댓글 전체보기 API")
    public ResponseDto<?> getCommentAllOfPost(@PathVariable Long id) {
        return commentService.getCommentAllOfPost(id);
    }

    // 대댓글 작성
    @PostMapping("/auth/reply/{id}")
    @ApiOperation(value = "대댓글 작성", notes = "대댓글 작성 API")
    public ResponseDto<?> createReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.createReply(id, replyRequestDto, userDetailsImpl.getUser().getEmail());
    }
}
