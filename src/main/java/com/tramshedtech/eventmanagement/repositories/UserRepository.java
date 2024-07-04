package com.tramshedtech.eventmanagement.repositories;

import com.tramshedtech.eventmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
