package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Comment;
import com.sparta.jwtboard2.entity.Reply;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "코멘트 아이디")
    private Long id;

    @ApiModelProperty(example = "유저 이메일")
    private String email;  // username

    @ApiModelProperty(example = "코멘트 내용")
    private String comment;

    @ApiModelProperty(example = "대댓글 목록")
    private List<Reply> replyResponseDtoList;

    @ApiModelProperty(example = "코멘트 생성일자")
    private LocalDateTime createAt;

    @ApiModelProperty(example = "코멘트 수정일자")
    private LocalDateTime modifiedAt;
}
