package com.example.baro.controller;

import com.example.baro.dto.response.UserResponseDto;
import com.example.baro.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminController {
    private final AdminService adminService;

    //권한 수정 api
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<UserResponseDto> grantAdminRole(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(adminService.grantAdminRole(userId));
    }
}
