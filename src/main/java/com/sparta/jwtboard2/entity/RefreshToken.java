package com.sparta.jwtboard2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    public RefreshToken(String token, String email) {
        this.refreshToken = token;
        this.userEmail = email;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
