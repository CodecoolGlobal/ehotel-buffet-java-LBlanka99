package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.mealtimes.BreakfastManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services
        GuestService guestService = new GuestServiceImpl(100, LocalDate.parse("2023-06-15"), LocalDate.parse("2023-06-17"));
        List<LocalTime> timeTable = getTimeTable();
        BreakfastManager breakfastManager = new BreakfastManager(guestService, 8, timeTable);
        Buffet buffet = new Buffet(new ArrayList<>());


        // Generate guests for the season
        //List<Guest> guests = guestService.getAllGuests();

        // Run breakfast buffet

        breakfastManager.serve(LocalDate.parse("2023-06-16"), buffet);

    }

    private static List<LocalTime> getTimeTable() {
        return List.of(LocalTime.of(6, 0), LocalTime.of(6, 30), LocalTime.of(7, 0), LocalTime.of(7, 30), LocalTime.of(8, 0), LocalTime.of(8, 30), LocalTime.of(9, 0), LocalTime.of(9, 30));
    }
}
