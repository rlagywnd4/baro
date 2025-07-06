package com.example.baro.repository;

import com.example.baro.domain.User;
import com.example.baro.dto.request.SignupRequestDto;
import com.example.baro.enums.ErrorCode;
import com.example.baro.enums.Role;
import com.example.baro.exception.AdminException;
import com.example.baro.exception.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        checkUserExist(username, USER_NOT_EXISTS);

        User user = users.get(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("")
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    public User findUserByUsername(String username){
        checkUserExist(username, USER_NOT_EXISTS);

        return users.get(username);
    }

    public User grantAdminRole(Long userId) {
        Optional<User> matchedUser = users.values().stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .findFirst();
        if(matchedUser.isEmpty()) {
            throw new AdminException(USER_NOT_EXISTS);
        }
        User user = matchedUser.get().setRole(Role.Admin);
        users.replace(user.getUsername(), user);

        return user;
    }

    /**
     * 유저가 있는지 확인(없으면 예외 발생)
     * @param username username
     * @param errorCode ErrorCode to be thrown when the user is not found
     */
    public void checkUserExist(String username, ErrorCode errorCode){
        if(!users.containsKey(username)){
            throw new AuthException(errorCode);
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
