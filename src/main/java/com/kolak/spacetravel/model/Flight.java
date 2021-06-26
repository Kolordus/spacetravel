package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Timestamp departureDate;
    @NotNull
    private Timestamp arrivalDate;
    @NotNull
    private int seatsAmount;
    private int touristAmount;
    @NotNull
    private float ticketPrice;

    @ManyToMany(mappedBy = "flights")
    private Set<Tourist> tourists = new HashSet<>();

    public Flight() {
    }

    public Flight(Timestamp departureDate, Timestamp arrivalDate, int seatsAmount, int touristAmount, float ticketPrice, Set<Tourist> tourists) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.seatsAmount = seatsAmount;
        this.touristAmount = touristAmount;
        this.ticketPrice = ticketPrice;
        this.tourists = tourists;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
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

    public Set<Tourist> getTourists() {
        return tourists;
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

    public void setTourists(Set<Tourist> tourists) {
        this.tourists = tourists;
    }

    public static final class Builder {
        private Timestamp departureDate;
        private Timestamp arrivalDate;
        private int seatsAmount;
        private int touristAmount;
        private float ticketPrice;
        private Set<Tourist> tourists = new HashSet<>();

        public Builder departureDate(Timestamp departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder arrivalDate(Timestamp arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder seatsAmount(int seatsAmount) {
            this.seatsAmount = seatsAmount;
            return this;
        }

        public Builder touristAmount(int touristAmount) {
            this.touristAmount = touristAmount;
            return this;
        }

        public Builder ticketPrice(float ticketPrice) {
            this.ticketPrice = ticketPrice;
            return this;
        }

        public Builder tourists(Set<Tourist> tourists) {
            this.tourists = tourists;
            return this;
        }

        public Flight build() {
            Flight flight = new Flight();
            flight.departureDate = this.departureDate;
            flight.arrivalDate = this.arrivalDate;
            flight.seatsAmount = this.seatsAmount;
            flight.touristAmount = this.touristAmount;
            flight.ticketPrice = this.ticketPrice;
            flight.tourists = this.tourists;

            return flight;
        }
    }
}
