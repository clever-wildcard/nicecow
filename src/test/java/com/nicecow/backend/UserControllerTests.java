package com.nicecow.backend;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserControllerTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User("John", "Doe", "john.doe@email.com", "johnny", "strong-password");
        userRepository.save(user);
        userRepository.findById(new Long(1))
                .map(newUser -> {
                    Assert.assertEquals("John", newUser.getName());
                    return true;
                });
    }

    @Test
    public void getUsers() {
        User user = new User("John", "Doe", "john.doe@email.com", "johnny", "strong-password");
        User user2 = new User("Daniel", "Marcus", "daniel@daniel.com", "danie", "super-strong-password");
        userRepository.save(user);
        userRepository.save(user2);

        Assert.assertNotNull(userRepository.findAll());
    }

    @Test
    public void deleteUser() {
        User user = new User("John", "Doe", "john.doe@email.com", "johnny", "strong-password");
        userRepository.save(user);
        userRepository.delete(user);
        Assert.assertTrue(userRepository.findAll().isEmpty());
    }
}
