package com.kolak.spacetravel.view;

import com.google.common.collect.Sets;
import com.kolak.spacetravel.api.RestApi;
import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.FlightReservation;
import com.kolak.spacetravel.model.Tourist;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


@Route("reservations")
public class ReservationView extends VerticalLayout {

    private RestApi restApi;
    private Tourist user;

    //date searching field
    private FlightTimeField departDate;

    //price searching fields
    private TextField lowerPrice;
    private TextField higherPrice;

    private Button searchButton;
    private Set<Long> reservedFlights;
    private Label idLabel;
    private TextField flightsIdTextField;

    private Grid<Flight> flightGrid;

    @Autowired
    public ReservationView(RestApi restApi) {
        this.restApi = restApi;
        user = restApi.getTouristById(1L).getBody();   //user

        initiateUI();

        Notification notification = new Notification(new Label("In order to reserve a flight -> double click on it"));
        notification.setDuration(5000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();

        searchButton.addClickListener(clickEvent -> {


            //only date form is done
            if (departDate.isEmpty() == false && lowerPrice.getValue().isEmpty() == true && higherPrice.getValue().isEmpty() == true){
                List<Flight> dateOnlySearch = restApi.getFlightAfterDate(departDate.generateModelValue())
                        .getBody();
                flightGrid.setItems(dateOnlySearch);
            }

            //date form + lower price
            else if (departDate.isEmpty() == false && lowerPrice.getValue().isEmpty() == false && higherPrice.getValue().isEmpty() == true){
                List<Flight> dateAndLowPriceSearch = restApi.getFlightAfterDateAndBetweenPrices(departDate.generateModelValue(),
                        Float.parseFloat(lowerPrice.getValue()),
                        9999999)
                        .getBody();
                flightGrid.setItems(dateAndLowPriceSearch);
            }

            //date form + higher price
            else if (departDate.isEmpty() == false && lowerPrice.getValue().isEmpty() == true && higherPrice.getValue().isEmpty() == false){
                List<Flight> dateAndHighPriceSearch = restApi.getFlightAfterDateAndBetweenPrices(departDate.generateModelValue(),
                        0, Float.parseFloat(higherPrice.getValue()))
                        .getBody();
                flightGrid.setItems(dateAndHighPriceSearch);
            }

            //every parameter
            else if (departDate.isEmpty() == false && lowerPrice.getValue().isEmpty() == false && higherPrice.getValue().isEmpty() == false){
                List<Flight> dateAndPricesSearch = restApi.getFlightAfterDateAndBetweenPrices(departDate.generateModelValue(),
                        Float.parseFloat(lowerPrice.getValue()),
                        Float.parseFloat(higherPrice.getValue()))
                        .getBody();
                flightGrid.setItems(dateAndPricesSearch);
            }

            //prices only
            else if (departDate.isEmpty() == true && lowerPrice.getValue().isEmpty() == false && higherPrice.getValue().isEmpty() == false){
                List<Flight> betweenPricesSearch = restApi.getFlightBetweenTicketPrices(Float.parseFloat(lowerPrice.getValue()), Float.parseFloat(higherPrice.getValue()))
                        .getBody();
                flightGrid.setItems(betweenPricesSearch);
            }

            //high price only
            else if (departDate.isEmpty() == true && lowerPrice.getValue().isEmpty() == true && higherPrice.getValue().isEmpty() == false){
                List<Flight> highPriceSearch = restApi.getFlightBetweenTicketPrices(0, Float.parseFloat(higherPrice.getValue()))
                        .getBody();
                flightGrid.setItems(highPriceSearch);
            }

            //lower price only
            else if (departDate.isEmpty() == true && lowerPrice.getValue().isEmpty() == false && higherPrice.getValue().isEmpty() == true) {
                List<Flight> lowPriceSearch = restApi.getFlightBetweenTicketPrices(Float.parseFloat(lowerPrice.getValue()), 9999999)
                        .getBody();
                flightGrid.setItems(lowPriceSearch);
            }

            //nothing
            else if (departDate.isEmpty() == true && lowerPrice.getValue().isEmpty() == true && higherPrice.getValue().isEmpty() == true){
                List<Flight> allFlights = restApi.getAllFlights().getBody();
                flightGrid.setItems(allFlights);

            }


        });

        Button buttonFlights = new Button("Flights");
        buttonFlights.addClickListener(clickEvent ->
                buttonFlights.getUI().ifPresent(ui -> ui.navigate("fligths")));

        Button buttonTourists = new Button("Tourists");
        buttonTourists.addClickListener(clickEvent ->
                buttonTourists.getUI().ifPresent(ui -> ui.navigate("tourists")));


        add(departDate,
                lowerPrice, higherPrice,
                searchButton, idLabel , flightsIdTextField, flightGrid,
                buttonFlights, buttonTourists);
    }

    public void initiateUI() {
        departDate = new FlightTimeField("Choose departure date");
        lowerPrice = new TextField("Price from:");
        lowerPrice.setPattern("[0-9]*");
        lowerPrice.setPreventInvalidInput(true);
        higherPrice = new TextField("Price to:");
        higherPrice.setPattern("[0-9]*");
        higherPrice.setPreventInvalidInput(true);
        searchButton = new Button("Search!");
        reservedFlights = Sets.newHashSet();
        idLabel = new Label("Reserved fligths ID");
        flightsIdTextField = new TextField();
        flightsIdTextField.setEnabled(false);

        flightGrid = new Grid<>(Flight.class);
        flightGrid.removeColumnByKey("tourists");
        flightGrid.setColumns("id", "departureDate", "arrivalDate", "ticketPrice", "seatsAmount", "touristAmount");
        flightGrid.setItems(restApi.getAllFlights().getBody());




        restApi.getReservationsByTouristId(user.getId())
                .getBody()
                .stream()
                .forEach(flightReservation -> {
            reservedFlights.add(flightReservation.getId());



            if (flightsIdTextField.isEmpty()){flightsIdTextField.setValue(flightReservation.getFlight().getId().toString());}
            else {flightsIdTextField.setValue(flightsIdTextField.getValue() + ", " + flightReservation.getFlight().getId());}
        });


        flightGrid.addItemDoubleClickListener(item -> {

            Flight flight = restApi.getFlightById(item.getItem().getId()).getBody();

            if (reservedFlights.contains(flight.getId())){
                new Dialog(new Label("Already reserved!")).open();
            }
            else {

                if(flight.getTouristAmount() >= flight.getSeatsAmount()){
                    new Dialog(new Label("No available seats!")).open();
                }

                else {
                    new Dialog(new Label("Flight reserved!")).open();
                    reservedFlights.add(flight.getId());

                    if (flightsIdTextField.isEmpty()){flightsIdTextField.setValue(flight.getId().toString());}
                    else {flightsIdTextField.setValue(flightsIdTextField.getValue() + ", " + flight.getId());}

                    flight.setTouristAmount(flight.getTouristAmount() + 1);
                    restApi.updateFlight(flight.getId(), flight);
                    flightGrid.setItems(restApi.getAllFlights().getBody());
                    restApi.addReservation(new FlightReservation(flight, user));
                }

            }
        });
    }
}
