package com.al.advControlApp.service;

import com.al.advControlApp.model.User;
import com.al.advControlApp.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.size() != 1) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        User user = byEmail.get(0);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getRole().toString()));
    }

    private List<SimpleGrantedAuthority> getAuthority(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            return user;
        }
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsersDependsOnRole(String role) {
        List<User> users = new ArrayList<>();
        if (User.ADMIN.toString().equals(role)) {
            users = findAll();
        } else if (User.ADOPS.toString().equals(role)) {
            users = findByRole(User.UserRole.PUBLISHER);
        }
        return users;
    }

    public User getUsersByIdDependsOnRole(Long id, String role) {
        if (User.ADMIN.toString().equals(role)) {
            return findById(id);
        } else if (User.ADOPS.toString().equals(role)) {
            User userByid = findById(id);
            if (userByid.getRole().equals(User.UserRole.PUBLISHER)) {
                return userByid;
            }
        }
        return null;
    }

    public User saveUserDependsOnRole(User user, String role) {
        if (User.ADMIN.toString().equals(role)) {
            return save(user);
        } else if (User.ADOPS.toString().equals(role) && User.PUBLISHER.equals(user.getRole())) {
            return save(user);
        }
        return null;
    }

    public void deleteUserDependsOnRole(Long id, String role) {
        if (User.ADMIN.toString().equals(role)) {
            delete(id);
        } else if (User.ADOPS.toString().equals(role) && User.PUBLISHER.equals(findById(id).getRole())) {
            delete(id);
        }
    }

    public User updateUserDependsOnRole(User user, String role) {

        if (user.getId() != null && existsById(user.getId())) {
            if (User.ADMIN.toString().equals(role)) {
                return save(user);
            } else if (User.ADOPS.toString().equals(role) && User.PUBLISHER.equals(user.getRole())) {
                return save(user);
            }

        }
        LOGGER.error("userID == null or User not found in repository by ID");
        return null;
    }
}
