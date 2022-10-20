package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Reply;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {

    @ApiModelProperty(example = "대댓글 아이디")
    private Long id;

    @ApiModelProperty(example = "유저 이메일")
    private String email;  // username

    @ApiModelProperty(example = "대댓글 내용")
    private String reply;

    @ApiModelProperty(example = "대댓글 생성일자")
    private LocalDateTime createAt;

    @ApiModelProperty(example = "대댓글 수정일자")
    private LocalDateTime modifiedAt;
}
