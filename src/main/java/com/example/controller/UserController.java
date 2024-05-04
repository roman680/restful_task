package com.example.controller;

import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import com.example.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
            User user = userService.create(userRequestDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserRequestDto userRequestDto) {
            User user = userService.update(userRequestDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/findInPeriod")
    public ResponseEntity<Object> getUsersByBirthRange(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
            List<User> users = userService.findUsersByBirthRange(from, to);
            return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
