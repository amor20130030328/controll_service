package com.amore.springboot.explore.bean;

import lombok.*;

@Data
public class App {

    private String id;
    private String appName;
    private String appPackageName ;
    private String appKey;
    private String appActivity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", appName='" + appName + '\'' +
                ", appPackageName='" + appPackageName + '\'' +
                ", appKey='" + appKey + '\'' +
                ", appActivity='" + appActivity + '\'' +
                '}';
    }

}
