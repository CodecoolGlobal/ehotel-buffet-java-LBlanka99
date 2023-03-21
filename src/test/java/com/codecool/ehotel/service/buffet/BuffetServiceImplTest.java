package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BuffetServiceImplTest {

    @Test
    void refill() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(9,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(8,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(7,30)));
        meals.add(new Meal(MealType.CROISSANT, LocalTime.of(10,30)));

        Buffet buffet = new Buffet(meals);

        BuffetServiceImpl buffetService = new BuffetServiceImpl();

        Map<MealType, Integer> types = new HashMap<>();
        types.put(MealType.MUFFIN, 3);
        types.put(MealType.CROISSANT, 4);

        buffetService.refill(buffet, types, LocalTime.of(8,45));
        System.out.println(buffet.meals());
    }

    @Test
    void collectWaste() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(9,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(8,30)));
        meals.add(new Meal(MealType.CEREAL, LocalTime.of(7,30)));
        meals.add(new Meal(MealType.CROISSANT, LocalTime.of(10,30)));

        Buffet buffet = new Buffet(meals);

        BuffetServiceImpl buffetService = new BuffetServiceImpl();

        System.out.println(buffetService.collectWaste(buffet, MealDurability.LONG, LocalTime.of(9,0)));
        System.out.println(buffet.meals());
    }
}