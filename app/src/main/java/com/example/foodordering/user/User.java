package com.example.foodordering.user;

public class User
{
    public String fullName;
    public String email;
    public String identity;
    public String phoneNumber;
    public String address;

    public String restaurantOpeningHours;
    public String restaurantName;
    public String restaurantAddress;
    public String restaurantPhoneNumber;

    public String icon_base64;

    /*
        Including restaurant info in User class since it's already mixed with restaurant info.
     */

    public User(String fullName, String email, String identity, String phoneNumber, String address) {
        this.fullName = fullName;
        this.email = email;
        this.identity = identity;
        this.phoneNumber = phoneNumber;
        this.address = address;

        this.restaurantOpeningHours = "";
        this.restaurantName = "";
        this.restaurantAddress = "";
        this.restaurantPhoneNumber = "";

        this.icon_base64 = "";
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getRestaurantOpeningHours() {
        return restaurantOpeningHours;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public User ()//這個不要刪，是拿來讀取firebase的
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getIdentity()
    {
        return this.identity;
    }
}
