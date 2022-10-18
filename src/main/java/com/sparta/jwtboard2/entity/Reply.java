package com.sparta.jwtboard2.entity;

import com.sparta.jwtboard2.dto.requestDto.CommentRequestDto;
import com.sparta.jwtboard2.dto.requestDto.ReplyRequestDto;
import com.sparta.jwtboard2.dto.responseDto.ReplyResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Reply extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reply_id", nullable = false)
    private Long id;        // 대댓글 고유 id

    //@ManyToOne
    @Column (name = "comment_Id", nullable = false)
    private Long commentId;    // 대댓글이 달린 댓글 id

    @Column (name = "reply", nullable = false)
    private String reply;  // 댓글

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;  // 댓글 작성자

    public Reply(Long id, ReplyRequestDto replyRequestDto, User user) {
        this.commentId = id;
        this.reply = replyRequestDto.getReply();
        this.user = user;
    }

    public void update(Long id, ReplyRequestDto replyRequestDto) {
        this.commentId = id;
        this.reply = replyRequestDto.getReply();
    }
}
