package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb2xhMjAxMTJxMTMzMTI0QG5hdmVyLmNvbSIsImlhdCI6MTcxMTM3NDg4NSwiZXhwIjoxNzExNzM0ODg1fQ.deFR8KUY2KwTCsZrP8cwE2J1Ax3bDVwYuz62Vxpc_otRlbNOmwJBC59-YiX9qj3_wEdlprCEdhk1j4V0l-nMIA")
    private String token;

    public LoginResponse (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
    }
}
