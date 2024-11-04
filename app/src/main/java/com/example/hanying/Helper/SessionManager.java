package com.example.hanying.Helper;

public class SessionManager {
    private static SessionManager instance;

    private String username;
    private String fullname;

    private SessionManager() {
        // Private constructor untuk Singleton
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserData(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullname;
    }
}

