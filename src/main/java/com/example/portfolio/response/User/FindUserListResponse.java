package com.example.portfolio.response.User;

import com.example.portfolio.Domain.User;
import lombok.Data;

import java.util.List;

@Data
public class FindUserListResponse {

    private List<User> users;
}
