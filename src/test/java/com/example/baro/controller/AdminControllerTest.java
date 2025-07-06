package com.example.baro.controller;

import com.example.baro.dto.response.RoleResponseDto;
import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.enums.Role;
import com.example.baro.exception.AdminException;
import com.example.baro.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static com.example.baro.enums.ErrorCode.USER_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @DisplayName("역할 부여 성공")
    @Test
    void grantAdminRole() {
        // given
        Long userId = 1L;
        when(adminService.grantAdminRole(userId)).thenReturn(new UserResponseDto("hi","hi1", List.of(new RoleResponseDto(Role.Admin))));

        // when
        ResponseEntity<UserResponseDto> response = adminController.grantAdminRole(userId);

        // then
        assertEquals(Role.Admin, Objects.requireNonNull(response.getBody()).getRoles().get(0).getRole());

    }

    @DisplayName("역할 부여 실패_없는 유저아이디")
    @Test
    void grantAdminRoleFailed(){
        // given
        Long userId = 2L;
        when(adminService.grantAdminRole(userId)).thenThrow(new AdminException(USER_NOT_EXISTS));

        // then
        assertThrows(AdminException.class, () -> adminController.grantAdminRole(userId));
    }
}