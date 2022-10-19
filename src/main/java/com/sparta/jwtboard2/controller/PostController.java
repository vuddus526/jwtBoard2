package com.sparta.jwtboard2.controller;

import com.sparta.jwtboard2.dto.responseDto.PostListResponseDto;
import com.sparta.jwtboard2.dto.requestDto.PostRequestDto;
import com.sparta.jwtboard2.dto.responseDto.PostResponseDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.security.UserDetailsImpl;
import com.sparta.jwtboard2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 글 작성
    @PostMapping("/auth/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println(postRequestDto.getTitle());
        System.out.println(postRequestDto.getContents());
        return postService.createPost(postRequestDto, userDetailsImpl.getUser().getEmail());
    }

    // 글 전체보기
    @GetMapping("/posts")
    public ResponseDto<?> getPostAll() {
        return postService.getPostAll();
    }

    // 글 상세보기
    @GetMapping("/posts/{id}")
    public ResponseDto<?> getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    // 글 수정
    @PostMapping("/auth/posts/{id}")
    public ResponseDto<?> updataPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.updatePost(id, postRequestDto, userDetailsImpl.getUser().getEmail());
    }

    // 글 삭제
    @DeleteMapping("/auth/posts/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.deletePost(id, userDetailsImpl.getUser().getEmail());
    }

    // 좋아요 기능
    @PostMapping("/auth/posts/{id}/like")
    public boolean likeUp(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.likeUp(id, userDetailsImpl.getUser().getEmail());
    }
}
