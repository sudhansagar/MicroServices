package com.madhu.flightreservation.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.madhu.flightreservation.entities.User;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("UserRepository Test")
class UserRepositoryTest {

    private static final String EMAIL = "test@example.com";

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("findByEmail returns user")
    void findByEmailReturnsUser() {
        User user = new User();
        user.setEmail(EMAIL);
        repository.save(user);

        User result = repository.findByEmail(EMAIL);

        assertEquals(EMAIL, result.getEmail());
    }
}
