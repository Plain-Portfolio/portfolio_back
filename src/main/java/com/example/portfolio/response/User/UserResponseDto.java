package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.response.Project.CreateProjectResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private List<UserImgDto> userImgs;

    public UserResponseDto (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.userImgs = user.getUserImgs().stream()
                .map(userImg -> new UserImgDto(userImg))
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public class UserImgDto {

        Long userImgId;
        String imgSrc;

        public UserImgDto(UserImg userImg) {
            this.imgSrc = userImg.getImgSrc();
            this.userImgId = userImg.getId();
        }
    }
}
