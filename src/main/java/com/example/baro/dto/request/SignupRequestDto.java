package com.example.baro.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {
    @Schema(description = "사용자 이름", example = "hi")
    private final String username;
    private final String password;
    private final String nickname;
}
