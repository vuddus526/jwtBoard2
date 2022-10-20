package com.sparta.jwtboard2.entity;

import com.sparta.jwtboard2.dto.requestDto.UserRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Enumerated(EnumType.STRING)
//    private Authority authority;

    public User(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
    }

//    public User(String email, String password, Authority authority) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.authority = authority;
//    }

}
