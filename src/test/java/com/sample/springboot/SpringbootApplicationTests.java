```java
package com.sample.springboot.service;

import com.sample.springboot.model.User;
import com.sample.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long userId, User newUserData) {
        return userRepository.findById(userId).map(user -> {
            user.setName(newUserData.getName());
            user.setEmail(newUserData.getEmail());
            user.setAddress(newUserData.getAddress());
            // Update other fields as necessary...
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
```

```java
package com.sample.springboot.controller;

import com.sample.springboot.model.User;
import com.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUserData) {
        User updatedUser = userService.updateUser(id, newUserData);
        return ResponseEntity.ok(updatedUser);
    }
}
```

```java
package com.sample.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String address;
    // Add other fields as necessary...

    // Getters and Setters...
}
```

```java
package com.sample.springboot.repository;

import com.sample.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

Note: The code above assumes the presence of a typical Spring Boot setup with JPA and a User entity. The method `updateUser` in the `UserService` class has been designed such that it updates an existing user's information without potentially deleting them. The controller class, `UserController`, exposes an API endpoint to handle PUT requests for updating user information.