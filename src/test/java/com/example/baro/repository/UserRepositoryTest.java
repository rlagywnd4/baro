package com.example.baro.repository;

import com.example.baro.domain.User;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    private UserRepository userRepository = new UserRepository();

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void createAndFindUser() {
        // given
        SignupRequestDto dto = new SignupRequestDto("hi", "1234", "nickname");

        // when
        User user = userRepository.createUser(dto);
        User found = userRepository.findUserByUsername("hi");

        // then
        assertEquals(user.getUserId(), found.getUserId());
        assertEquals("nickname", found.getNickname());
    }

    @Test
    void grantAdminRole() {
        // given
        SignupRequestDto dto = new SignupRequestDto("admin", "pw", "nick");
        User user = userRepository.createUser(dto);

        // when
        User result = userRepository.grantAdminRole(user.getUserId());

        // then
        assertEquals(Role.Admin, result.getRole());
    }
}