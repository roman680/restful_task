package com.example.controller;

import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Test
    void createUser_ValidUser_Returns201() {
        UserRequestDto requestDto = new UserRequestDto("test@example.com",
                "John", "Doe", LocalDate.now(), "Address", "1234567890");
        UserService userService = mock(UserService.class);
        when(userService.create(any(UserRequestDto.class))).thenReturn(new User());
        UserController controller = new UserController(userService);

        ResponseEntity<Object> response = controller.createUser(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateUser_ValidUser_Returns200() {
        UserRequestDto requestDto = new UserRequestDto("test@example.com", "John",
                "Doe", LocalDate.now(), "Address", "1234567890");
        UserService userService = mock(UserService.class);
        when(userService.update(any(UserRequestDto.class))).thenReturn(new User());
        UserController controller = new UserController(userService);

        ResponseEntity<Object> response = controller.updateUser(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUserById_ValidUserId_Returns200() {
        Long userId = 1L;
        UserService userService = mock(UserService.class);
        UserController controller = new UserController(userService);

        ResponseEntity<Object> response = controller.deleteUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUsersByBirthRange_ValidRange_Returns200() {
        LocalDate from = LocalDate.now().minusYears(30);
        LocalDate to = LocalDate.now();
        UserService userService = mock(UserService.class);
        when(userService.findUsersByBirthRange(from, to)).thenReturn(Collections
                .singletonList(new User()));
        UserController controller = new UserController(userService);

        ResponseEntity<Object> response = controller.getUsersByBirthRange(from, to);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
