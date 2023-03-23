package com.codecool.ehotel.service.mealtimes;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BreakfastManager {
    private final GuestService guestService;
    private final int groupCount;
    private final List<LocalTime> timeTable;
    private final BuffetService buffetService = new BuffetServiceImpl();
    private final Random random = new Random();

    public BreakfastManager(GuestService guestService, int groupCount, List<LocalTime> timeTable) {
        this.guestService = guestService;
        this.groupCount = groupCount;
        this.timeTable = timeTable;
    }

    public void serve(LocalDate date, Buffet buffet) {
        int unhappyGuests = 0;
        int wasteCost = 0;
        List<List<Guest>> guests = guestService.dividingToGroups(groupCount, date);
        for (int i = 0; i < groupCount; i++) {
            int remainingBusiness = 0;
            int remainingKids = 0;
            int remainingTourists = 0;
            for (int j=i; j < groupCount; j++){
                for (Guest guest : guests.get(j)) {
                    if(guest.guestType() == GuestType.BUSINESS){
                        remainingBusiness++;
                    } else if(guest.guestType() == GuestType.TOURIST){
                        remainingTourists++;
                    } else {
                        remainingKids++;
                    }
                }
            }
            Map<MealType, Integer> typesToAdd = getOptimalPortions(buffet, remainingBusiness,  remainingKids, remainingTourists, groupCount - i);
            buffetService.refill(buffet, typesToAdd, timeTable.get(i));
            for (Guest guest : guests.get(i)) {
                int index = random.nextInt(guest.guestType().getMealPreferences().size());
                MealType mealType = guest.guestType().getMealPreferences().get(index);
                if (!(buffetService.consumeFreshest(buffet, mealType))) {
                    unhappyGuests++;
                    System.out.println("Guest couldn't eat: " + mealType);
                }
            }
            if (i >= 2) {
                wasteCost += buffetService.collectWaste(buffet, MealDurability.SHORT, timeTable.get(i - 2).plusMinutes(1));

            }
        }
        wasteCost += buffetService.collectWasteWithoutTime(buffet, MealDurability.SHORT);
        wasteCost += buffetService.collectWasteWithoutTime(buffet, MealDurability.MEDIUM);

        System.out.println("\nThe amount of unhappy guests: " + unhappyGuests);
        System.out.println("All guests for the day: " + guestService.getGuestsForDay(date).size());
        System.out.println("The cost of wasted food: " + wasteCost + " coins");
    }

    public Map<MealType, Integer> getOptimalPortions(Buffet buffet, int remainingBusiness, int remainingKids, int remainingTourists, int remainingCycles){
        Map<MealType, Integer> result = new HashMap<>();


        System.out.println("\nremain cycle: " + remainingCycles);

        //1st round, put longs AND short
        if(remainingCycles == groupCount){
            result.put(MealType.CEREAL, remainingKids);
            result.put(MealType.MILK, remainingKids);

            int pancakeAdd = (int) Math.round((remainingKids / 4.0d) * (3.0d / groupCount));
            int croissantAdd = (int) Math.round(remainingBusiness / 3.0d * (3.0d / groupCount));
            int friedBaconAdd = (int) Math.round(remainingBusiness / 3.0d * (2.0d / groupCount));
            int friedSausageAdd = (int) Math.round(remainingTourists / 5.0d * (2.0d/ groupCount));
            int sunnySideUpAdd  = (int) Math.round(remainingTourists / 5.0d * (2.0d/ groupCount));
            int scrambledEggsAdd = (int) Math.round(remainingBusiness / 3.0d * (2.0d / groupCount));
            result.put(MealType.PANCAKE, pancakeAdd);
            result.put(MealType.CROISSANT, croissantAdd);
            result.put(MealType.FRIED_BACON, friedBaconAdd);
            result.put(MealType.FRIED_SAUSAGE, friedSausageAdd);
            result.put(MealType.SUNNY_SIDE_UP, sunnySideUpAdd);
            result.put(MealType.SCRAMBLED_EGGS, scrambledEggsAdd);
        } else {
            int pancakeAdd = (int)Math.ceil(remainingKids / 4.0d * (1.0d / groupCount));
            int croissantAdd = (int)Math.ceil(remainingBusiness / 3.0d * (1.0d / groupCount));

            int friedBaconAdd = (int)Math.ceil(remainingBusiness / 3.0d * (1.0d / groupCount));
            int friedSausageAdd = (int)Math.ceil(remainingTourists / 5.0d * (1.0d/ groupCount));
            int sunnySideUpAdd  = (int)Math.ceil(remainingTourists / 5.0d * (1.0d/ groupCount));
            int scrambledEggsAdd = (int)Math.ceil(remainingBusiness / 3.0d * (1.0d / groupCount));
            result.put(MealType.PANCAKE, pancakeAdd);
            result.put(MealType.CROISSANT, croissantAdd);
            result.put(MealType.FRIED_BACON, friedBaconAdd);
            result.put(MealType.FRIED_SAUSAGE, friedSausageAdd);
            result.put(MealType.SUNNY_SIDE_UP, sunnySideUpAdd);
            result.put(MealType.SCRAMBLED_EGGS, scrambledEggsAdd);
        }
        //not last 2 rounds, put mediums
        if (remainingCycles > 2){
            List<MealType> mediumDurability = List.of(MealType.BUN, MealType.MASHED_POTATO, MealType.MUFFIN);
            for (MealType mealtype : mediumDurability) {
                int amount = buffet.getMealsByType(mealtype).size();
                int amountAdd;
                if(mealtype == MealType.MUFFIN){
                    amountAdd = Math.max((Math.round((float) remainingKids / 4)) + (Math.round((float) remainingTourists / 5) - amount), 0);
                } else {
                    amountAdd = (int)Math.max((Math.floor((double) remainingTourists / 5) - amount), 0);
                }

                result.put(mealtype, amountAdd);
            }
        }
        //

        System.out.println("Foods added: " + result);
        return result;
    }
}
