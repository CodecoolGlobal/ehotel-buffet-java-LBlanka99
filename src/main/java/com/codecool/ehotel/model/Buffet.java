package com.codecool.ehotel.model;

import java.util.ArrayList;
import java.util.List;

public record Buffet (List<Meal> meals) {

    public void addMeal(Meal meal){
        meals.add(meal);
    }

    public boolean removeMeal(MealType type){
        List<Meal> mealsOfAType = getMealsByType(type);
        try {
            meals.remove(mealsOfAType.get(0));
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public List<Meal> getMealsByType(MealType type){
        List<Meal> mealsOfAType = new ArrayList<>();

        for (Meal meal : meals) {
            if(meal.type().equals(type)){
                mealsOfAType.add(meal);
            }
        }
        mealsOfAType.sort((meal1, meal2)
                -> meal2.timeStamp().compareTo(
                meal1.timeStamp()));
        return  mealsOfAType;
    }

    public List<Meal> getMealsByDurability(MealDurability durability){
        List<Meal> mealsOfADurability = new ArrayList<>();
        for (Meal meal : meals) {
            if(meal.type().getDurability() == durability){
                mealsOfADurability.add(meal);
            }
        }
        return mealsOfADurability;
    }
}
