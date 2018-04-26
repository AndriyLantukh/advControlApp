package com.al.advControlApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "APPS")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APP_ID")
    private Long id;

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "APP_TYPE")
    private AppType appType;

    @Column(name = "APP_CONTENT_TYPES")
    @ElementCollection(targetClass=ContentType.class)
    private Set<ContentType> contentTypes;

    @Column(nullable = false)
    private Long appUserId;

    @Column(nullable = false)
    private String appUserName;

    public App(String appName, AppType appType, Set<ContentType> contentTypes, Long appUserId, String appUserName) {
        this.appName = appName;
        this.appType = appType;
        this.contentTypes = contentTypes;
        this.appUserId = appUserId;
        this.appUserName = appUserName;
    }

    public static final AppType IOS = AppType.IOS;
    public static final AppType ANDROID = AppType.ANDROID;
    public static final AppType WEBSITE = AppType.WEBSITE;

    public static final ContentType VIDEO = ContentType.VIDEO;
    public static final ContentType IMAGE = ContentType.IMAGE;
    public static final ContentType HTML = ContentType.HTML;

    public enum AppType {IOS, ANDROID, WEBSITE}

    public enum ContentType {VIDEO, IMAGE, HTML}

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", appType=" + appType +
                ", contentTypes=" + contentTypes +
                ", appUserId=" + appUserId +
                ", appUsername='" + appUserName + '\'' +
                '}';
    }
}



