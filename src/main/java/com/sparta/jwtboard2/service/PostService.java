package com.sparta.jwtboard2.service;

import com.sparta.jwtboard2.dto.responseDto.PostListResponseDto;
import com.sparta.jwtboard2.dto.requestDto.PostRequestDto;
import com.sparta.jwtboard2.dto.responseDto.PostResponseDto;
import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Likes;
import com.sparta.jwtboard2.entity.Post;
import com.sparta.jwtboard2.entity.User;
import com.sparta.jwtboard2.repository.CommentRepository;
import com.sparta.jwtboard2.repository.LikesRepository;
import com.sparta.jwtboard2.repository.PostRepository;
import com.sparta.jwtboard2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    // username을 이용해서 User 객체 만들기 및 유저정보 확인 ( user 사용해서)
    private User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return user;
    }

    // 글작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String email) {
        User user = getUser(email);

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 글 전체보기
    public List<PostListResponseDto> getPostAll() {
        List<Post> list = postRepository.findAllByOrderByModifiedAtDesc();

        List<PostListResponseDto> plist = new ArrayList<>();

        for(Post post : list) {
            PostListResponseDto postListResponseDto = PostListResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .email(post.getUser().getEmail())
                    .createAt(post.getCreateAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            plist.add(postListResponseDto);
        }
        return plist;
    }

    // 글 상세보기
    public PostResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
        return new PostResponseDto(post);
    }

    // 글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String email) {
        User user = getUser(email);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));

        if (!user.getEmail().equals(post.getUser().getEmail())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }

        post.update(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 글 삭제
    @Transactional
    public String deletePost(Long id, String email) {
        User user = getUser(email);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));
        if (!user.getEmail().equals(post.getUser().getEmail())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
        postRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByPostId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
        return "글이 삭제되었습니다";
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
