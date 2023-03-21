package com.codecool.ehotel.service.guest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
}