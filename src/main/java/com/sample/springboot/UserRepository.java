```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    <S extends User> S save(S user);

    // Removing any custom methods that might have caused the issue. 
    // Ensuring only save is properly overridden and no delete or similar methods are interfering.
}
``` 

Note: Since the issue description suggests an unexpected delete operation, it's crucial to review all places in the application where user updates are being performed. This may not solely be a repository issue, and could involve service-layer logic. Ensure that wherever the `save` method is used, it is indeed intended to update existing records and not perform deletions inadvertently.