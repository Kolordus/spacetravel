package com.kolak.spacetravel.manager;


import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.FlightReservation;
import com.kolak.spacetravel.model.Tourist;
import com.kolak.spacetravel.repo.FlightRepo;
import com.kolak.spacetravel.repo.FlightReservationRepo;
import com.kolak.spacetravel.repo.TouristRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class Controller {


    /*
        Service class which provides connection with database.
        Whis class has connections to 3 repositories and by them
        it controlls all data flow between server and DB

     */

    private TouristRepo touristRepo;
    private FlightRepo flightRepo;
    private FlightReservationRepo flightReservationRepo;

    @Autowired
    public Controller(TouristRepo touristRepo, FlightRepo flightRepo, FlightReservationRepo flightReservationRepo) {
        this.touristRepo = touristRepo;
        this.flightRepo = flightRepo;
        this.flightReservationRepo = flightReservationRepo;
    }


    // GET
    public List<Tourist> getAllTourists() {
        return touristRepo.findAll();
    }

    public List<Flight> getAllFlights (){
        return flightRepo.findAll();
    }

    public List<FlightReservation> findAllUserReservations() {
        return flightReservationRepo.findAllUserReservations();
    }

    public Flight getFlightById (Long id){
        return flightRepo.findById(id).get();
    }

    public Tourist getTouristById (Long id){
        return touristRepo.findById(id).get();
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

    public FlightReservation findByFlightIdAndAndTouristId(Long flightId, Long touristId){
        return flightReservationRepo.findByFlight_IdAndAndTourist_Id(flightId, touristId);
    }


    public List<FlightReservation> getReservationsByFlightId(Long id) {
        return (List<FlightReservation>)flightReservationRepo.findAllByFlightId(id);
    }

    public List<FlightReservation> getReservationsByTouristId(Long id) {
        return flightReservationRepo.findAllByTouristId(id);
    }

    //POST

    public void addReservation(FlightReservation flightReservation) {
        flightReservationRepo.save(flightReservation);
    }


    public void addTourist(Tourist tourist) {
        touristRepo.save(tourist);
    }

    public void addFlight(Flight flight) {
        flightRepo.save(flight);
    }

    public void deleteTourist(Long id) {
        touristRepo.deleteById(id);
    }

    public void deleteFlight(Long id) {
        flightRepo.deleteById(id);
    }



    // PUT

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





    // DELETE

    public void deleteReservation(Long id) {
        flightReservationRepo.deleteById(id);
    }

    public void deleteAllReservationsByFlightId(Long flightId) {
        flightReservationRepo.deleteAllByFlightId(flightId);
    }

    public void deleteAllReservationsByTouristId(Long touristId) {
        flightReservationRepo.deleteAllByTouristId(touristId);
    }


}
