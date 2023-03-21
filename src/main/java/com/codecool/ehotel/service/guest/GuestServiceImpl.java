package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class GuestServiceImpl implements GuestService{
    private final List<String> possibleNames = new ArrayList<>(List.of(
            "John Wick",
            "Susanne Heart",
            "Joe Smith",
            "Bill Big",
            "Kate Winslet",
            "Timothy Swear"
    ));
    private final List<Guest> allGuests = new ArrayList<>();
    public GuestServiceImpl(int count, LocalDate seasonStart, LocalDate seasonEnd) {
        while (count > 0) {
            allGuests.add(generateRandomGuest(seasonStart, seasonEnd));
            count--;
        }
    }
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

        return new Guest(name, GuestType.getRandomGuestType(), checkIn, checkOut);
    }

    @Override
    public Set<Guest> getGuestsForDay(LocalDate date) {
        Set<Guest> guestsForDay = new HashSet<>();
        for (Guest guest : allGuests) {
            if (isBetween(guest.checkIn(), guest.checkOut(), date)) {
                guestsForDay.add(guest);
            }
        }
        return guestsForDay;
    }

    public boolean isBetween(LocalDate start, LocalDate end, LocalDate date) {
        return (date.isEqual(end) || (date.isAfter(start) && date.isBefore(end)));
    }
    public List<Guest> getAllGuests() {
        return allGuests;
    }
}
