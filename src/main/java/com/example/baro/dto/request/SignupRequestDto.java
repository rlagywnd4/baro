package com.example.baro.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {
    private final String username;
    private final String password;
    private final String nickname;
}
