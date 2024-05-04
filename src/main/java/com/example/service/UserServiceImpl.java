package com.example.service;

import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        User user = new User();
        userMapper.updateUserFromDto(userRequestDto, user);
        return userRepository.save(user);
    }

    @Override
    public User update(UserRequestDto userRequestDto) {
        Optional<User> userFromDb = userRepository.findByEmail(userRequestDto.getEmail());
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            userMapper.updateUserFromDto(userRequestDto, user);
            return userRepository.save(user);
        }
        throw new UserNotFoundException("User with email: " + userRequestDto.getEmail() + " not found");
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<User> findUsersByBirthRange(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid date range: 'from' must be before 'to'");
        }

        return userRepository.findByDateOfBirthBetweenAndIsDeletedFalse(from, to);
    }
}
