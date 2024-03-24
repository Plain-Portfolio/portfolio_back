package com.example.portfolio.response;

public class CommonResponse<T> {
    private String status;
    private String message;
    private T data;
}