package com.sparta.jwtboard2.controller;

import com.sparta.jwtboard2.dto.responseDto.GlobalResDto;
import com.sparta.jwtboard2.dto.requestDto.LoginRequestDto;
import com.sparta.jwtboard2.dto.requestDto.UserRequestDto;
import com.sparta.jwtboard2.dto.responseDto.ResponseDto;
import com.sparta.jwtboard2.jwt.JwtUtil;
import com.sparta.jwtboard2.security.UserDetailsImpl;
import com.sparta.jwtboard2.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = {"jwtBoard2 API"})  // Swagger 최상단 Controller 명칭
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    @ApiOperation(value = "회원가입", notes = "회원가입 API") // Swagger에 사용하는 API에 대한 간단 설명
    @ApiImplicitParam(name = "userRequestDto", value = "회원가입시 입력된 정보")  // Swagger에 사용하는 파라미터에 대해 설명
    public ResponseDto<?> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    // 로그인
    @PostMapping("/user/login")
    @ApiOperation(value = "로그인", notes = "로그인 API")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

//    @GetMapping("/auth/user/mypage")
//    public ResponseDto<?> mypage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
//        return userService.mypage(userDetailsImpl.getUser().getEmail());
//    }

    // 토큰 재발행
    @GetMapping("/issue/token")
    @ApiOperation(value = "토큰재발행", notes = "토큰재발행 API")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getUser().getEmail(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }
}
