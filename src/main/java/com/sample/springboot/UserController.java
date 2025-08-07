```java
package com.sample.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName()); // Assuming User has a setName method
            existingUser.setEmail(user.getEmail()); // Assuming User has a setEmail method
            // Add other fields to be updated as necessary
            return userRepository.save(existingUser);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }
}

// Example exception class that could be used
@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```