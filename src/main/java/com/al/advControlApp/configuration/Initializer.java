package com.al.advControlApp.configuration;

import com.al.advControlApp.model.User;
import com.al.advControlApp.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Initializer {

    private static Logger LOGGER = Logger.getLogger(Initializer.class);

    @Autowired
    private UserRepository userRepository;

    private String adminUsername = "admin";
    private String adminEmail = "admin@admin.com";
    private String adminPassword = "admin";


    @PostConstruct
    private void init() {
        buildAdmin();
    }

    private void buildAdmin() {
        //here I try to retrieve the Admin from my persistence layer
        List<User> admins = userRepository.findByName(adminUsername);
        User admin = null;

        if (admins.size() == 1) {
            admin = admins.get(0);
        } else if (admins.size() > 1) {
            LOGGER.error(admins.size() + " users with ADMIN role found in the system");
            return;
        }

        //If the application is started for the first time (e.g., the admin is not in the DB)
        if (admin == null) {

            try {
                //create a user for the admin
                admin = new User(adminUsername, adminEmail, adminPassword, User.ADMIN);
                userRepository.save(admin);
                LOGGER.info("Admin created and saved to DB");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("Errors occurred during initialization. System verification is required.");
            }
        }
    }
}