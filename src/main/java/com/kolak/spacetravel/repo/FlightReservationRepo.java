package com.kolak.spacetravel.repo;

import com.kolak.spacetravel.model.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FlightReservationRepo extends JpaRepository<FlightReservation, Long> {


    @Query(value = "SELECT DISTINCT * FROM flight_reservations WHERE flight_id = :flightId", nativeQuery = true)
    List<FlightReservation> findAllByFlightId(@Param("flightId") Long flightId);

    @Query(value = "SELECT DISTINCT * FROM flight_reservations WHERE tourist_id = :touristId", nativeQuery = true)
    List<FlightReservation> findAllByTouristId(@Param("touristId") Long touristId);

    @Query(value = "SELECT DISTINCT * FROM flight_reservations WHERE flight_id = :flightId and tourist_id = :touristId", nativeQuery = true)
    FlightReservation findByFlight_IdAndAndTourist_Id(@Param("flightId") Long flightId,
                                                      @Param("touristId") Long touristId);


    @Query(value = "SELECT * FROM flight_reservations WHERE tourist_id = 1", nativeQuery = true)
    List<FlightReservation> findAllUserReservations();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM flight_reservations WHERE flight_id = :flightId", nativeQuery = true)
    void deleteAllByFlightId(@Param("flightId") Long flightId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM flight_reservations WHERE tourist_id = :flightId", nativeQuery = true)
    void deleteAllByTouristId(@Param("flightId") Long flightId);
}
