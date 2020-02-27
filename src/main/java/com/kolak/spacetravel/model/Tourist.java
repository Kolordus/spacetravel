package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tourists")
public class Tourist {

    /*
        model class of tourist
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String country;
//    private Set<String> notes;
    private LocalDate birthDate;

    @JsonIgnore
    @Transient
    private Set<Flight> flights;

    public Tourist() {
    }

    public Tourist(String name, String surname, Sex sex, String country, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.country = country;
        this.birthDate = birthDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tourist tourist = (Tourist) o;
        return Objects.equals(id, tourist.id) &&
                Objects.equals(name, tourist.name) &&
                Objects.equals(surname, tourist.surname) &&
                sex == tourist.sex &&
                Objects.equals(country, tourist.country) &&
                Objects.equals(birthDate, tourist.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, sex, country, birthDate) * 31;
    }
}
