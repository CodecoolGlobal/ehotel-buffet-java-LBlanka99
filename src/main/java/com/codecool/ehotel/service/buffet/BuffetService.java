package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalTime;
import java.util.Map;

public interface BuffetService {
    void refill(Buffet buffet, Map<MealType, Integer> types, LocalTime timeStamp);

    boolean consumeFreshest(Buffet buffet, MealType type);

    int collectWaste(Buffet buffet, MealDurability durability, LocalTime time);
}
