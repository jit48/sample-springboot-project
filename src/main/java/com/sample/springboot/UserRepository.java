```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // No custom methods added here, focus on correct use in the service layer.
}
```

```java
package com.sample.springboot.service;

import com.sample.springboot.User;
import com.sample.springboot.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update user details here
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        // Add other fields to update

        return userRepository.save(user); // ensure 'save' is used to update
    }
    
    // Other service methods
}
```

Please ensure that the `User` entity's setters are properly defined and that `userDetails` passes all necessary and valid information to `updateUser`. Review backing services and clients to make sure they strictly follow update operations intended for `UserService`.