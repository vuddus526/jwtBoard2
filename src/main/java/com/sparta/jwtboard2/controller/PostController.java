package com.sparta.jwtboard2.controller;

import com.sparta.jwtboard2.dto.requestDto.PostRequestDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.security.UserDetailsImpl;
import com.sparta.jwtboard2.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 글 작성
    @PostMapping("/auth/posts")
    @ApiOperation(value = "글 작성", notes = "글 작성 API")
    public ResponseDto<?> createPost(@RequestPart(required = false,value = "file") List<MultipartFile> multipartFile,
                                     @RequestPart(value = "postRequestDto") PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        return postService.createPost(multipartFile, postRequestDto, userDetailsImpl.getUser().getEmail());
    }

    // 테스트 글 작성
    @PostMapping("/auth/testPosts")
    public ResponseDto<?> testPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.testPost(postRequestDto, userDetailsImpl.getUser().getEmail());
    }

    // 글 전체보기
    @GetMapping("/posts")
    @ApiOperation(value = "글 전체보기", notes = "글 전체보기 API")
    public ResponseDto<?> getPostAll() {
        return postService.getPostAll();
    }

    // 글 상세보기
    @GetMapping("/posts/{id}")
    @ApiOperation(value = "글 상세보기", notes = "글 상세보기 API")
    public ResponseDto<?> getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    // 글 수정
    @PostMapping("/auth/posts/{id}")
    @ApiOperation(value = "글 수정", notes = "글 수정하기 API")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.updatePost(id, postRequestDto, userDetailsImpl.getUser().getEmail());
    }

    // 글 삭제
    @DeleteMapping("/auth/posts/{id}")
    @ApiOperation(value = "글 삭제", notes = "글 삭제하기 API")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.deletePost(id, userDetailsImpl.getUser().getEmail());
    }

    // 좋아요 기능
    @PostMapping("/auth/posts/{id}/like")
    @ApiOperation(value = "좋아요 기능", notes = "좋아요 기능 API")
    public boolean likeUp(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.likeUp(id, userDetailsImpl.getUser().getEmail());
    }

    // 키워드 검색기능
    @GetMapping("/posts/search")
    public ResponseDto<?> searchKeyword(@RequestParam String keyword) {
        return postService.searchKeyword(keyword);
    }
}
