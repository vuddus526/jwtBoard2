package com.sparta.jwtboard2.dto.responseDto;

import com.sparta.jwtboard2.entity.Reply;
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
    private Long id;
    private String email;  // username
    private String reply;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

}
