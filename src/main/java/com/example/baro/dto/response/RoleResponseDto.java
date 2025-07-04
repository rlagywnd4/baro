package com.example.baro.dto.response;

import com.example.baro.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoleResponseDto {
    private final Role role;

    public static RoleResponseDto from(Role role) {
        return new RoleResponseDto(role);
    }
}
