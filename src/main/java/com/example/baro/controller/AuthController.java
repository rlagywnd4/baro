package com.example.baro.controller;

import com.example.baro.domain.User;
import com.example.baro.dto.request.LoginRequestDto;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.LoginResponseDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.repository.UserRepository;
import com.example.baro.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    // for test
    private final UserRepository userRepository;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(
            @RequestBody SignupRequestDto dto
    ) {
        return ResponseEntity.ok(authService.signup(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse response){
        String token = authService.login(dto);
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    // test를 위한 권한 변경 api
    @PatchMapping("/test/{userId}")
    public ResponseEntity<User> changePermition(@PathVariable Long userId){
        User user = userRepository.grantAdminRole(userId);
        return ResponseEntity.ok(user);
    }
}
