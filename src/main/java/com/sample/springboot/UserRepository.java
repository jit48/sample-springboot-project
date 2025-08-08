```java
package com.sample.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sample.springboot.UserRepository;
import com.sample.springboot.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long userId, User updatedUserInfo) {
        return userRepository.findById(userId)
            .map(user -> {
                user.setName(updatedUserInfo.getName());
                user.setEmail(updatedUserInfo.getEmail());
                // Add more fields as necessary
                return userRepository.save(user);
            })
            .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
    }
}

// Custom exception class for handling user not found scenario
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

This code snippet introduces a `UserService` class with an `updateUser` method that ensures the user is fetched by ID before applying updates and saving it back to the database. It utilizes `save()` method of `JpaRepository` to persist the updates and includes a custom exception `UserNotFoundException` to handle cases where the user is not found. No modifications are needed to `UserRepository` as it delegates operations to the correct methods of `JpaRepository`.