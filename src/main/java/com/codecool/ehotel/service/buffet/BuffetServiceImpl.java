package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class BuffetServiceImpl implements BuffetService {
    @Override
    public void refill(Buffet buffet, Map<MealType, Integer> types, LocalTime timeStamp) {
        for (Map.Entry<MealType, Integer> entry : types.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                buffet.addMeal(new Meal(entry.getKey(), timeStamp));
            }
        }
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType type) {
        return buffet.removeMeal(type);
    }

    @Override
    public int collectWaste(Buffet buffet, MealDurability durability, LocalTime time) {
        List<Meal> mealsOfADurability = buffet.getMealsByDurability(durability);
        int counter = 0;
        for (Meal meal : mealsOfADurability) {
            if (meal.wasCreatedBefore(time)) {
                counter += meal.type().getCost();
                buffet.meals().remove(meal);
            }
        }
        return counter;
    }

    public int collectWasteWithoutTime(Buffet buffet, MealDurability durability) {
        List<Meal> mealsOfADurability = buffet.getMealsByDurability(durability);
        int counter = 0;
        for (Meal meal : mealsOfADurability) {
            counter += meal.type().getCost();
            buffet.meals().remove(meal);
        }
        return counter;
    }
}
