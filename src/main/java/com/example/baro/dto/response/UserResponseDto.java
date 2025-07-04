package com.example.baro.dto.response;

import com.example.baro.domain.User;
import com.example.baro.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final String username;
    private final String nickname;
    private final List<RoleResponseDto> roles;

    public static UserResponseDto from(User user){
        return new UserResponseDto(user.getUsername(), user.getNickname(), List.of(RoleResponseDto.from(user.getRole())));
    }
}
