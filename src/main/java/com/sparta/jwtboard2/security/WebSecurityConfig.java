package com.sparta.jwtboard2.security;

import com.sparta.jwtboard2.jwt.JwtAuthFilter;
import com.sparta.jwtboard2.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration  // 설정파일을 만들기 위한 어노테이션 or Bean을 등록하기 위한 어노테이션
@EnableWebSecurity  // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor    // 생성자 주입 어노테이션
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    // 비밀번호가 해시값으로 들어오지 않으면 시큐리티에서 받아주지를않는다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CORS
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("*"));
            cors.setAllowedHeaders(List.of("*"));
            cors.addExposedHeader("Access_Token");
            cors.addExposedHeader("Refresh_Token");
            cors.setAllowCredentials(true);
            return cors;
        });

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers("/api/auth/**").authenticated()
//                .antMatchers(HttpMethod.DELETE,"/api/posts/{postId}").authenticated()
                .anyRequest().permitAll()
                .and()
                // 뒤에 필터보다 앞에 필터를 먼저 쓰겠다
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
