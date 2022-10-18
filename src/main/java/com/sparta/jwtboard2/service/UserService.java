package com.sparta.jwtboard2.service;

import com.sparta.jwtboard2.dto.responseDto.GlobalResDto;
import com.sparta.jwtboard2.dto.requestDto.LoginRequestDto;
import com.sparta.jwtboard2.dto.TokenDto;
import com.sparta.jwtboard2.dto.requestDto.UserRequestDto;
import com.sparta.jwtboard2.entity.RefreshToken;
import com.sparta.jwtboard2.entity.User;
import com.sparta.jwtboard2.jwt.JwtUtil;
import com.sparta.jwtboard2.repository.RefreshTokenRepository;
import com.sparta.jwtboard2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    public final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResDto signup(UserRequestDto userRequestDto) {
        // email 중복 검사
        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }
        // 패스워드 암호화로 만들기
        userRequestDto.setEncodePwd(passwordEncoder.encode(userRequestDto.getPassword()));

        // 이메일과 패스워드 객체에 담시
        User user = new User(userRequestDto);

        // DB에 객체 저장
        userRepository.save(user);
        return new GlobalResDto("Success signup", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 아이디(이메일) 있는지 확인
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );
        // 비밀번호 있는지 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        // 엑세스토큰, 리프레쉬토큰 생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getEmail());

        // 리프레쉬 토큰은 DB에서 찾기
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEmail(loginRequestDto.getEmail());

        // 리프레쉬토큰 null인지 아닌지 에 따라서
        // 값을 가지고있으면 save
        // 값이 없으면 newToken 만들어내서 save
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        // 헤더에 response == 엑세스 토큰인지 리프레쉬 토큰인지
        // tokenDto == 생성된 엑세스, 리프레쉬 담기
        setHeader(response, tokenDto);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());

    }

    // response에 담는 메서드
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
