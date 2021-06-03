package com.kolak.spacetravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tourists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tourist {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String country;
    private LocalDate birthDate;

    @JsonIgnore
    @Transient
    private Set<Flight> flights;


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


