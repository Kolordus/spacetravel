package com.kolak.spacetravel.repo;

import com.kolak.spacetravel.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristRepo extends JpaRepository<Tourist, Long> {
}
