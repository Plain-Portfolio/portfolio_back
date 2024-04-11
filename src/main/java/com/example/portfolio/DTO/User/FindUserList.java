package com.example.portfolio.DTO.User;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
        private String Introduction;
        private List<UserImgDto> userImgs;

        public FindUserListUserDto(User user) {
            this.id = user.getId();
            this.email = user .getEmail();
            this.password = user.getPassword();
            this.nickname = user.getNickname();
            this.Introduction = user.getIntroduction();
            this.userImgs = user.getUserImgs().stream()
                    .map(userImg -> new UserImgDto(userImg))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UserImgDto {

        Long userImgId;
        String imgSrc;

        public UserImgDto(UserImg userImg) {
            this.imgSrc = userImg.getImgSrc();
            this.userImgId = userImg.getId();
        }
    }
}
