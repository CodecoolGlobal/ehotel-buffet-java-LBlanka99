package com.codecool.ehotel.model;

import java.util.List;
import java.util.Random;

import static com.codecool.ehotel.model.MealType.*;

public enum GuestType {

    BUSINESS(List.of(SCRAMBLED_EGGS, FRIED_BACON, CROISSANT)),
    TOURIST(List.of(SUNNY_SIDE_UP, FRIED_SAUSAGE, MASHED_POTATO, BUN, MUFFIN)),
    KID(List.of(PANCAKE, MUFFIN, CEREAL, MILK));

    private List<MealType> mealPreferences;

    GuestType(List<MealType> mealPreferences) {
        this.mealPreferences = mealPreferences;
    }

    public List<MealType> getMealPreferences() {
        return mealPreferences;
    }

    public static GuestType getRandomGuestType() {
        Random random = new Random();
        GuestType[] guestTypes = values();
        return guestTypes[random.nextInt(guestTypes.length)];
    }
}
