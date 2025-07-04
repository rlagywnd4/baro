package com.example.baro.dto.response.Error;

import com.example.baro.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDto {
    private final ErrorDetail error;

    public static ErrorResponseDto from(BusinessException ex){
        return new ErrorResponseDto(ErrorDetail.from(ex));
    }
}
