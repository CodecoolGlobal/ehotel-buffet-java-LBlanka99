package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;


import java.time.LocalDate;
import java.util.List;


public interface GuestService {

    Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd);

    List<Guest> getGuestsForDay(LocalDate date);
    boolean isBetween(LocalDate start, LocalDate end, LocalDate date);
    List<Guest> getAllGuests();
    List<List<Guest>> dividingToGroups(int groupCount, LocalDate date);

}
