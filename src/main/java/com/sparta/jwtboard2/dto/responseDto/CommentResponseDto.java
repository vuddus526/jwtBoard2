package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String email;  // username
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto (Comment comment) {
        this.id = comment.getId();
        this.email = comment.getUser().getEmail();
        this.comment = comment.getComment();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
