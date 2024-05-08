package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import com.example.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User createdUser = userService.create(userRequestDto);

        assertNotNull(createdUser);
        assertEquals(savedUser.getId(), createdUser.getId());
        assertEquals(savedUser.getEmail(), createdUser.getEmail());
        assertEquals(savedUser.getFirstName(), createdUser.getFirstName());
        assertEquals(savedUser.getLastName(), createdUser.getLastName());
    }

    @Test
    void updateUser_UserExists_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("test@example.com");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.update(userRequestDto);

        assertNotNull(updatedUser);
        assertEquals(existingUser.getId(), updatedUser.getId());
        assertEquals(existingUser.getEmail(), updatedUser.getEmail());
        assertEquals(existingUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(existingUser.getLastName(), updatedUser.getLastName());
    }

    @Test
    void updateUser_UserNotFound_ExceptionThrown() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(userRequestDto));
    }

    @Test
    void deleteUser_UserExists_Success() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.delete(userId);

        assertTrue(existingUser.isDeleted());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void deleteUser_UserNotFound_ExceptionThrown() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
    }

    @Test
    void findUsersByBirthRange_ValidRange_Success() {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 12, 31);
        List<User> usersInDateRange = new ArrayList<>();

        when(userRepository.findByDateOfBirthBetweenAndIsDeletedFalse(fromDate, toDate))
                .thenReturn(usersInDateRange);

        List<User> foundUsers = userService.findUsersByBirthRange(fromDate, toDate);

        assertEquals(usersInDateRange, foundUsers);
    }

    @Test
    void findUsersByBirthRange_InvalidRange_ExceptionThrown() {
        LocalDate fromDate = LocalDate.of(2001, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 12, 31);

        assertThrows(IllegalArgumentException.class, () -> userService
                .findUsersByBirthRange(fromDate, toDate));
    }
}
