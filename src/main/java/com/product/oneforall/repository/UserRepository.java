package com.product.oneforall.repository;

import com.product.oneforall.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {


    User findByUsername(String username);
}
