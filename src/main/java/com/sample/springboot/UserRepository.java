```java
package com.sample.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = ?2, u.email = ?3 WHERE u.id = ?1")
    int updateUserDetails(Long id, String name, String email);
}
```