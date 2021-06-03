package com.kolak.spacetravel.model.dto;

import lombok.*;

import java.sql.Timestamp;

@ToString
@EqualsAndHashCode
public final class FlightDto {

    private final float price;
    private final Timestamp departureTime;


    private FlightDto(float price, Timestamp departureTime) {
        this.price = price;
        this.departureTime = departureTime;
    }


    public static FlightDto createDto(float price, Timestamp departureTime) {
        return new FlightDto(price, departureTime);
    }

    public float getPrice() {
        return price;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }
}
