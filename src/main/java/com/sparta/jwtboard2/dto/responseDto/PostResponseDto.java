package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String contents;

    private Long id;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto (Post post){
        this.title = post.getTitle();
        this.contents = post.getContents();

        this.id = post.getId();
        this.email = post.getUser().getEmail();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
