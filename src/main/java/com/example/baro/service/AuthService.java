package com.example.baro.service;

import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.exception.AuthException;
import com.example.baro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.baro.enums.ErrorCode.USER_ALREADY_EXISTS;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    public UserResponseDto signup(SignupRequestDto dto){
        if (userRepository.hasUser(dto.getUsername())) {
            throw new AuthException(USER_ALREADY_EXISTS);
        }
        return UserResponseDto.from(userRepository.createUser(dto));
    }
}