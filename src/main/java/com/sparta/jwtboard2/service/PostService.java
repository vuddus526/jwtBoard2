package com.sparta.jwtboard2.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.sparta.jwtboard2.s3.CommonUtils;
import com.sparta.jwtboard2.dto.responseDto.*;
import com.sparta.jwtboard2.dto.requestDto.PostRequestDto;
import com.sparta.jwtboard2.entity.*;
import com.sparta.jwtboard2.exception.PostNotFoundException;
import com.sparta.jwtboard2.exception.UserNotEqualsException;
import com.sparta.jwtboard2.exception.UserNotFoundException;
import com.sparta.jwtboard2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    private final ImgRepository imgRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // username을 이용해서 User 객체 만들기 및 유저정보 확인 ( user 사용해서)
    private User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException() );
        return user;
    }

    // 글작성
    public ResponseDto<?> createPost(List<MultipartFile> multipartFile, PostRequestDto postRequestDto, String email) throws IOException {
        // 넘어온 multipartFile이 있는지 확인하고 img 객체에 담고 저장하기
        // 저장할때 imgurl 이랑 postId 같이 저장하기
        // 그러려면 Img 객체에 Post를 ManyToOne 해두어야함
        // 그런 다음 상세보기에서 ImgRepository.findByPostId
        User user = getUser(email);
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        String imgUrl = "";

        for (MultipartFile file : multipartFile) {
            if(!file.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrl = amazonS3Client.getUrl(bucketName, fileName).toString();

                Img img = new Img(imgUrl, post);

                imgRepository.save(img);
            }
        }

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

        // 해당 글의 이미지 찾기
        List<Img> imgList = imgRepository.findAllByPostId(id);
        List<ImgResponseDto> imgResponseDtoList = new ArrayList<>();
        for(Img img : imgList) {
            imgResponseDtoList.add(
                    ImgResponseDto.builder()
                            .url(img.getUrl())
                            .build()
            );
        }

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
                        .imgResponseDtoList(imgResponseDtoList)
                        .email(post.getUser().getEmail())
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

    // 키워드 검색기능
    @Transactional
    public ResponseDto<?> searchKeyword(String keyword) {

        List<Post> posts = postRepository.findByKeyword(keyword);

        return ResponseDto.success("posts");
    }
}
