package com.kolak.spacetravel.contoller;

import com.kolak.spacetravel.service.FlightService;
import com.kolak.spacetravel.model.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/all-flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(flightService.getAllFlights(),
                HttpStatus.OK);
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity(flightService.getFlightById(id),
                HttpStatus.OK);
    }


    @GetMapping("/flights/date")
    public ResponseEntity<List<Flight>> getFlightAfterDate(@RequestParam Timestamp departTime) {
        return new ResponseEntity<>(flightService.getFlightAfterDate(departTime),
                HttpStatus.OK);
    }

    @GetMapping("/flights/price")
    public ResponseEntity<List<Flight>> getFlightBetweenTicketPrices(@RequestParam float lowPrice,
                                                                     @RequestParam float highPrice) {
        return new ResponseEntity<>(flightService.getFlightBetweenTicketPrices(lowPrice, highPrice),
                HttpStatus.OK);
    }

    @GetMapping("/flights/date-price")
    public ResponseEntity<List<Flight>> getFlightAfterDateAndBetweenPrices(
            @RequestParam Timestamp departTime,
            @RequestParam float lowPrice,
            @RequestParam float highPrice) {
        return new ResponseEntity<>(flightService.getFlightAfterDateAndBetweenTicketPrices(departTime, lowPrice, highPrice),
                HttpStatus.OK);
    }

    @PostMapping("/add-flight")
    public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
        flightService.addFlight(flight);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-flight/{id}")
    public ResponseEntity updateFlight(@PathVariable Long id,
                                       @RequestBody Flight newFlight) {
        flightService.updateFlight(newFlight, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
