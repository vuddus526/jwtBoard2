package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Post;
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
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private List<CommentResponseDto> commentResponseDtoList;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
