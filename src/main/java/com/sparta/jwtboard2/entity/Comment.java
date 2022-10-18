package com.sparta.jwtboard2.entity;

import com.sparta.jwtboard2.dto.requestDto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id", nullable = false)
    private Long id;        // 댓글 고유 id

    // ManyToOne
    @Column (name = "post_Id", nullable = false)
    private Long postId;    // 댓글이 달린 게시글 id

    @Column (name = "comment", nullable = false)
    private String comment;  // 댓글

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;  // 댓글 작성자

    public Comment(CommentRequestDto commentRequestDto, User user) {
        this.postId = commentRequestDto.getPostId();
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.postId = commentRequestDto.getPostId();
        this.comment = commentRequestDto.getComment();
    }
}
