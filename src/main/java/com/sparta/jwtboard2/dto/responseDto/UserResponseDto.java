package com.sparta.jwtboard2.dto.responseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @ApiModelProperty(example = "유저 고유 아이디")  // Swagger에 해당 필드가 무엇인지 나타냄
    private Long id;

    @ApiModelProperty(example = "유저 이름")
    private String name;

    @ApiModelProperty(example = "유저 이메일")
    private String email;

    @ApiModelProperty(example = "생성 일자")
    private LocalDateTime createdAt;

    @ApiModelProperty(example = "수정 일자")
    private LocalDateTime modifiedAt;
}
