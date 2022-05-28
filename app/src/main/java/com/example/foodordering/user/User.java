package com.example.foodordering.user;

public class User
{
    public String fullName;
    public String email;
    public String identidy;
    public String phone;
    public String address;

    public User ()//這個不要刪，是拿來讀取firebase的
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String email, String identidy, String phone, String address)
    {
        this.fullName = fullName;
        this.email = email;
        this.identidy = identidy;
        this.phone = phone;
        this.address = address;
    }

    public String getIdentidy()
    {
        return this.identidy;
    }
}
