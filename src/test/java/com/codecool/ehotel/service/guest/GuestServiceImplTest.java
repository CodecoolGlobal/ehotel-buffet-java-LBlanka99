package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class GuestServiceImplTest {

    @org.junit.jupiter.api.Test
    void generateRandomGuest() {
        GuestService guestService = new GuestServiceImpl(35, LocalDate.parse("2023-06-07"), LocalDate.parse("2023-07-02"));
        System.out.print(guestService.getAllGuests());
    }

    @Test
    void getGuests() {
        GuestService guestService = new GuestServiceImpl(5, LocalDate.parse("2023-06-07"), LocalDate.parse("2023-07-02"));
        System.out.print(guestService.getGuestsForDay(LocalDate.parse("2023-06-15")));
    }

    @Test
    void dividingToGroups() {
        GuestService guestService = new GuestServiceImpl(25, LocalDate.parse("2023-06-17"), LocalDate.parse("2023-06-21"));
        List<List<Guest>> list = guestService.dividingToGroups(8, LocalDate.parse("2023-06-20"));
        for (List<Guest> element : list) {
            System.out.println(element);
        }
    }
}