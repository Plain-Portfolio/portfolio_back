package com.example.portfolio.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀한 에러코드를 작성한다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    //User
    HTTP_INTERNAL_SERVER_ERROR(1000, 500, "서버 내부 오류가 발생했습니다."),
    EMAIL_IS_DUPLICATED(1001, 401, "이메일이 중복되었습니다."),
    NICKNAME_IS_DUPLICATED(1002, 401, "닉네임이 중복되었습니다."),
    EMAIL_IS_VALID(1003, 400, "이메일 유효성 검사에 실패하였습니다."),
    PASSWORD_IS_VALID(1004, 400, "비밀번호 유효성 검사에 실패하였습니다."),
    NICKNAME_IS_VALID(1005, 400, "닉네임 유효성 검사에 실패하였습니다."),
    EMAIL_CANNOT_BE_NULL(1006, 400, "이메일 필드는 빈 값이 허용되지 않습니다."),
    NO_MATCHING_USER_FOUND_WITH_EMAIL(1007, 402, "이메일과 매칭되는 유저가 존재하지 않습니다."),
    NO_MATCHING_USER_FOUND_WITH_PASSWORD(1008, 402, "비밀번호와 매칭되는 유저가 존재하지 않습니다."),

    //Token
    TOKEN_AUTHENTICATION_ERROR(1009, 402, "JwtError")

    ;

    private int code;
    private int status;
    private String message;

}