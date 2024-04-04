package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;

    public UserResponseDto (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
    }
}
