package com.example.foodordering.restaurant;

import java.util.ArrayList;
import java.util.List;

public class FoodMenu {
    private List<Food> foodList;

    public FoodMenu() {
        this.foodList = new ArrayList<>();
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    // Return type should be boolean or void?
    public void addFood(Food food) {
        this.foodList.add(food);
    }
}
