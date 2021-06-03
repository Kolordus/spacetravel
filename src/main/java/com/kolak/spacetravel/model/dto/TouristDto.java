package com.kolak.spacetravel.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
@EqualsAndHashCode
public final class TouristDto {

    private final String name;
    private final String surname;


    private TouristDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static TouristDto createTouristDto(String name, String surname) {
        if (StringUtils.isNoneBlank(name, surname)) {
            return new TouristDto(name, surname);
        }
        throw new RuntimeException("Wrong values passed!");
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
