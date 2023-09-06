package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.mealtimes.BreakfastManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EHotelBuffetApplication {

    public static void main(String[] args) {
        final int amountOfGuests;
        final int amountOfBreakfastCycles;
        final LocalDate seasonStart = LocalDate.parse("2023-06-15");
        final LocalDate seasonEnd = LocalDate.parse("2023-06-16");


        System.out.println("Hello! This is the e-hotel buffet simulator. You should give the data, and the program will simulate the breakfast runs for you!");
        amountOfGuests = askForNumber("How many guests do you have?");
        amountOfBreakfastCycles = askForNumber("How many breakfast cycles do you have? (1 breakfast cycle is 30 minutes)");


        // Initialize services
        GuestService guestService = new GuestServiceImpl(amountOfGuests, seasonStart, seasonEnd);
        List<LocalTime> timeTable = getTimeTable();
        BreakfastManager breakfastManager = new BreakfastManager(guestService, amountOfBreakfastCycles, timeTable);
        Buffet buffet = new Buffet(new ArrayList<>());


        // Generate guests for the season
        //List<Guest> guests = guestService.getAllGuests();

        // Run breakfast buffet
        breakfastManager.run(seasonStart, seasonEnd, buffet);

    }

    private static List<LocalTime> getTimeTable() {
        return List.of(LocalTime.of(6, 0), LocalTime.of(6, 30), LocalTime.of(7, 0), LocalTime.of(7, 30), LocalTime.of(8, 0), LocalTime.of(8, 30), LocalTime.of(9, 0), LocalTime.of(9, 30));
    }

    private static int askForNumber(String question) {
        Scanner scanner = new Scanner(System.in);
        int answerNumber = 0;
        do {
            System.out.println(question);
            String answer = scanner.nextLine();
            try {
                answerNumber = Integer.parseInt(answer);
                if (answerNumber < 1) {
                    System.out.println("You should enter a positive number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        } while (answerNumber < 1);
        return answerNumber;
    }
}
