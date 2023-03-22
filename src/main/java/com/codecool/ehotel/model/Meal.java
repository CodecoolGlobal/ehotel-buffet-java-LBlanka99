package com.codecool.ehotel.model;

import java.time.LocalTime;

public record Meal(MealType type,
                   LocalTime timeStamp) {

    public boolean wasCreatedBefore(LocalTime time) {
        return timeStamp().isBefore(time);
    }
}
