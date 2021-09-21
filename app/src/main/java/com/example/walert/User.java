package com.example.walert;


public class User {

    public String email, password, confirm_password, name;

    public User(){

    }
    public User(String email, String password, String confirm_password, String name){
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.name = name;
    }
}
