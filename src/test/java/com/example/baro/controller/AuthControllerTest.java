package com.example.baro.controller;

import com.example.baro.dto.request.LoginRequestDto;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.LoginResponseDto;
import com.example.baro.dto.response.RoleResponseDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.enums.Role;
import com.example.baro.exception.AuthException;
import com.example.baro.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static com.example.baro.enums.ErrorCode.INVALID_CREDENTIALS;
import static com.example.baro.enums.ErrorCode.USER_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @DisplayName("회원가입 성공")
    @Test
    void signup() {
        // given
        SignupRequestDto dto = new SignupRequestDto("hi","1234", "hi1");
        when(authService.signup(dto)).thenReturn(new UserResponseDto("hi", "hi1", List.of(new RoleResponseDto(Role.USER))));

        // when
        ResponseEntity<UserResponseDto> response = authController.signup(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("hi", Objects.requireNonNull(response.getBody()).getUsername());
        assertEquals("hi1", response.getBody().getNickname());
        assertEquals(Role.USER, response.getBody().getRoles().get(0).getRole());
    }

    @DisplayName("회원가입 실패_똑같은 유저이름")
    @Test
    void signupFailed() {
        // given
        SignupRequestDto dto = new SignupRequestDto("hi","1234", "hi1");
        when(authService.signup(dto)).thenThrow(new AuthException(USER_ALREADY_EXISTS));

        // then
        assertThrows(AuthException.class, () -> authController.signup(dto));
    }

    @DisplayName("로그인 성공")
    @Test
    void login(){
        // given
        LoginRequestDto dto = new LoginRequestDto("hi", "1234");
        String fakeToken = "fake-jwt-token";
        when(authService.login(dto)).thenReturn(fakeToken);

        // when
        ResponseEntity<LoginResponseDto> response = authController.login(dto, httpServletResponse);

        // then
        assertEquals(fakeToken, Objects.requireNonNull(response.getBody()).getToken());
    }

    @DisplayName("로그인 실패_틀린 비밀번호")
    @Test
    void loginFailed(){
        // given
        LoginRequestDto dto = new LoginRequestDto("hi", "123");
        when(authService.login(dto)).thenThrow(new AuthException(INVALID_CREDENTIALS));

        // then
        assertThrows(AuthException.class, () -> authController.login(dto, httpServletResponse));
    }
}