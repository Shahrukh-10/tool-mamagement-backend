package com.product.oneforall.services;

import com.product.oneforall.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User getUserDetail(String id);

    List<User> findByUsername(String username);



//    CREATE ACCOUNT
    User createUser(String username,String email ,String password);

//    LOGIN
    User loginByUsername(String username, String password);
    User loginByEmail(String email, String password);

    //    UPDATE PROFILE
    Object updateProfile(String firstname ,String lastname , String id);
}
