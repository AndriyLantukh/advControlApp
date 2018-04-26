package com.al.advControlApp.service;

import com.al.advControlApp.model.App;

import java.util.List;

public interface AppService {

    App save(App app);

    List<App> findAll();

    List<App> findByAppUserId(Long appUserId);

    void delete(long id);

    App findById(Long id);

    boolean existsById(Long id);

    List<App> getAllAppsDependsOnRole(String sessionUserId);

    App getAppByIdDependsOnRole(Long id, String sessionUserId);

    App saveAppDependsOnRole(App app, String sessionUserId);

    void deleteAppDependsOnRole(Long id, String sessionUserId);

    App updateAppDependsOnRole(App app, String sessionUserId);
}
