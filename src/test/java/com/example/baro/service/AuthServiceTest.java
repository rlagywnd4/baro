package com.example.baro.service;

import com.example.baro.domain.User;
import com.example.baro.dto.request.LoginRequestDto;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.enums.Role;
import com.example.baro.jwt.JwtTokenProvider;
import com.example.baro.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.baro.enums.ErrorCode.INVALID_CREDENTIALS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @DisplayName("회원가입 성공")
    @Test
    void signup() {
        //given
        SignupRequestDto dto = new SignupRequestDto("hi", "1234", "hi1");
        when(userRepository.createUser(dto)).thenReturn(new User(1L,"hi", "1234", "hi1", Role.USER));

        // when
        UserResponseDto result = authService.signup(dto);

        // then
        assertEquals("hi", result.getUsername());
        assertEquals("hi1", result.getNickname());
        assertEquals(Role.USER, result.getRoles().get(0).getRole());
    }

    @DisplayName("로그인 성공")
    @Test
    void login() {
        // given
        LoginRequestDto dto = new LoginRequestDto("hi", "1234");
        User user = new User(1L, "hi", "1234", "nickname", Role.USER);
        when(userRepository.findUserByUsername("hi")).thenReturn(user);
        when(jwtTokenProvider.createToken("hi", Role.USER)).thenReturn("fake-token");

        doNothing().when(userRepository).checkUserExist("hi", INVALID_CREDENTIALS);

        // when
        String token = authService.login(dto);

        // then
        assertEquals("fake-token", token);
    }
}