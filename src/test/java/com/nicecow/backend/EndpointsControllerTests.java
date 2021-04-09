package com.nicecow.backend;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EndpointsControllerTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User("johnny", "123-456-7890");
        userRepository.save(user);
        userRepository.findById(new Long(1))
                .map(newUser -> {
                    Assert.assertEquals("johnny", newUser.getUsername());
                    return true;
                });
    }

    @Test
    public void getUsers() {
        User user = new User("johnny", "123-456-7890");
        User user2 = new User("danie", "123-456-7890");
        userRepository.save(user);
        userRepository.save(user2);

        Assert.assertNotNull(userRepository.findAll());
    }

    @Test
    public void deleteUser() {
        User user = new User("johnny", "123-456-7890");
        userRepository.save(user);
        userRepository.delete(user);
        Assert.assertTrue(userRepository.findAll().isEmpty());
    }
}
