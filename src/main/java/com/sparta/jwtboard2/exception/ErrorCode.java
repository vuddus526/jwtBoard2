package com.sparta.jwtboard2.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND_EXCEPTION("404", "Bad Request", "유저 정보를 찾을 수 없습니다"),
    USER_EMAIL_ALREADY_EXCEPTION("400", "Bad Request", "이미 이메일이 존재합니다"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error", "서버 오류");

    private String code;
    private String message;
    private String detail;

    ErrorCode(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
