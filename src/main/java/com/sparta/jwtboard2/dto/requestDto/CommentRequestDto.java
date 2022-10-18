package com.sparta.jwtboard2.dto.requestDto;

import com.sparta.jwtboard2.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private String comment;
    private User user;
}
