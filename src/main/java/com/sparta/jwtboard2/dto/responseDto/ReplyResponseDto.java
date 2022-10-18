package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String email;  // username
    private String reply;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ReplyResponseDto (Reply reply) {
        this.id = reply.getId();
        this.email = reply.getUser().getEmail();
        this.reply = reply.getReply();
        this.createAt = reply.getCreateAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
