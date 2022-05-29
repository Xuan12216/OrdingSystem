package com.example.foodordering.restaurant;

public class Restaurant {
    /*
        Attributes:
            - Icon (?)
            - Name
            - Address
            - Food menu
            - Rating (?)
            ...
     */

    private String name = null;
    private String address = null;
    private String phoneNumber = null;

    // Uses String (Temporary)
    private String openingHours = null;

    private FoodMenu foodMenu = null;

    // -----------------------

    public Restaurant() {
        // pass
    }

    public Restaurant(String name) {
        this.name = name;
        this.foodMenu = new FoodMenu();
    }

    public Restaurant(String name, FoodMenu foodMenu) {
        this.name = name;
        this.foodMenu = foodMenu;
    }

    // -------------------------

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public FoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    public String getName() {
        return name;
    }
}
