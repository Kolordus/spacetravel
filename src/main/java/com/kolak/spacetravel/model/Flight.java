package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "flights")
public class Flight {

    /*
        model class of flight
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private int seatsAmount;
    private int touristAmount;
    private float ticketPrice;

    @JsonIgnore
    @Transient
    private Set<Tourist> tourists;

    public Flight() {
    }

    public Flight(Timestamp departureDate, Timestamp arrivalDate, int seatsAmount, int touristAmount, float ticketPrice) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.seatsAmount = seatsAmount;
        this.touristAmount = touristAmount;
        this.ticketPrice = ticketPrice;
    }

    public Long getId() {
        return id;
    }

    public Set<Tourist> getTourists() {
        return tourists;
    }

    public void setTourists(Set<Tourist> tourists) {
        this.tourists = tourists;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getSeatsAmount() {
        return seatsAmount;
    }

    public void setSeatsAmount(int seatsAmount) {
        this.seatsAmount = seatsAmount;
    }

    public int getTouristAmount() {
        return touristAmount;
    }

    public void setTouristAmount(int touristAmount) {
        this.touristAmount = touristAmount;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", seatsAmount=" + seatsAmount +
                ", touristAmount=" + touristAmount +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
