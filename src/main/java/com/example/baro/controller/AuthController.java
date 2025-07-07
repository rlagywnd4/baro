package com.example.baro.controller;

import com.example.baro.domain.User;
import com.example.baro.dto.request.LoginRequestDto;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.LoginResponseDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.repository.UserRepository;
import com.example.baro.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "회원가입", description = "사용자의 정보를 받아 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 가입된 사용자입니다.",
                    content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "이미 가입된 사용자 예시",
                    summary = "중복 이메일 응답",
                    value = """
                        {
                          "error": {
                            "code": "USER_ALREADY_EXISTS",
                            "message": "이미 가입된 사용자입니다."
                          }
                        }
                        """
            )))
    })
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
