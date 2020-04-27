# spacetravel

This project is a show off of my skills for internship interwiev.
I succedded :)

Technologies used: Spring Boot, Hibernate, Vaadin

View classes 'ViewFlights' and 'ViewTourist' are meant to be admin's panel for managing data (such as creating new flights managing reservations).
Class 'ReservationView' is custemer side of app. Spring Security was not required.

To transfer data to VIEW classes i had to prepare some model classes: Flight, Tourist and Reservation.
Reservation class gets IDs of two others.
Other classes in model are auxillary for passing needed informations (DTOs).

Manager keeps dependencies of all repositories and works as service for RestApi.
