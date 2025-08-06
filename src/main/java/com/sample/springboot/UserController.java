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

    // Fixed: Now updates the user instead of deleting
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Assuming you want to update all fields of the user; adjust as necessary
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            // Add any other fields that need to be updated
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user doesn't exist
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
    }
}
```