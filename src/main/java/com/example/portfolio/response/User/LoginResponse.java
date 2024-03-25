package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {

    private User user;
    private String token;
}
