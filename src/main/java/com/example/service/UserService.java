package com.example.service;

import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User create(UserRequestDto userRequestDto);

    User update(UserRequestDto userRequestDto);

    void delete(Long id);

    List<User> findUsersByBirthRange(LocalDate from, LocalDate to);
}
