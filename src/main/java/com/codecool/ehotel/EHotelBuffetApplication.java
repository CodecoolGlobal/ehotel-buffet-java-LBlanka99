package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.mealtimes.BreakfastManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EHotelBuffetApplication {
    static Scanner scanner = new Scanner(System.in);
    static final int cycleLengthInMinutes = 30;

    public static void main(String[] args) {
        final int amountOfGuests;
        final int amountOfBreakfastCycles;
        final LocalDate seasonStart;
        final LocalDate seasonEnd;
        final LocalTime breakfastStart;


        // Ask for data
        System.out.println("Hello! This is the e-hotel buffet simulator. You should give the data, and the program will simulate the breakfast runs for you!");
        amountOfGuests = askForNumber("How many guests do you have?");
        amountOfBreakfastCycles = askForNumber("How many breakfast cycles do you have? (1 breakfast cycle is 30 minutes)");
        seasonStart = askForDateInput("When does the season start? (You should give the date in yyyy-mm-dd format)");
        seasonEnd = askForDateInput("When does the season end? (You should give the date in yyyy-mm-dd format)");
        breakfastStart = askForTimeInput("When does the breakfast time start? (You should give a time in hh:mm format)");


        // Initialize services
        GuestService guestService = new GuestServiceImpl(amountOfGuests, seasonStart, seasonEnd);
        List<LocalTime> timeTable = getTimeTable(breakfastStart, amountOfBreakfastCycles);
        BreakfastManager breakfastManager = new BreakfastManager(guestService, amountOfBreakfastCycles, timeTable);
        Buffet buffet = new Buffet(new ArrayList<>());


        // Run breakfast buffet
        breakfastManager.run(seasonStart, seasonEnd, buffet);

    }

    private static List<LocalTime> getTimeTable(LocalTime breakfastStart, int amountOfBreakfastCycles) {
        List<LocalTime> timeTable = new ArrayList<>();
        for (int i = 0; i < amountOfBreakfastCycles; i++) {
            timeTable.add(breakfastStart.plusMinutes((long) i * cycleLengthInMinutes));
        }

        return timeTable;
    }

    private static int askForNumber(String question) {
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

    private static LocalDate askForDateInput(String question) {
        LocalDate answerDate = null;
        do {
            System.out.println(question);
            String answer = scanner.nextLine();
            try {
                answerDate = LocalDate.parse(answer);
            } catch (DateTimeParseException e) {
                System.out.println("You should give the date in yyyy-mm-dd format!");
            }
        } while (answerDate == null);

        return answerDate;
    }

    private static LocalTime askForTimeInput(String question) {
        LocalTime answerTime =null;
        do {
            System.out.println(question);
            String answer = scanner.nextLine();
            try {
                List<Integer> answerList = Arrays.stream(answer.split(":")).map(Integer::parseInt).toList();
                answerTime = LocalTime.of(answerList.get(0), answerList.get(1));
            } catch (Exception e) {
                System.out.println("You should give a valid time in hh:mm format!");
            }
        } while (answerTime == null);

        return answerTime;
    }
}
