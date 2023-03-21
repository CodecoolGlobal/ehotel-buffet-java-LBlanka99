package com.codecool.ehotel.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


class BuffetTest {

    @Test
    void getMealsByType() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(8,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(9,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(7,30)));
        meals.add(new Meal(MealType.CROISSANT, LocalTime.of(10,30)));
        Buffet buffet = new Buffet(meals);
        List<Meal> mealsOfAType = buffet.getMealsByType(MealType.CEREAL);
        for (Meal meal : mealsOfAType) {
            System.out.println(meal);
        }
        /*buffet.removeMeal(MealType.CEREAL);
        System.out.println(buffet.meals());*/

    }
}