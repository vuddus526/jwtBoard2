package com.sparta.jwtboard2.service;

import com.sparta.jwtboard2.dto.requestDto.CommentRequestDto;
import com.sparta.jwtboard2.dto.requestDto.ReplyRequestDto;
import com.sparta.jwtboard2.dto.responseDto.CommentResponseDto;
import com.sparta.jwtboard2.dto.responseDto.ReplyResponseDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Reply;
import com.sparta.jwtboard2.entity.User;
import com.sparta.jwtboard2.exception.CommentNotFoundException;
import com.sparta.jwtboard2.exception.PostNotFoundException;
import com.sparta.jwtboard2.exception.UserNotEqualsException;
import com.sparta.jwtboard2.exception.UserNotFoundException;
import com.sparta.jwtboard2.repository.CommentRepository;
import com.sparta.jwtboard2.repository.PostRepository;
import com.sparta.jwtboard2.repository.ReplyRepositoy;
import com.sparta.jwtboard2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepositoy replyRepositoy;

    private User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException());
        return user;
    }

    public void postCheck(CommentRequestDto commentRequestDto) {
        postRepository.findById( commentRequestDto.getPostId() )
                .orElseThrow( () -> new PostNotFoundException());
    }

    public void commentCheck(Long id) {
        commentRepository.findById( id )
                .orElseThrow( () -> new CommentNotFoundException());
    }

    //댓글 쓰기
    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, String email) {
        User user = getUser(email);
        postCheck(commentRequestDto);

        Comment comment = new Comment(commentRequestDto, user);
        commentRepository.save(comment);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .email(comment.getUser().getEmail())
                        .comment(comment.getComment())
                        .createAt(comment.getCreateAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    //댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long id, CommentRequestDto commentRequestDto, String email) {
        User user = getUser(email);
        postCheck(commentRequestDto);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new CommentNotFoundException());

        if(!user.getEmail().equals(comment.getUser().getEmail())){
            throw new UserNotEqualsException();
        }

        comment.update(commentRequestDto);
        commentRepository.save(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .email(comment.getUser().getEmail())
                        .comment(comment.getComment())
                        .createAt(comment.getCreateAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    //댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long id, String email) {
        User user = getUser(email);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new CommentNotFoundException());

        if(!user.getEmail().equals(comment.getUser().getEmail())){
            throw new UserNotEqualsException();
        }

        commentRepository.deleteById(id);
        return ResponseDto.success("success");
    }

    //댓글 전체목록 보기
    @Transactional(readOnly =true)
    public ResponseDto<?> getCommentAllOfPost(Long id) {
        List<Comment> commentList = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .email(comment.getUser().getEmail())
                            .comment(comment.getComment())
                            .createAt(comment.getCreateAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(commentResponseDtoList);
    }

    // 대댓글 추가
    public ResponseDto<?> createReply(Long id, ReplyRequestDto replyRequestDto, String email) {

        User user = getUser(email);
        commentCheck(id);

        Reply reply = new Reply(id, replyRequestDto, user);
        replyRepositoy.save(reply);

        return ResponseDto.success(
                ReplyResponseDto.builder()
                        .id(reply.getId())
                        .email(reply.getUser().getEmail())
                        .reply(reply.getReply())
                        .createAt(reply.getCreateAt())
                        .modifiedAt(reply.getModifiedAt())
                        .build()
        );
    }
}
