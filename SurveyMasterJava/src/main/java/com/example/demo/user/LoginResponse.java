package com.example.demo.user;

public class LoginResponse {

    private User user;

    public LoginResponse( User user) {

        this.user = user;
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
