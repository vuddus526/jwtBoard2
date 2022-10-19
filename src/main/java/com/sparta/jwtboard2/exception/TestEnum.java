package com.sparta.jwtboard2.exception;

import lombok.Getter;

@Getter
public enum TestEnum {
    USERNAME_NOTFOUND("404", "유저이름이 없습니다"),
    NOT_FOUND("", "");

    private String value;
    private String message;

    TestEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }
}
