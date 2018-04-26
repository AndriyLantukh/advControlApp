package com.al.advControlApp.service;

import com.al.advControlApp.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    List<User> findByRole(User.UserRole role);

    void delete(long id);

    User findById(Long id);

    boolean existsById(Long id);

    List<User> getAllUsersDependsOnRole(String role);

    User getUsersByIdDependsOnRole(Long id, String role);

    User saveUserDependsOnRole(User user, String role);

    void deleteUserDependsOnRole(Long id, String role);

    User updateUserDependsOnRole(User user, String role);

}