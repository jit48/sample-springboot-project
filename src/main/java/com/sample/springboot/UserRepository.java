```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Transactional
    default User updateUser(Long userId, User updatedUser) {
        return findById(userId).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            // update other fields as necessary
            return save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
```