package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.mealtimes.BreakfastManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services
        GuestService guestService = new GuestServiceImpl(100, LocalDate.parse("2023-06-15"), LocalDate.parse("2023-06-17"));
        List<LocalTime> timeTable = getTimeTable();
        BreakfastManager breakfastManager = new BreakfastManager(guestService, 8, timeTable);
        Buffet buffet = new Buffet(new ArrayList<>());


        // Generate guests for the season
        List<Guest> guests = guestService.getAllGuests();

        // Run breakfast buffet
        Map<MealType, Integer> types = new HashMap<>();
        types.put(MealType.CEREAL, 1);
        types.put(MealType.MILK, 1);
        types.put(MealType.CROISSANT, 1);
        types.put(MealType.MUFFIN, 1);
        types.put(MealType.BUN, 1);
        types.put(MealType.SUNNY_SIDE_UP, 1);
        types.put(MealType.PANCAKE, 1);
        breakfastManager.serve(LocalDate.parse("2023-06-16"), buffet, types);


    }

    private static List<LocalTime> getTimeTable() {
        return List.of(LocalTime.of(6, 0), LocalTime.of(6, 30), LocalTime.of(7, 0), LocalTime.of(7, 30), LocalTime.of(8, 0), LocalTime.of(8, 30), LocalTime.of(9, 0), LocalTime.of(9, 30));
    }
}
