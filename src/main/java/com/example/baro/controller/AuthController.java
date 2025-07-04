package com.example.baro.controller;

import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    //TODO: 디벨롭시 Valid 추가할 것
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(
            @RequestBody SignupRequestDto dto
    ) {
        return ResponseEntity.ok(authService.signup(dto));
    }
}
