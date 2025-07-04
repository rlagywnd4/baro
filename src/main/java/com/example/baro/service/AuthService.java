package com.example.baro.service;

import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    public UserResponseDto signup(SignupRequestDto dto){
        userRepository.checkUserNotExist(dto.getUsername());

        return UserResponseDto.from(userRepository.createUser(dto));
    }
}