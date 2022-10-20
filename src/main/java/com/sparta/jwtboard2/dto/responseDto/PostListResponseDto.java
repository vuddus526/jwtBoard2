package com.sparta.jwtboard2.dto.responseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {

    @ApiModelProperty(example = "글 제목")
    private String title;

    @ApiModelProperty(example = "글 아이디")
    private Long id;

    @ApiModelProperty(example = "유저 이메일")
    private String email;  // username

    @ApiModelProperty(example = "글 생성일자")
    private LocalDateTime createAt;

    @ApiModelProperty(example = "글 수정일자")
    private LocalDateTime modifiedAt;
}
