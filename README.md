# 🔐 JWT Authentication & Authorization Project

이 프로젝트는 Spring Boot 기반의 JWT 인증/인가 시스템을 구현한 과제 프로젝트입니다.  
회원가입, 로그인, 권한 부여 등의 기능을 제공하며, 사용자 인증은 JWT 토큰을 통해 이루어집니다.

---
## ✅ 과제 목표 및 진행 상황

**과제: Spring Boot 기반 JWT 인증/인가 및 AWS 배포**

## 📌 주요 기능

- 회원가입
- 로그인 (JWT 발급)
- 관리자 권한 부여

---

##  **Swagger URL**: `http://54.180.116.44:8080/swagger-ui/index.html#/`


## 🔗 API 명세

### 📍 회원가입

- **HTTP Method**: `POST`  
- **URL**: `http://0.0.0.0:8080/signup`

#### ✅ Request Body 예시
```json
{
  "username": "JIN HO",
  "password": "12341234",
  "nickname": "Mentos"
}
```

#### ✅ Response Body 예시 (성공)
```json
{
  "username": "JIN HO",
  "nickname": "Mentos",
  "roles": [
    {
      "role": "USER"
    }
  ]
}
```

#### ❌ Response Body 예시 (실패 - 중복 사용자)
```json
{
  "error": {
    "code": "USER_ALREADY_EXISTS",
    "message": "이미 가입된 사용자입니다."
  }
}
```

---

### 📍 로그인

- **HTTP Method**: `POST`  
- **URL**: `http://0.0.0.0:8080/login`

#### ✅ Request Body 예시
```json
{
  "username": "JIN HO",
  "password": "12341234"
}
```

#### ✅ Response Body 예시 (성공)
```json
{
  "token": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
}
```

#### ❌ Response Body 예시 (실패 - 로그인 정보 오류)
```json
{
  "error": {
    "code": "INVALID_CREDENTIALS",
    "message": "아이디 또는 비밀번호가 올바르지 않습니다."
  }
}
```

---

### 📍 관리자 권한 부여

- **HTTP Method**: `PATCH`  
- **URL**: `http://0.0.0.0:8080/admin/users/{userId}/roles`  
- **예시**: `/admin/users/15/roles`

#### ✅ Response Body 예시 (성공)
```json
{
  "username": "JIN HO",
  "nickname": "Mentos",
  "roles": [
    {
      "role": "Admin"
    }
  ]
}
```

#### ❌ Response Body 예시 (실패 - 권한 부족)
```json
{
  "error": {
    "code": "ACCESS_DENIED",
    "message": "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
  }
}
```
