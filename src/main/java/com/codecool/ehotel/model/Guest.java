package com.codecool.ehotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Guest(String name, GuestType guestType, LocalDate checkIn, LocalDate checkOut) {

}
