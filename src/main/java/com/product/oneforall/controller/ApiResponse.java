package com.product.oneforall.controller;

import com.product.oneforall.models.User;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    public String message;
    public User user;
    public List<User> users;
}
