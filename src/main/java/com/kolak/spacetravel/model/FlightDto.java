package com.kolak.spacetravel.model;

import java.sql.Timestamp;
import java.util.Objects;

public class FlightDto {
    /*

    model class for frontend purpouse
     */

    private Long id;
    private float price;
    private Timestamp departureTime;


    public FlightDto() {
    }


    public FlightDto(Long id,float price, Timestamp departureTime) {
        this.id = id;
        this.price = price;
        this.departureTime = departureTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " " + price + " " + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return Float.compare(flightDto.price, price) == 0 &&
                Objects.equals(id, flightDto.id) &&
                Objects.equals(departureTime, flightDto.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, departureTime) * 31;
    }
}
