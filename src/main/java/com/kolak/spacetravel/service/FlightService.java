package com.kolak.spacetravel.service;


import com.kolak.spacetravel.excpetion.NoSuchElementException;
import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.Tourist;
import com.kolak.spacetravel.repo.FlightRepo;
import com.kolak.spacetravel.repo.TouristRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class FlightService {

    private final FlightRepo flightRepo;
    private final TouristRepo touristRepo;

    public FlightService(FlightRepo flightRepo, TouristRepo touristRepo) {
        this.flightRepo = flightRepo;
        this.touristRepo = touristRepo;
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No such flight"));
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
        Flight one = flightRepo.getOne(id);
        Set<Tourist> tourists = one.getTourists();

        for (Tourist tourist : tourists) {
            Set<Flight> flights = tourist.getFlights();
            flights.remove(one);
            tourist.setFlights(flights);

            touristRepo.save(tourist);
        }

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
