package com.example.baro.repository;

import com.example.baro.domain.User;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.exception.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.baro.enums.ErrorCode.USER_ALREADY_EXISTS;

@Component
@AllArgsConstructor
public class UserRepository {
    private static final Map<String, User> users = new HashMap<>();
    private static Long userCount = 1L;

    public boolean hasUser(String username){
        return users.containsKey(username);
    }

    public User createUser(SignupRequestDto dto){
        if(hasUser(dto.getUsername())){
            throw new AuthException(USER_ALREADY_EXISTS);
        }
        User newUser = User.of(dto, userCount++);
        users.put(newUser.getUsername(), newUser);

        return newUser;
    }
}
