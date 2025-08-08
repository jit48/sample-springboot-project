```java
package com.sample.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long userId, String name, int age) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(name);
        user.setAge(age);

        return userRepository.save(user);
    }
}

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

### Explanation:
1. **Service Layer Addition**: I've added a `UserService` with a method `updateUser` to handle updating user information. This method locates the existing user by ID, updates the fields, and saves the user back to the repository.
   
2. **Repository Interface**: A `UserRepository` interface has been added, extending `JpaRepository`, which provides the data access layer for `User` entities.

3. **Transaction Management**: The `@Transactional` annotation is used in the service method to ensure database transactions are handled correctly, which helps prevent unintended deletions or state inconsistencies.

This setup focuses on correctness in the data access layer where CRUD operations are properly executed.