package com.al.advControlApp.service;

import com.al.advControlApp.model.App;
import com.al.advControlApp.model.User;
import com.al.advControlApp.repository.AppRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "appService")
public class AppServiceImpl implements AppService {

    private static Logger LOGGER = Logger.getLogger(AppServiceImpl.class);

    @Autowired
    AppRepository appRepository;

    @Autowired
    UserService userService;

    @Override
    public App save(App app) {
        return appRepository.save(app);
    }

    @Override
    public List<App> findAll() {
        return appRepository.findAll();
    }

    @Override
    public List<App> findByAppUserId(Long appUserId) {
        return appRepository.findByAppUserId(appUserId);
    }

    @Override
    public void delete(long id) {
        appRepository.deleteById(id);
    }

    @Override
    public App findById(Long id) {
        Optional<App> appById = appRepository.findById(id);
        if(appById.isPresent()) {
            return appById.get();
        }
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return appRepository.existsById(id);
    }

    @Override
    public List<App> getAllAppsDependsOnRole(String sessionUserId) {
        User sessionUser = userService.findById(getNumberId(sessionUserId));
        if (sessionUser == null) {
            LOGGER.error("Can't get session user by id " + sessionUserId);
            return null;
        }
        if (User.ADOPS.equals(sessionUser.getRole())) {
            return findAll();
        } else if (User.PUBLISHER.equals(sessionUser.getRole())) {
            return findByAppUserId(sessionUser.getId());
        }
        LOGGER.error("ADMIN got access to app component");
        return null;
    }

    @Override
    public App getAppByIdDependsOnRole(Long id, String sessionUserId) {
        User sessionUser = userService.findById(getNumberId(sessionUserId));
        if (sessionUser == null) {
            LOGGER.error("Can't get session user by id " + sessionUserId);
            return null;
        }
        if (User.ADOPS.equals(sessionUser.getRole())) {
            return findById(id);
        } else if (User.PUBLISHER.equals(sessionUser.getRole())) {
            App appByid = findById(id);
            if (appByid.getAppUserId() == sessionUser.getId()) {
                return appByid;
            }
        }
        LOGGER.error("ADMIN got access to app component");
        return null;
    }

    @Override
    public App saveAppDependsOnRole(App app, String sessionUserId) {
        User sessionUser = userService.findById(getNumberId(sessionUserId));
        if (sessionUser == null) {
            LOGGER.error("Can't get session user by id " + sessionUserId);
            return null;
        }
        if (User.ADMIN.equals(sessionUser.getRole())) {
            LOGGER.error("ADMIN got access to app component");
            return null;
        }
        app.setAppUserId(sessionUser.getId());
        app.setAppUserName(sessionUser.getName());
        return appRepository.save(app);
    }

    @Override
    public void deleteAppDependsOnRole(Long id, String sessionUserId) {
        User sessionUser = userService.findById(getNumberId(sessionUserId));
        if (sessionUser == null) {
            LOGGER.error("Can't get session user by id " + sessionUserId);
            return;
        } else if (User.ADOPS.equals(sessionUser.getRole())) {
            delete(id);
        } else if (User.PUBLISHER.equals(sessionUser.getRole())) {
            App appByid = findById(id);
            if (appByid.getAppUserId() == sessionUser.getId()) {
                delete(id);
            }
        } else {
            LOGGER.error("ADMIN got access to app component");
        }
    }

    @Override
    public App updateAppDependsOnRole(App app, String sessionUserId) {
        User sessionUser = userService.findById(getNumberId(sessionUserId));
        if (sessionUser == null) {
            LOGGER.error("Can't get session user by id " + sessionUserId);
            return null;
        } else if (User.ADOPS.equals(sessionUser.getRole()) && app.getId() != null) {
            return save(app);
        } else if (User.PUBLISHER.equals(sessionUser.getRole()) && sessionUser.getId() == app.getAppUserId()) {
            return save(app);

        } else {
            LOGGER.error("ADMIN got access to app component");
            return null;
        }
    }

    private Long getNumberId(String stringId) {
        try {
            return Long.valueOf(stringId);
        } catch (NumberFormatException e) {
            LOGGER.error("Can't parse userId from token " + stringId);
            return null;
        }
    }


}
