package com.example.secondapp.models.entities;

/**
 * Created by liran on 08/11/2017.
 */

public class User {
    private String name;
    private int password;

    public User(String Name, int Password) {
        this.name = Name;
        this.password = Password;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getPassword() {return password;}

    public void setPassword(int password) {this.password = password;}
}
