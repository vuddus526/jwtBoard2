package com.sparta.jwtboard2.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {
    private String title;

    private Long id;
    private String email;  // username
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
