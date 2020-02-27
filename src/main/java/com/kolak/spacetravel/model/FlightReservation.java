package com.kolak.spacetravel.model;

import javax.persistence.*;

@Entity
@Table(name = "flight_reservations")
public class FlightReservation {

    /*
        class for tourist-flight mapping entities

     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Flight flight;

    @OneToOne
    private Tourist tourist;


    public FlightReservation() {
    }

    public FlightReservation(Flight flight, Tourist tourist) {
        this.flight = flight;
        this.tourist = tourist;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    @Override
    public String toString() {
        return "FlightReservations{" +
                "id=" + id +
                '}';
    }
}
