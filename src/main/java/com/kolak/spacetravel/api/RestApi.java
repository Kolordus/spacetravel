package com.kolak.spacetravel.api;


import com.kolak.spacetravel.manager.Controller;
import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.FlightReservation;
import com.kolak.spacetravel.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApi {

    /*
        Rest controller for mapping HTTP request
        this class has injected class of controller
        which provide communicaton with database
     */

    private Controller controller;

    @Autowired
    public RestApi(Controller controller) {
        this.controller = controller;
    }

    //CRUD

    // GET MAPPINGS


    @GetMapping("/all-tourists")
    public ResponseEntity<List<Tourist>> getAllTourists() {

        return new ResponseEntity<>(controller.getAllTourists()
                ,HttpStatus.OK);
    }

    @GetMapping("/all-flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(controller.getAllFlights(),
                HttpStatus.OK);
    }

    @GetMapping("/all-user-reservations")
    public ResponseEntity<List<FlightReservation>> getUserReservations() {
        return new ResponseEntity<>(controller.findAllUserReservations(),
                HttpStatus.OK);
    }


    @GetMapping("/tourist/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        return new ResponseEntity(controller.getTouristById(id),
                HttpStatus.OK);
    }


    @GetMapping("/flight/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity(controller.getFlightById(id),
                HttpStatus.OK);
    }

    @GetMapping("/flights/date")
    public ResponseEntity<List<Flight>> getFlightAfterDate(@RequestParam Timestamp departTime) {
        return new ResponseEntity<>(controller.getFlightAfterDate(departTime),
                HttpStatus.OK);
    }

    @GetMapping("/flights/price")
    public ResponseEntity<List<Flight>> getFlightBetweenTicketPrices(@RequestParam float lowPrice,
                                                                     @RequestParam float highPrice) {
        return new ResponseEntity<>(controller.getFlightBetweenTicketPrices(lowPrice, highPrice),
                HttpStatus.OK);
    }

    @GetMapping("/flights/date-price")
    public ResponseEntity<List<Flight>> getFlightAfterDateAndBetweenPrices(
            @RequestParam Timestamp departTime,
            @RequestParam float lowPrice,
            @RequestParam float highPrice) {
        return new ResponseEntity<>(controller.getFlightAfterDateAndBetweenTicketPrices(departTime, lowPrice, highPrice),
                HttpStatus.OK);
    }

    @GetMapping("/resevations/flight/{id}")
    public ResponseEntity<List<FlightReservation>> getReservationsByFlightId(@PathVariable Long id) {
        return new ResponseEntity<>(controller.getReservationsByFlightId(id),
                HttpStatus.OK);
    }

    @GetMapping("/resevations/tourist/{id}")
    public ResponseEntity<List<FlightReservation>> getReservationsByTouristId(@PathVariable Long id) {
        return new ResponseEntity<>(controller.getReservationsByTouristId(id),
                HttpStatus.OK);
    }

    @GetMapping("/resevations/flight-tourist/{id}")
    public ResponseEntity<FlightReservation> getByFlightIdAndAndTouristId(@PathVariable Long flightId,
                                                                           @RequestParam Long touristId) {
        return new ResponseEntity<>(controller.findByFlightIdAndAndTouristId(flightId, touristId),
                HttpStatus.OK);
    }



    // POST MAPPINGS



    @PostMapping("/add-tourist")
    public ResponseEntity addTourist(@RequestBody Tourist tourist) {
        controller.addTourist(tourist);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/add-flight")
    public ResponseEntity addFlight(@RequestBody Flight flight) {
        controller.addFlight(flight);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PostMapping("/add-reservation")
    public ResponseEntity addReservation(@RequestBody FlightReservation flightReservation) {
        controller.addReservation(flightReservation);
        return new ResponseEntity(HttpStatus.CREATED);
    }




    // DELETE MAPPINGS


    @DeleteMapping("/delete-tourist/{id}")
    public ResponseEntity deleteTourist(@PathVariable Long id) {
        controller.deleteTourist(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity deleteFlight(@PathVariable Long id) {
        controller.deleteFlight(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/delete-reservation/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        controller.deleteReservation(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-flight-reservations/{id}")
    public ResponseEntity deleteAllReservationsByFlightId(@PathVariable Long id) {
        controller.deleteAllReservationsByFlightId(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-tourist-reservations/{id}")
    public ResponseEntity deleteAllReservationsByTouristId(@PathVariable Long id) {
        controller.deleteAllReservationsByTouristId(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }



    //PUT MAPPINGS

    @PutMapping("/update-tourist/{id}")
    public ResponseEntity updateTourist(@PathVariable Long id,
                                        @RequestBody Tourist newTourist)
    {
        controller.updateTourist(newTourist, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-flight/{id}")
    public ResponseEntity updateFlight(@PathVariable Long id,
                                       @RequestBody Flight newFlight)
    {
        controller.updateFlight(newFlight, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
