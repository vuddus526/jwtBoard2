package com.sparta.jwtboard2.dto.requestDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostRequestDto {
    private final String title;
    private final String contents;
}
