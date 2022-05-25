package com.example.foodordering.user;

import java.util.HashMap;

public class User {

    private String fullName = "";
    private String email = "";
    private String identity = "";
    private String phone = "";
    private String address = "";

    private HashMap<String, String> info;

    public User(String fullName, String email, String identity) {
        this.fullName = fullName;
        this.email = email;
        this.identity = identity;
    }

    public User(String fullName, String email, String identity, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.identity = identity;
        this.phone = phone;
        this.address = address;
    }

    public HashMap<String, String> getAccountInformation() {
        HashMap<String, String> info = new HashMap<>();
        info.put("Name", fullName);
        info.put("Email", email);
        info.put("Identity", identity);
        info.put("Phone", phone);
        info.put("Address", address);

        return info;
    }

    public void setAccountInformation(HashMap<String, String> newInfo) {
        this.info = newInfo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
