package com.sparta.jwtboard2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JwtBoard2Application {

    public static void main(String[] args) {
        SpringApplication.run(JwtBoard2Application.class, args);
    }

}
