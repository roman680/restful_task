package com.example.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.model.User;
import com.example.model.dto.UserRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByDateOfBirthBetweenAndIsDeletedFalse(Date from, Date to);
}
