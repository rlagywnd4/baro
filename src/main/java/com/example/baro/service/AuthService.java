package com.example.baro.service;

import com.example.baro.domain.User;
import com.example.baro.dto.request.LoginRequestDto;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.exception.AuthException;
import com.example.baro.jwt.JwtTokenProvider;
import com.example.baro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.baro.enums.ErrorCode.INVALID_CREDENTIALS;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponseDto signup(SignupRequestDto dto){
        userRepository.checkUserNotExist(dto.getUsername());

        return UserResponseDto.from(userRepository.createUser(dto));
    }

    public String login(LoginRequestDto dto) {
        userRepository.checkUserExist(dto.getUsername(), INVALID_CREDENTIALS);

        User user = userRepository.findUserByUsername(dto.getUsername());
        if(!user.getPassword().equals(dto.getPassword())){
            throw new AuthException(INVALID_CREDENTIALS);
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }
}