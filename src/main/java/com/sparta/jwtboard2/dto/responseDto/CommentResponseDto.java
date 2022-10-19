package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String email;  // username
    private String comment;
    private List<Reply> replyResponseDtoList;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

}
