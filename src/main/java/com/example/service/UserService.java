package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.model.User;
import com.example.model.dto.UserRequestDto;

public interface UserService {

    User create(UserRequestDto userRequestDto);

    User update(UserRequestDto userRequestDto);

    void delete(Long id);

    Optional<List<User>> findUsersByBirthRange(Date from, Date to);
}
