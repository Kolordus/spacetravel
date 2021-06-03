package com.kolak.spacetravel.contoller;


import com.kolak.spacetravel.service.TouristService;
import com.kolak.spacetravel.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController()
@RequestMapping("/api")
@CrossOrigin("*")
public class TouristController {

    private final TouristService touristService;
    private final Logger log = Logger.getLogger(TouristController.class.getName());

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }


    @GetMapping("/all-tourists")
    public ResponseEntity<List<Tourist>> getAllTourists() {
        return new ResponseEntity<>(touristService.getAllTourists(), HttpStatus.OK);
    }

    @GetMapping("/tourist/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        return ResponseEntity.ok(touristService.getTouristById(id));
    }

    @GetMapping("/buyFlight")
    public ResponseEntity<?> assignFlightToTourist(@RequestParam Long touristId,
                                                   @RequestParam Long flightId) {
        touristService.assignFlightToTourist(touristId, flightId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-tourist")
    public ResponseEntity<?> addTourist(@RequestBody Tourist tourist) {
        touristService.addTourist(tourist);
        log.info("Tourist created: " + tourist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-tourist/{id}")
    public ResponseEntity deleteTourist(@PathVariable Long id) {
        touristService.deleteTourist(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @PutMapping("/update-tourist/{id}")
    public ResponseEntity updateTourist(@PathVariable Long id,
                                        @RequestBody Tourist newTourist) {
        touristService.updateTourist(newTourist, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
