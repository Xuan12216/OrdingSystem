package com.example.foodordering.user;

public class User
{
    public String fullName;
    public String email;
    public String identidy;

    public User()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String email, String identidy)
    {
        this.fullName = fullName;
        this.email = email;
        this.identidy = identidy;
    }
}
