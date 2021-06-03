package com.kolak.spacetravel.service;


import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.repo.FlightRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FlightService {

    private FlightRepo flightRepo;

    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepo.findById(id).get();
    }

    public List<Flight> getFlightAfterDate(Timestamp departTime) {
        return flightRepo.findAllAndDepartureDate(departTime);
    }

    public List<Flight> getFlightBetweenTicketPrices(float lowPrice, float highPrice) {
        return flightRepo.findAllByTicketPriceBetween(lowPrice, highPrice);
    }

    public List<Flight> getFlightAfterDateAndBetweenTicketPrices(Timestamp departTime, float lowPrice, float highPrice) {
        return flightRepo.findAllByDepartureDateAndTicketPriceBetween(departTime, lowPrice, highPrice);
    }

    public void addFlight(Flight flight) {
        flightRepo.save(flight);
    }


    public void deleteFlight(Long id) {
        flightRepo.deleteById(id);
    }

    @Modifying
    public void updateFlight(Flight newFlight, Long id) {
        flightRepo.findById(id)
                .map(flight -> {
                    flight.getId();
                    flight.setDepartureDate(newFlight.getDepartureDate());
                    flight.setArrivalDate(newFlight.getArrivalDate());
                    flight.setTouristAmount(newFlight.getTouristAmount());
                    flight.setTicketPrice(newFlight.getTicketPrice());
                    flight.setSeatsAmount(newFlight.getSeatsAmount());
                    flight.setTourists(newFlight.getTourists());

                    return flightRepo.save(flight);
                })
                .orElseGet(() -> {
                    return flightRepo.save(newFlight);
                });
    }

}
