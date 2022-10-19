package com.sparta.jwtboard2.repository;

import com.sparta.jwtboard2.entity.RefreshToken;
import com.sparta.jwtboard2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserEmail(String email);
}
