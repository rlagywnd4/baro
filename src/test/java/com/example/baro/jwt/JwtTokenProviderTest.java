package com.example.baro.jwt;

import com.example.baro.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenProvider = new JwtTokenProvider();

        // @Value("${jwt.secret}") 을 수동으로 주입
        Field secretKeyField = JwtTokenProvider.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtTokenProvider, "my-secret-key-which-is-long-enough");

        jwtTokenProvider.init();
    }


    @Test
    @DisplayName("토큰 생성 및 검증 성공")
    void createAndValidateToken() {
        String token = jwtTokenProvider.createToken("hi", Role.USER);

        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("hi", jwtTokenProvider.getUsername(token));
    }

    @Test
    @DisplayName("잘못된 형식의 토큰 검증 실패")
    void invalidTokenFormat() {
        String invalidToken = "not.a.valid.token";

        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    @DisplayName("만료된 토큰 검증 실패")
    void expiredToken() throws Exception {
        Date now = new Date();

        Field field = JwtTokenProvider.class.getDeclaredField("secretKey");
        field.setAccessible(true);
        String secretKey = (String) field.get(jwtTokenProvider);

        String token = Jwts.builder()
                .subject("hi")
                .claims(new HashMap<>())
                .issuedAt(now)
                .expiration(new Date(now.getTime() - 1000L)) // 기간을 만료시킴
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();

        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("getUsername 실패 시 예외 발생 여부 확인")
    void getUsernameWithInvalidToken() {
        String invalidToken = "this.is.invalid.token";
        assertThrows(Exception.class, () -> jwtTokenProvider.getUsername(invalidToken));
    }
}
