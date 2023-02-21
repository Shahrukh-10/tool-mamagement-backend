package com.product.oneforall.services;

import com.product.oneforall.exception.PasswordMismatchException;
import com.product.oneforall.models.User;
import com.product.oneforall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.product.oneforall.constants.OneForAllConstants.*;

@Service
public class UserServiceImplementaion implements UserService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserDetail(String id) {
        Criteria criteria = Criteria.where(ID).is(id);
        Query query = new Query(criteria);
        if (mongoTemplate.exists(query, User.class)) {
            return mongoTemplate.findOne(query, User.class);
        } else {
            throw new RuntimeException("No user exist with this id..");
        }
    }

    @Override
    public User createUser(String username, String email, String password) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where(USERNAME).is(username),
                Criteria.where(EMAIL).is(email)
        );
        Query query = new Query(criteria);
        if (mongoTemplate.exists(query, User.class)) {
            return User.builder()
                    .username(username)
                    .email(email).password(password).build();
        } else {
            User user = User.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .build();
            return userRepository.save(user);
        }
    }

    @Override
    public List<User> findByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        System.out.println(query);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    /*
        Login By username
     */
    public User loginByUsername(String username, String password) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        if (!mongoTemplate.exists(query, User.class)) {
            return null;
        }
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new PasswordMismatchException("incorrect password");
        }

    }

    @Override
    /*
        Login By email
     */
    public User loginByEmail(String email, String password) {
        Criteria criteria = Criteria.where("email").is(email);
        Query query = new Query(criteria);
        if (!mongoTemplate.exists(query, User.class)) {
            return null;
        }
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new PasswordMismatchException("incorrect password");
        }

    }

    @Override
    public Object updateProfile(String firstname, String lastname, String id) {
        HashMap<String , Object> hs = new HashMap<>();
            Optional<User> user = userRepository.findById(id);
            if (user == null) {
                hs.put("msg","invalid_user");
            }else{
                User user1 = user.get();
                user1.setFirstName(firstname);
                user1.setLastName(lastname);
                user1.setStatus("active_user");
                user1.setRole("normal_user");
                userRepository.save(user1);
                hs.put("user",user1);
                hs.put("msg","update_success");
            }
        return hs;
    }

}
