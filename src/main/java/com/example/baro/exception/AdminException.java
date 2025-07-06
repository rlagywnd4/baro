package com.example.baro.exception;

import com.example.baro.enums.ErrorCode;

public class AdminException extends BusinessException{
    public AdminException(ErrorCode errorCode) {super(errorCode);}
}
