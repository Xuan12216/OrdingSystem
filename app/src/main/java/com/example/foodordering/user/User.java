package com.example.foodordering.user;

public class User
{
    private static String identidy;
    private String fullName;
    private String email;

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

    public static String getIdentidy() { return identidy; }
}
