package com.example.demo.model;

public class Authenticated {
    private boolean authenticated;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Authenticated(boolean authenticated, User user) {
        this.authenticated = authenticated;
        this.user = user;
    }
}
