package com.kolak.spacetravel;


import com.kolak.spacetravel.service.TouristService;
import com.kolak.spacetravel.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class start {

    private final TouristService touristService;

    @Autowired
    public start(TouristService touristService) {
        this.touristService = touristService;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void method() {
        System.out.println(touristService.getAllTourists());
    }

}
