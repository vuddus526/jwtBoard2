package com.sparta.jwtboard2.service;

import com.sparta.jwtboard2.dto.responseDto.CommentResponseDto;
import com.sparta.jwtboard2.dto.requestDto.PostRequestDto;
import com.sparta.jwtboard2.dto.responseDto.PostResponseDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.entity.*;
import com.sparta.jwtboard2.exception.PostNotFoundException;
import com.sparta.jwtboard2.exception.UserNotEqualsException;
import com.sparta.jwtboard2.exception.UserNotFoundException;
import com.sparta.jwtboard2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final ReplyRepositoy replyRepositoy;

    // username을 이용해서 User 객체 만들기 및 유저정보 확인 ( user 사용해서)
    private User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException() );
        return user;
    }

    // 글작성
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, String email) {
        User user = getUser(email);

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .email(post.getUser().getEmail())
                        .createAt(post.getCreateAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    // 글 전체보기
    @Transactional(readOnly =true)
    public ResponseDto<?> getPostAll() {
         return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
    }

    // 글 상세보기
    public ResponseDto<?> getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new PostNotFoundException());

        // 해당 글의 댓글 찾기
        List<Comment> commentList = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : commentList) {
                commentResponseDtoList.add(
                        CommentResponseDto.builder()
                                .id(comment.getId())
                                .email(comment.getUser().getEmail())
                                .comment(comment.getComment())
                                .replyResponseDtoList(replyRepositoy.findAllByCommentId(comment.getId()))
                                .createAt(comment.getCreateAt())
                                .modifiedAt(comment.getModifiedAt())
                                .build()
                );
        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .commentResponseDtoList(commentResponseDtoList)
                        .createAt(post.getCreateAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    // 글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto postRequestDto, String email) {
        User user = getUser(email);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new PostNotFoundException());

        if (!user.getEmail().equals(post.getUser().getEmail())) {
            throw new UserNotEqualsException();
        }

        post.update(postRequestDto);
        postRepository.save(post);
        return ResponseDto.success(post);
    }

    // 글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long id, String email) {
        User user = getUser(email);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new PostNotFoundException());
        if (!user.getEmail().equals(post.getUser().getEmail())) {
            throw new UserNotEqualsException();
        }
        postRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByPostId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
        return ResponseDto.success("delete success");
    }

    // 좋아요 추가
    public boolean likeUp(Long id, String email) {
        //1. postId 와 userEmail로 좋아요 여부 판단하기
        Optional<Likes> likes = likesRepository.findByPostIdAndUserEmail(id, email);
        //Exists 메소드
        // id 만 가져오기
        User user = getUser(email);
        Post post = new Post(id);
        if (likes.isPresent()){
            //2-1. 있으면 삭제
            likesRepository.delete(likes.get());
            //id 만 받아와서 삭제!!
        }else {
            //2-2. 없으면 등록
            Likes like = new Likes(post, user);
            likesRepository.save(like);
        }
            return true;
    }
}
