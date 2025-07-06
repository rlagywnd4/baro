package com.example.baro.service;

import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 권한 수정
@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;

    public UserResponseDto grantAdminRole(Long userId){
        return UserResponseDto.from(userRepository.grantAdminRole(userId));
    }
}
