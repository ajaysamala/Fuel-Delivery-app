package com.example.fueldelivery1.model;

public class user {
    private String FullName;
    private String email;
    private String PhoneNumber;
    private String Password;

    public user(String FullName,String email,String phoneNumber,String password) {
        this.FullName = FullName;
        this.email = email;
        this.PhoneNumber = phoneNumber;
        this.Password = password;
    }

    public user() {
    }

    public String getFullName() { return FullName; }

    public void setFullName(String fullName) { FullName = fullName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { this.PhoneNumber = phoneNumber; }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

}
