package com.kolak.spacetravel.service;

import com.kolak.spacetravel.excpetion.NoSuchElementException;
import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.Tourist;
import com.kolak.spacetravel.repo.FlightRepo;
import com.kolak.spacetravel.repo.TouristRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TouristService {

    private final TouristRepo touristRepo;
    private final FlightRepo flightRepo;

    public TouristService(TouristRepo touristRepo, FlightRepo flightRepo) {
        this.touristRepo = touristRepo;
        this.flightRepo = flightRepo;
    }

    public List<Tourist> getAllTourists() {
        return touristRepo.findAll();
    }

    public Tourist getTouristById(Long id) {
        return touristRepo.findById(id).get();
    }

    public void addTourist(Tourist tourist) {
        touristRepo.save(tourist);
    }

    public void deleteTourist(Long id) {
        touristRepo.deleteById(id);
    }

    @Modifying
    public void updateTourist(Tourist newTourist, Long id) {
        touristRepo.findById(id)
                .map(tourist -> {
                    tourist.getId();
                    tourist.setName(newTourist.getName());
                    tourist.setSurname(newTourist.getSurname());
                    tourist.setSex(newTourist.getSex());
                    tourist.setBirthDate(newTourist.getBirthDate());
                    tourist.setCountry(newTourist.getCountry());
                    tourist.setFlights(newTourist.getFlights());

                    return touristRepo.save(tourist);
                })
                .orElseGet(() -> {
                    return touristRepo.save(newTourist);
                });
    }


    public void assignFlightToTourist(Long touristId, Long flightId) {
        Optional<Flight> attachedFlight = flightRepo.findById(flightId);
        if (!attachedFlight.isPresent()) {
            throw new NoSuchElementException("No such flight!!");
        }

        Optional<Tourist> attachedTourist = touristRepo.findById(touristId);
        if (!attachedTourist.isPresent()) {
            throw new NoSuchElementException("No such toruist");
        }

        Flight flight = attachedFlight.get();
        Tourist tourist = attachedTourist.get();

        tourist.getFlights().add(flight);
        flight.getTourists().add(tourist);

        touristRepo.save(tourist);
        flightRepo.save(flight);
    }
}
