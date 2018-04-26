package com.al.advControlApp.repository;

import com.al.advControlApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String username);

    List<User> findByEmail(String email);

    List<User> findByRole(User.UserRole role);


}
