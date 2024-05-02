package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User create(UserRequestDto userRequestDto) {
        return userRepository.save(initializeUser(userRequestDto));
    }

    @Override
    public User update(UserRequestDto userRequestDto) {
        User existingUser = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email "
                        + userRequestDto.getEmail() + " not found"));
        userMapper.updateUserFromDto(userRequestDto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public Optional<List<User>> findUsersByBirthRange(Date from, Date to) {
        List<User> users = userRepository.findByDateOfBirthBetweenAndIsDeletedFalse(from, to);
        return Optional.ofNullable(users);
    }

    private User initializeUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setDateOfBirth(userRequestDto.getDateOfBirth());
        user.setAddress(userRequestDto.getAddress());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        return user;
    }
}
