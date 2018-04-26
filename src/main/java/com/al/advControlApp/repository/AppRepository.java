package com.al.advControlApp.repository;

import com.al.advControlApp.model.App;
import com.al.advControlApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {

    List<App> findByAppUserId(Long appUserId);


}
