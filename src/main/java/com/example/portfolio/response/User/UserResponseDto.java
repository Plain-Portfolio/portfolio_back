package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private UserImgDto userImg;

    public UserResponseDto (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.userImg = new UserImgDto(user.getUserImg());
    }

    @Getter
    @NoArgsConstructor
    public class UserImgDto {

        String imgSrc;

        public UserImgDto(UserImg userImg) {
            this.imgSrc = userImg.getImgSrc();
        }
    }
}
