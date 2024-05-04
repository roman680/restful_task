package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isDeleted = false")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.dateOfBirth BETWEEN :from AND :to AND u.isDeleted = false")
    List<User> findByDateOfBirthBetweenAndIsDeletedFalse(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
