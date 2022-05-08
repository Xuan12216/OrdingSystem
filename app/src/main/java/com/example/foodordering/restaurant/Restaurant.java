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

    private String name;
    private FoodMenu foodMenu;

    public Restaurant(String name) {
        this.name = name;
        this.foodMenu = new FoodMenu();
    }

    public Restaurant(String name, FoodMenu foodMenu) {
        this.name = name;
        this.foodMenu = foodMenu;
    }

    public String getName() {
        return name;
    }
}
