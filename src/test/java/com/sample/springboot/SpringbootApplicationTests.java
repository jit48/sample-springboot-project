```java
package com.sample.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserService userService; // Assume UserService is the correct service to manipulate user data

    @Test
    void contextLoads() {
    }

    @Test
    void testUpdateUserInformation() {
        User existingUser = new User("John", "Doe"); // Create a test user
        userService.save(existingUser); // Save the test user

        existingUser.setFirstName("Jane");
        userService.update(existingUser); // Update user information

        User updatedUser = userService.findById(existingUser.getId());
        assertThat(updatedUser.getFirstName()).isEqualTo("Jane");
        assertThat(userService.existsById(existingUser.getId())).isTrue(); // Verify user still exists
    }
}
```