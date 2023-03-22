package com.codecool.ehotel.service.mealtimes;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BreakfastManager {
    private final GuestService guestService;
    private final int groupCount;
    private final List<LocalTime> timeTable;
    private final BuffetService buffetService = new BuffetServiceImpl();

    public BreakfastManager(GuestService guestService, int groupCount, List<LocalTime> timeTable) {
        this.guestService = guestService;
        this.groupCount = groupCount;
        this.timeTable = timeTable;
    }

    public void serve(LocalDate date, Buffet buffet, Map<MealType, Integer> types) {
        int unhappyGuests = 0;
        int wasteCost = 0;
        List<List<Guest>> guests = guestService.dividingToGroups(groupCount, date);
        for (int i = 0; i < groupCount; i++) {
            buffetService.refill(buffet, types, timeTable.get(i));
            System.out.println(guests.get(i));
            for (Guest guest : guests.get(i)) {
                System.out.println("ide");
                Random random = new Random();
                int index = random.nextInt(guest.guestType().getMealPreferences().size());
                MealType mealType = guest.guestType().getMealPreferences().get(index);
                System.out.println(mealType);
                if (!(buffetService.consumeFreshest(buffet, mealType))) {
                    System.out.println("hu");
                    unhappyGuests++;
                }
            }
            if (i >= 2) {
                wasteCost += buffetService.collectWaste(buffet, MealDurability.SHORT, timeTable.get(i - 2).plusMinutes(1));

            }
        }
        wasteCost += buffetService.collectWasteWithoutTime(buffet, MealDurability.SHORT);
        wasteCost += buffetService.collectWasteWithoutTime(buffet, MealDurability.MEDIUM);
        System.out.println("The amount of unhappy guests: " + unhappyGuests);
        System.out.println("The cost of wasted food: " + wasteCost + " coins");
    }
}
