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
    TOKEN_AUTHENTICATION_ERROR(1009, 402, "JwtError"),

    //Project
    TITLE_CANNOT_BE_NULL(1012, 400,"제목은 빈 값이 허용되지 않습니다"),
    DESCRIPTION_CANNOT_BE_NULL(1013, 400,"설명은 빈 값이 허용되지 않습니다"),
    GITHUB_LINK_CANNOT_BE_NULL(1014, 400,"깃허브 링크는 빈 값이 허용되지 않습니다"),
    IS_TEAM_PROJECT_CANNOT_BE_NULL(1015, 400,"팀프로젝트여부는 빈 값이 허용되지 않습니다"),
    OWNNER_ID_CANNOT_BE_NULL(1016, 400,"소유자ID는 빈 값이 허용되지 않습니다"),
    IS_NOT_SAME_OWNERIDS(1017, 401, "토큰으로 확인된 소유자ID와 input으로 넘어온 소유자ID가 일치하지 않습니다"),
    PROJECT_IS_NOT_FOUND(1018, 401, "해당 ID를 가진 프로젝트가 존재하지 않습니다"),
    ALREADY_LIKED(1019, 402, "이미 좋아요한 유저입니다"),
    PROJECT_NOT_YEY_LIKED(1020, 400, "좋아요한 프로젝트가 아닙니다"),
    TITLE_IS_DUPLICATED(1023, 400, "해당 title을 가진 프로젝트가 이미 존재합니다"),
    CATEGORYID_MUST_BE_CONVERTIBLE_TO_NUMBER(1024, 400, "카테고리ID는 숫자형식으로 변환 될 수 있어야합니다"),
    DUPLICATE_CATEGORY_EXISTS(1025, 401, "같은 ID를 가진 카테고리가 여러 개 전달되었습니다."),
    DUPLICATE_IMAGE_EXISTS(1027, 400, "같은 ID를 가진 이미지가 여러 개 전달되었습니다."),
    DUPLICATE_DUPLICATE_TEAMPROJECTMEMBER_EXISTS_EXIST(1028, 400, "같은 ID를 가진 팀프로젝트맴버의 userId가 여러 개 전달되었습니다."),
    INVALID_USERID_WAS_PROVIDED(1028, 402, "userId에 숫자 형식이 아닌 데이터가 전달되었습니다"),
    USER_DOES_NOT_HAVE_ANY_EXISTING_PROJECTS(1029, 400, "해당 유저가 가진 프로젝트가 존재하지 않습니다."),

    //ProjectImg
    NO_MATCHING_PROJECTIMG_WITH_PROJECT_IMG_ID(1026, 400, "해당 ID를 가진 프로젝트이미지가 존재하지 않습니다"),

    //Category
    CATEGORY_IS_ALREADY_CREATED(1021, 400, "이미 해당 이름을 가진 카테고리가 존재합니다"),
    NO_MATCHING_CATEGORY_WITH_ID(1022, 401, "해당 ID를 가진 카테고리가 존재하지 않습니다"),
    CATEGORIES_IS_VALID(1030, 402, "잘못된 형식의 name이 전달되었습니다.");

    private int code;
    private int status;
    private String message;

}