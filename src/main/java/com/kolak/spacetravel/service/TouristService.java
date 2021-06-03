package com.kolak.spacetravel.service;

import com.kolak.spacetravel.model.Tourist;
import com.kolak.spacetravel.repo.TouristRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TouristService {

    private TouristRepo touristRepo;

    public TouristService(TouristRepo touristRepo) {
        this.touristRepo = touristRepo;
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


}
