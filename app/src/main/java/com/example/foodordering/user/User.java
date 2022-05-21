package com.example.foodordering.user;

public class User
{
    private String fullName;
    private String email;
    private String identidy;

    public User(String fullName, String email, String identidy)
    {
        this.fullName = fullName;
        this.email = email;
        this.identidy = identidy;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentidy() { return identidy; }
}
