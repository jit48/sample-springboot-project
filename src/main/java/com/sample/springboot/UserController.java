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
        Optional<User> existingUserOptional = userRepository.findById(id);
        
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update fields of existingUser with user information from the request body
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            // Add any other fields that need to be updated
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user with the given id does not exist
            // This might throw an exception or return an error response, depending on requirements
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
```