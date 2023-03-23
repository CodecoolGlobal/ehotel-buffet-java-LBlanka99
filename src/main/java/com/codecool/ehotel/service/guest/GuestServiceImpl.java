package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class GuestServiceImpl implements GuestService {
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
        long amount = random.nextLong(seasonLength);


        LocalDate checkIn = seasonStart.plus(amount, ChronoUnit.DAYS);

        long maxStayLength = checkIn.until(seasonEnd, ChronoUnit.DAYS);
        long randStayLength = random.nextLong(maxStayLength+1);
        long actualStayLength = randStayLength < 1 ? 1 : randStayLength;
        LocalDate checkOut = checkIn.plus(actualStayLength, ChronoUnit.DAYS);

        return new Guest(name, GuestType.getRandomGuestType(), checkIn, checkOut);
    }

    @Override
    public List<Guest> getGuestsForDay(LocalDate today) {
        List<Guest> guestsForDay = new ArrayList<>();
        for (Guest guest : allGuests) {
            if (isBetween(guest.checkIn(), guest.checkOut(), today)) {
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

    @Override
    public List<List<Guest>> dividingToGroups(int groupCount, LocalDate date) {
        List<Guest> guests = getGuestsForDay(date);
        List<List<Guest>> dividedGuests = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            dividedGuests.add(new ArrayList<>());
        }
        for (Guest guest : guests) {
            Random random = new Random();
            int index = random.nextInt(groupCount);
            dividedGuests.get(index).add(guest);
        }
        return dividedGuests;
    }
}
