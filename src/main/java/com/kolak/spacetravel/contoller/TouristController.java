package com.kolak.spacetravel.contoller;


import com.kolak.spacetravel.service.TouristService;
import com.kolak.spacetravel.model.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
@CrossOrigin("*")
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }


    @GetMapping("/all-tourists")
    public ResponseEntity<List<Tourist>> getAllTourists() {
        return new ResponseEntity<>(touristService.getAllTourists()
                , HttpStatus.OK);
    }

    @GetMapping("/tourist/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        return ResponseEntity.ok(touristService.getTouristById(id));
    }

    @PostMapping("/add-tourist")
    public ResponseEntity<?> addTourist(@RequestBody Tourist tourist) {
        touristService.addTourist(tourist);
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
