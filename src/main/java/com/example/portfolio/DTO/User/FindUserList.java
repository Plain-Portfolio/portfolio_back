package com.example.portfolio.DTO.User;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindUserList {

    List<FindUserListUserDto> userList;

    @Getter
    @NoArgsConstructor
    public static class FindUserListUserDto {

        private Long id;
        private String email;
        private String password;
        private String nickname;

        public FindUserListUserDto(User user) {
            this.id = user.getId();
            this.email = user .getEmail();
            this.password = user.getPassword();
            this.nickname = user .getNickname();
        }
    }
}
