package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import lombok.Data;

@Data
public class SocialLoginRes {

    Long userId;
    String email;
    String nickname;

    public SocialLoginRes (User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
