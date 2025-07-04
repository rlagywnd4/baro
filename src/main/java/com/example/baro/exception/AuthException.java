package com.example.baro.exception;

import com.example.baro.enums.ErrorCode;

public class AuthException extends BusinessException{
    public AuthException(ErrorCode errorCode) { super(errorCode);}
}
