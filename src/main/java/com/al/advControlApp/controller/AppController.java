package com.al.advControlApp.controller;

import com.al.advControlApp.auth.JwtTokenUtil;
import com.al.advControlApp.model.App;
import com.al.advControlApp.model.User;
import com.al.advControlApp.repository.AppRepository;
import com.al.advControlApp.service.AppService;
import com.al.advControlApp.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppController {

    private static Logger LOGGER = Logger.getLogger(AppController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AppService appService;

    @GetMapping("/apps")
    public List<App> getAllAppsDependsOnRole(HttpServletRequest req) {
        String userIdFromToken = jwtTokenUtil.getClimeToken(req, "userId");
        return appService.getAllAppsDependsOnRole(userIdFromToken);
    }

    @GetMapping("/apps/{id}")
    public App getAppByIdDependsOnRole(@PathVariable("id") long id, HttpServletRequest req) {
        String userIdFromToken = jwtTokenUtil.getClimeToken(req, "userId");
        return appService.getAppByIdDependsOnRole(id, userIdFromToken);
    }

    @PostMapping(path = "/apps", consumes = "application/json", produces = "application/json")
    public App saveAppDependsOnRole(@RequestBody App app, HttpServletRequest req) {
        String userIdFromToken = jwtTokenUtil.getClimeToken(req, "userId");
        return appService.saveAppDependsOnRole(app, userIdFromToken);
    }

    @DeleteMapping("/apps/{id}")
    public void deleteAppByIdDependsOnRole(@PathVariable("id") long appId, HttpServletRequest req) {
        String userIdFromToken = jwtTokenUtil.getClimeToken(req, "userId");
        appService.deleteAppDependsOnRole(appId, userIdFromToken);
    }

    @PutMapping(path = "/apps", consumes = "application/json", produces = "application/json")
    public App updateAppByIdDependsOnRole(@RequestBody App app, HttpServletRequest req) {
        String userIdFromToken = jwtTokenUtil.getClimeToken(req, "userId");
        return appService.updateAppDependsOnRole(app, userIdFromToken);
    }

}
