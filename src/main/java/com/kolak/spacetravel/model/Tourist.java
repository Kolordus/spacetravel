package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tourists")
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name cannot be null!")
    private String name;
    @NotNull(message = "Surname cannot be null!")
    private String surname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String country;
    @NotNull
    private LocalDate birthDate;

    @JsonIgnore
    @ManyToMany
    private Set<Flight> flights = new HashSet<>();

    public Tourist() {
    }

    public Tourist(String name, String surname, Sex sex, String country, LocalDate birthDate, Set<Flight> flights) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.country = country;
        this.birthDate = birthDate;
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "Tourist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", country='" + country + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public enum Sex {
        MALE, FEMALE
    }
}


