package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class GuestServiceImpl implements GuestService{
    private final List<String> possibleNames = new ArrayList<>(List.of(
            "John Wick",
            "Susanne Heart",
            "Joe Smith",
            "Bill Big",
            "Kate Winslet",
            "Timothy Swear"
    ));
    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();
        int nameIndex = random.nextInt(possibleNames.size());
        String name = possibleNames.get(nameIndex);
        long seasonLength = seasonStart.until(seasonEnd, ChronoUnit.DAYS);
        long amount = random.nextLong(seasonLength - 1);
        LocalDate checkIn = seasonStart.plus(amount, ChronoUnit.DAYS);
        long maxStayLength = checkIn.until(seasonEnd, ChronoUnit.DAYS);
        long actualStayLength = random.nextLong(maxStayLength -1) + 1;
        LocalDate checkOut = checkIn.plus(actualStayLength, ChronoUnit.DAYS);
        Guest guest = new Guest(name, GuestType.getRandomGuestType(), checkIn, checkOut);

        return guest;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return null;
    }
}
