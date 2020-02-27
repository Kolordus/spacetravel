// package com.kolak.spacetravel;


// import com.google.common.collect.Lists;
// import com.kolak.spacetravel.api.RestApi;
// import com.kolak.spacetravel.model.Flight;
// import com.kolak.spacetravel.model.FlightReservation;
// import com.kolak.spacetravel.model.Sex;
// import com.kolak.spacetravel.model.Tourist;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;
// import org.springframework.stereotype.Component;

// import java.sql.Timestamp;
// import java.util.Collections;
// import java.util.List;
// import java.util.Random;

// @Component
// public class start {

//     private RestApi restApi;
//     private Random r = new Random();


//     @Autowired
//     public start(RestApi restApi) {
//         this.restApi = restApi;
//     }




//     @EventListener(ApplicationReadyEvent.class)
//     public void fillDatabase() {

//        Tourist user = new Tourist("user", "user", Sex.MALE, "user", Timestamp.valueOf("0000-01-01 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist1 = new Tourist("Andrzej", "Kmicic", Sex.MALE, "Lithuania", Timestamp.valueOf("1596-05-11 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist2 = new Tourist("Aleksandra", "Billewicz", Sex.FEMALE, "Lithuania", Timestamp.valueOf("1600-08-01 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist3 = new Tourist("Jan", "Skrzetuski", Sex.MALE, "Poland", Timestamp.valueOf("1610-10-09 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist4 = new Tourist("Onufry", "Zagłoba", Sex.MALE, "Poland", Timestamp.valueOf("1580-06-18 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist5 = new Tourist("Michał", "Wołodyjowski", Sex.MALE, "Poland", Timestamp.valueOf("1605-01-25 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist6 = new Tourist("Iwan", "Bohun", Sex.MALE, "Ukraine", Timestamp.valueOf("1615-01-01 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist7 = new Tourist("Jeremi", "Wiśniowiecki", Sex.MALE, "Ukraine", Timestamp.valueOf("1595-03-01 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist8 = new Tourist("Jan", "Waza", Sex.MALE, "Sweden", Timestamp.valueOf("1600-01-30 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist9 = new Tourist("Helena", "Skrzetuska", Sex.FEMALE, "Ukraine", Timestamp.valueOf("1620-11-07 00:00:00").toLocalDateTime().toLocalDate());
//        Tourist tourist10 = new Tourist("Andrzej", "Duda", Sex.MALE, "Poland", Timestamp.valueOf("1979-12-27 00:00:00").toLocalDateTime().toLocalDate());

//        restApi.addTourist(user);
//        restApi.addTourist(tourist1);
//        restApi.addTourist(tourist2);
//        restApi.addTourist(tourist3);
//        restApi.addTourist(tourist4);
//        restApi.addTourist(tourist5);
//        restApi.addTourist(tourist6);
//        restApi.addTourist(tourist7);
//        restApi.addTourist(tourist8);
//        restApi.addTourist(tourist9);
//        restApi.addTourist(tourist10);

//         List<Tourist> flightTourists = Lists.newArrayList();


//         long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime(); // 2020 its: 1577833200000 long
//         long end = Timestamp.valueOf("2020-10-03 00:00:00").getTime(); // 2 days difference: 172800001 long

//         long diff = end - offset + 1;


//         Flight flight;
//         FlightReservation flightReservation;

//         for (int i = 0; i < 100; i++) {

//             //randomize data for flight

//             Timestamp depart = new Timestamp(offset + (long)(Math.random() * diff));
//             Timestamp arrive = new Timestamp(depart.getTime() + 172800001L);
//             int touristAmount = r.nextInt(11); // 11
//             int freeSeats = r.nextInt(5) + 1;
//             float price = (float)r.nextInt(9001) + 1000;

//             flight = new Flight(
//                     depart,
//                     arrive,
//                     freeSeats + touristAmount,
//                     touristAmount,
//                     price);

//             //new flight created

//             restApi.addFlight(flight); // ADD flight

//             flightTourists.clear();
//             flightTourists.addAll(restApi.getAllTourists().getBody());
//             flightTourists.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
//             flightTourists.remove(0);                                               //substract user
//             Collections.shuffle(flightTourists);
//             flightTourists = flightTourists.subList(0, touristAmount);// add different people


//             for (int j = 0; j < touristAmount; j++){
//                 flightReservation = new FlightReservation(flight,
//                         flightTourists.get(j));
//                 restApi.addReservation(flightReservation);
//             }                                                                                //adding reservations with every tourist
//         }
//     }
// }
