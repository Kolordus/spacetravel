package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Flight {

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
    @ManyToMany
    private Set<Tourist> tourists;


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
