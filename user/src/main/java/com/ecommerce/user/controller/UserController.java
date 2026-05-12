package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;
//    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        System.out.println("Request received.");
        return new ResponseEntity<> (userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> geyUserById(@PathVariable String id) {
        log.info("Fetching user with id: {}", id);
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ResponseEntity<> ("User created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest updateUserRequest) {
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated) {
            return new ResponseEntity<> ("User updated successfully", HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
