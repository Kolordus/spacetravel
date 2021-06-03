package com.kolak.spacetravel.repo;

import com.kolak.spacetravel.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepo extends JpaRepository<Flight, Long> {


    @Query(value = "SELECT * from flights where DEPARTURE_DATE > :departTime" , nativeQuery = true)
    List<Flight> findAllAndDepartureDate(@Param("departTime") Timestamp departTime);

    @Query(value = "SELECT * FROM flights where TICKET_PRICE BETWEEN :lowPrice and :highPrice", nativeQuery = true)
    List<Flight> findAllByTicketPriceBetween(@Param("lowPrice") float lowPrice,
                                             @Param("highPrice") float highPrice);

    @Query(value = "SELECT * FROM flights where DEPARTURE_DATE > :departTime AND TICKET_PRICE BETWEEN :lowPrice and :highPrice", nativeQuery = true)
    List<Flight> findAllByDepartureDateAndTicketPriceBetween(@Param("departTime") Timestamp timestamp,
                                                             @Param("lowPrice") float lowPrice,
                                                             @Param("highPrice") float highPrice);
}
