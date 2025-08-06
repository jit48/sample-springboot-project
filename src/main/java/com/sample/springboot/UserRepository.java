```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Transactional
    default <S extends User> S save(S user) {
        if (user.getId() != null) {
            return updateExistingUser(user);
        } else {
            return JpaRepository.super.save(user);
        }
    }

    @Transactional
    default <S extends User> S updateExistingUser(S user) {
        // Implement logic to update user information here without removing the user
        // Fetch the existing user, update the necessary fields, and save again

        User existingUser = findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        // Update other fields as necessary

        return JpaRepository.super.save((S) existingUser);
    }
}
```

Note: The specific fields such as `name` and `email` are just examples. Ensure to update the necessary fields that are relevant to your `User` entity. Also, this solution assumes that `getId()`, `setName()`, and other similar methods are present on the `User` entity, which should be verified and adapted according to the actual implementation.