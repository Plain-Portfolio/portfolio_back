package com.example.portfolio.DTO.User;

import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.response.User.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SignUpDto {

    @Schema(description = "사용자 email", example = "eos0103@naver.com")
    private String email;

    @Schema(description = "사용자 password", example = "aos4050123!@#")
    private String password;

    @Schema(description = "사용자 nickname", example = "김기범")
    private String nickname;

    @Schema(description = "사용자 소개", example = "안녕하세요 저는 React 개발자입니다")
    private String introduction;

    @Schema(description = "사용자 소개")
    private List<SignupUserImg> userImgs = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    public static class SignupUserImg {

        private Long userImgId;

    }

    // getUserImgs() 메서드 추가
    public List<SignupUserImg> getUserImgs() {
        return userImgs;
    }

}