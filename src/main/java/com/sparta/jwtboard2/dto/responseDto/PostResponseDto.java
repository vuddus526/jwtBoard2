package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Img;
import com.sparta.jwtboard2.entity.Post;
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
public class PostResponseDto {

    @ApiModelProperty(example = "글 아이디")
    private Long id;

    @ApiModelProperty(example = "글 제목")
    private String title;

    @ApiModelProperty(example = "글 내용")
    private String contents;

    @ApiModelProperty(example = "댓글 목록")
    private List<CommentResponseDto> commentResponseDtoList;

    private List<ImgResponseDto> imgResponseDtoList;

    @ApiModelProperty(example = "유저 이메일")
    private String email;

    @ApiModelProperty(example = "글 생성일자")
    private LocalDateTime createAt;

    @ApiModelProperty(example = "글 수정일자")
    private LocalDateTime modifiedAt;
}
