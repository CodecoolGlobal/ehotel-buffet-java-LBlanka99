package com.codecool.ehotel.service.guest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class GuestServiceImplTest {

    @org.junit.jupiter.api.Test
    void generateRandomGuest() {
        GuestService guestService = new GuestServiceImpl();
        LocalDate start = LocalDate.parse("2023-06-07");
        LocalDate end = LocalDate.parse("2023-07-02");
        System.out.print(guestService.generateRandomGuest(start, end));
    }
}