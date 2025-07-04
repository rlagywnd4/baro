package com.example.baro.repository;

import com.example.baro.domain.User;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.exception.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.baro.enums.ErrorCode.USER_ALREADY_EXISTS;
import static com.example.baro.enums.ErrorCode.USER_NOT_EXISTS;

@Component
@AllArgsConstructor
public class UserRepository {
    private static final Map<String, User> users = new HashMap<>();
    private static Long userCount = 1L;

    public User createUser(SignupRequestDto dto){
        checkUserNotExist(dto.getUsername());

        User newUser = User.of(dto, userCount++);
        users.put(newUser.getUsername(), newUser);

        return newUser;
    }

    public UserDetails loadUserByUsername(String username) {
        checkUserExists(username);

        User user = users.get(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("")
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    /**
     * 유저가 있는지 확인(없으면 예외 발생)
     * @param username username
     */
    public void checkUserExists(String username){
        if(!users.containsKey(username)){
            throw new AuthException(USER_NOT_EXISTS);
        }
    }

    /**
     * 유저가 없는지 확인(있으면 예외 발생)
     * @param username username
     */
    public void checkUserNotExist(String username){
        if(users.containsKey(username)){
            throw new AuthException(USER_ALREADY_EXISTS);
        }
    }
}
