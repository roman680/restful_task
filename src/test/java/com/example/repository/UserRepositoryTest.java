package com.example.repository;

import com.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        entityManager.persist(user);
        entityManager.flush();

        User foundUser = userRepository.findByEmail("test@example.com").orElse(null);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testFindByDateOfBirthBetweenAndIsDeletedFalse() {
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        entityManager.persist(user1);

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setDateOfBirth(LocalDate.of(1995, 6, 15));
        entityManager.persist(user2);

        entityManager.flush();

        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(1995, 6, 30);
        List<User> users = userRepository.findByDateOfBirthBetweenAndIsDeletedFalse
                (fromDate, toDate);

        Assertions.assertEquals(2, users.size());
    }
}