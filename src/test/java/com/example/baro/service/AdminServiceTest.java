package com.example.baro.service;

import com.example.baro.domain.User;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.enums.Role;
import com.example.baro.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @DisplayName("역할 부여 성공")
    @Test
    void grantAdminRole() {
        //given
        Long userId = 1L;
        User user = new User(userId, "kim", "1234","kim nick", Role.Admin);
        when(userRepository.grantAdminRole(userId)).thenReturn(user);

        // when
        UserResponseDto result = adminService.grantAdminRole(userId);

        // then
        assertEquals(Role.Admin, result.getRoles().get(0).getRole());
    }
}