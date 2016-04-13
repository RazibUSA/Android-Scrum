package com.gnirt69.mscrum.adapter;

/**
 * Created by Administrator on 9/3/2015.
 */
public class User {

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean gender;

    public User(String fname,String lname, boolean gender, String email, String pass) {
        this.firstName = fname;
        this.lastName = lname;
        this.password = pass;
        this.gender = gender;
        this.email = email;

    }



    public boolean isGender() {
        return gender;
    }
}
