package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {

    private User user;
    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb2xhMjAxMTJxMTMzMTI0QG5hdmVyLmNvbSIsImlhdCI6MTcxMTM3NDg4NSwiZXhwIjoxNzExNzM0ODg1fQ.deFR8KUY2KwTCsZrP8cwE2J1Ax3bDVwYuz62Vxpc_otRlbNOmwJBC59-YiX9qj3_wEdlprCEdhk1j4V0l-nMIA")
    private String token;
}
