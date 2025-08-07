```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Transactional
    default User updateUser(User user) {
        if (existsById(user.getId())) {
            return save(user);
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }
}
```