package com.example.baro.dto.response.Error;

import com.example.baro.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorDetail {
    private final String code;
    private final String message;

    public static ErrorDetail from(BusinessException ex){
        return new ErrorDetail(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage());
    }
}
