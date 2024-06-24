package com.example.nhac.Model;

public class role {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private String user = USER;
    private String admin = ADMIN;

    public role() {
    }

    public role(String user, String admin) {
        this.user = user;
        this.admin = admin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }


}
