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
    public String totalMeals;

    public String meal_name;
    public String meal_price;
    public String image_link;
    public String seller_id;

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_price() {
        return meal_price;
    }

    public void setMeal_price(String meal_price) {
        this.meal_price = meal_price;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
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
        this.totalMeals= "0";
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

    public String getTotalMeals() { return totalMeals; }

    public User ()//這個不要刪，是拿來讀取firebase的
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getIdentity()
    {
        return this.identity;
    }
}
