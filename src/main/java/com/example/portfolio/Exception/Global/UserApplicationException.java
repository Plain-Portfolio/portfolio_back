package com.example.portfolio.Exception.Global;

import com.example.portfolio.Common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User 커스텀 예외처리 클래스
 */
@Getter
@AllArgsConstructor
public class UserApplicationException extends RuntimeException {

    private ErrorCode errorCode;

}