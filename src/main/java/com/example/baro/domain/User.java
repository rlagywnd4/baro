package com.example.baro.domain;

import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.baro.enums.Role.USER;

@Getter
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private Role role;

    public static User of(SignupRequestDto dto, Long userId){
        return new User(userId, dto.getUsername(), dto.getPassword(), dto.getNickname(), USER);
    }

    public User setRole(Role role){
        return new User(this.userId,this.username,this.password,this.nickname, role);
    }
}

