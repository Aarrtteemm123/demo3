package com.example.demo.Model;

public class Password {
    private String password;

    public Password() {
        password = new String();
    }

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
