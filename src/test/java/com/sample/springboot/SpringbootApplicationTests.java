```java
package com.sample.springboot;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1L, "johndoe", "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testUpdateUserInformation() {
        // Given
        User updatedInfo = new User(1L, "johnsmith", "john.smith@example.com");
        
        // When
        when(userRepository.save(any(User.class))).thenReturn(updatedInfo);
        userService.updateUser(1L, updatedInfo);
        
        // Then
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedInfo);
        
        User result = userRepository.findById(1L).orElseThrow();
        assertNotNull(result);
        assertEquals("johnsmith", result.getUsername());
        assertEquals("john.smith@example.com", result.getEmail());
    }

    @Test
    void testUserPersistsAfterUpdate() {
        // Given
        User updatedInfo = new User(1L, "johnsmith", "john.smith@example.com");
        
        // When
        when(userRepository.save(any(User.class))).thenReturn(updatedInfo);
        userService.updateUser(1L, updatedInfo);

        // Then
        verify(userRepository, times(1)).findById(1L);
        User result = userRepository.findById(1L).orElse(null);
        assertNotNull(result, "User should exist after update operation");
    }
}

// Assuming UserService and UserRepository are defined somewhere in the codebase
class UserService {
    @Autowired
    private UserRepository userRepository;

    public void updateUser(Long userId, User userUpdates) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setUsername(userUpdates.getUsername());
            user.setEmail(userUpdates.getEmail());
            userRepository.save(user);
        });
    }
}

interface UserRepository {
    java.util.Optional<User> findById(Long id);
    User save(User user);
}

// Basic User class definition
class User {
    private Long id;
    private String username;
    private String email;

    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```