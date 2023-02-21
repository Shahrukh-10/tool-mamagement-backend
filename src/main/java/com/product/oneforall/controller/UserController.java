package com.product.oneforall.controller;

import com.product.oneforall.models.User;
import com.product.oneforall.repository.UserRepository;
import com.product.oneforall.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createAccount")
    public ResponseEntity<ApiResponse> createAccount(@RequestBody User user) {

        ApiResponse cr = new ApiResponse();
        User newUser = userService.createUser(user.getUsername(), user.getEmail(), user.getPassword());
        if (user.equals(newUser)){
            cr.setMessage("Account already exists");
            cr.setUser(newUser);
            return ResponseEntity.status(HttpStatus.OK).body(cr);
        }
        cr.setUser(newUser);
        cr.setMessage("Account created successfully ... ");
        return ResponseEntity.ok(cr);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user) {
        ApiResponse cr = new ApiResponse();
        User user1;

        if (user.getUsername() != null && !user.getUsername().contains("@")) {
            user1 = userService.loginByUsername(user.getUsername(), user.getPassword());
        } else if (user.getUsername() != null && user.getUsername().contains("@") ) {
            user.setEmail(user.getUsername());
            user1 = userService.loginByEmail(user.getEmail(), user.getPassword());
        } else {
            cr.setMessage("Username and email should not be null");
            return new ResponseEntity<>(cr, HttpStatus.BAD_REQUEST);
        }
        if (user1 == null) {
            cr.setMessage("No such user exist ! , create account first");
            return new ResponseEntity<>(cr, HttpStatus.BAD_REQUEST);
        } else {
            cr.setUser(user1);
            cr.setMessage("Logged In");
            return ResponseEntity.ok(cr);
        }
    }

    @GetMapping("/getAll")
    public List<User> test() {
        return userService.getAllUser();
    }

    @GetMapping("/getUserDetail")
    public User getUserDetail(@RequestBody User user) {
        return userService.getUserDetail(user.getId());
    }


    @GetMapping("/getUsersUsername")
    public List<User> getExampleByUsername(@RequestBody String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<Object> updateUser(@RequestBody User user){
        HashMap<String,Object> hs = (HashMap<String, Object>) userService.updateProfile(user.getFirstName(),user.getLastName(),user.getId());
        if (hs.get("msg").equals("invalid_user")){
            return new ResponseEntity<>(hs,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hs.get("user"),HttpStatus.OK);
    }
}
