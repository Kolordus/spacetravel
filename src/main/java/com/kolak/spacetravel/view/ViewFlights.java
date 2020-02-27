package com.kolak.spacetravel.view;


import com.google.common.collect.Sets;
import com.kolak.spacetravel.api.RestApi;
import com.kolak.spacetravel.model.Flight;
import com.kolak.spacetravel.model.FlightReservation;
import com.kolak.spacetravel.model.Tourist;
import com.kolak.spacetravel.model.TouristDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route("fligths")
public class ViewFlights extends VerticalLayout {

    private RestApi restApi;

    // /flights endpoint items
    private Button buttonAdd;
    private Grid<Flight> flightGrid;
    private GridContextMenu<Flight> contextMenu;
    private GridContextMenu<TouristDto> contextMenuDelete;
    private Dialog dialogAddNewFlight;
    //

    //add flight items
    private FlightTimeField departDate;
    private FlightTimeField arriveDate;


    //both add and update
    private TextField textFieldSeatAmount;
    private TextField textFieldTouristAmount;
    private TextField textFieldPrice;
    private NativeButton nativeButtonConfirm;
    private VerticalLayout verticalLayout;

    //update
    private Dialog dialogUpdateFlight;
    private Button buttonAddTourist;

    private Set<TouristDto> allTouristDtoSet; //used for combobox
    private Set<Tourist> tourists; // this is trasfered to tourist dto

    private Grid<TouristDto> touristDtoGrid; //this cointains touristMinList
    private Set<TouristDto> touristDtoList; //list of tourist in single flight

    @Autowired
    public ViewFlights(RestApi restApi) {
        this.restApi = restApi;
        initializeUI();

        Notification notification = new Notification(new Label("In order to change any table, right click on any row. REMEMBER TO SCROLL DOWN AND CLICK CONFIRM!"));
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();

        /*
        Add new flight by open dialog
         */
        buttonAdd.addClickListener(clickEvent -> {
            createAddDialog();
            dialogAddNewFlight.open();
        });

        ///
        GridMenuItem<Flight> update = contextMenu.addItem("Update",
                event -> {

                    createUpdateDialog(event.getItem().get().getId());

                    event.getItem().ifPresent(flight -> {
                        departDate.setValue(flight.getDepartureDate());
                        arriveDate.setValue(flight.getArrivalDate());
                        textFieldSeatAmount.setValue(String.valueOf(flight.getSeatsAmount()));
                        textFieldTouristAmount.setValue(String.valueOf(flight.getTouristAmount()));
                        textFieldPrice.setValue(String.valueOf(flight.getTicketPrice()));

                        dialogUpdateFlight.open();
                    });

                    flightGrid.setItems(restApi.getAllFlights().getBody());

                });
//update ends -> delete
        contextMenu.addItem("Delete",
                event -> {
                    Dialog dialogDel = new Dialog();
                    dialogDel.add(new Label("Really want to delete?"));
                    dialogDel.open();

                    event.getItem().ifPresent(tourist ->
                    {

                        dialogDel.add(new NativeButton("Confirm",
                                e -> {
                                    restApi.deleteAllReservationsByFlightId(event.getItem().get().getId());
                                    restApi.deleteFlight(event.getItem().get().getId());

                                    flightGrid.setItems(restApi.getAllFlights().getBody());
                                    dialogDel.close();
                                }));
                    });
                }
        );

        Button buttonReservations = new Button("Reservations");
        buttonReservations.addClickListener(clickEvent ->
                buttonReservations.getUI().ifPresent(ui -> ui.navigate("reservations")));

        Button buttonTourists = new Button("Tourists");
        buttonTourists.addClickListener(clickEvent ->
                buttonTourists.getUI().ifPresent(ui -> ui.navigate("tourists")));

        add(buttonAdd, flightGrid, buttonReservations, buttonTourists);
    }

    public void initializeUI() {
        flightGrid = new Grid<>(Flight.class);
        touristDtoGrid = new Grid<>(TouristDto.class);
        flightGrid.setItems(restApi.getAllFlights().getBody());
        flightGrid.removeColumnByKey("tourists");
        contextMenu = new GridContextMenu<>(flightGrid);
        contextMenuDelete = new GridContextMenu<>(touristDtoGrid);
        touristDtoList = Sets.newHashSet();
        allTouristDtoSet = Sets.newHashSet();
        buttonAdd = new Button("Add Flight");
        tourists = Sets.newHashSet();

        flightGrid.setColumns("id", "departureDate", "arrivalDate", "ticketPrice", "seatsAmount", "touristAmount");

    }

    public void createAddDialog() {
        dialogAddNewFlight = new Dialog();

        textFieldSeatAmount = new TextField("Seat amount:");
        textFieldSeatAmount.setPattern("[0-9]*");
        textFieldSeatAmount.setPreventInvalidInput(true);
        textFieldSeatAmount.setMaxLength(2);

        textFieldPrice = new TextField("Price:");
        textFieldPrice.setPattern("[0-9]*");
        textFieldPrice.setPreventInvalidInput(true);

        departDate = new FlightTimeField("Departure");
        arriveDate = new FlightTimeField("Arrival");

        verticalLayout = new VerticalLayout();

        nativeButtonConfirm = new NativeButton("Confirm", event ->
        {
            restApi.addFlight(new Flight(
                    departDate.generateModelValue(),
                    arriveDate.generateModelValue(),
                    Integer.parseInt(textFieldSeatAmount.getValue()),
                    0,
                    Float.parseFloat(textFieldPrice.getValue())));
            dialogAddNewFlight.close();
            flightGrid.setItems(restApi.getAllFlights().getBody());
        });


        dialogAddNewFlight.add(verticalLayout);
        verticalLayout.add(departDate, arriveDate,
                textFieldSeatAmount, textFieldPrice, nativeButtonConfirm);

    }

    public void createUpdateDialog(Long id) {

        Flight thisFlight = restApi.getFlightById(id).getBody();


        dialogUpdateFlight = new Dialog();
        buttonAddTourist = new Button("Add Tourist");

        textFieldSeatAmount = new TextField("Seat amount");
        textFieldSeatAmount.setPattern("[0-9]*");
        textFieldSeatAmount.setPreventInvalidInput(true);
        textFieldSeatAmount.setMaxLength(2);


        textFieldTouristAmount = new TextField("Tourists amount");
        textFieldTouristAmount.setValue(String.valueOf(restApi.getReservationsByFlightId(thisFlight.getId()).getBody().size()));
        textFieldTouristAmount.setEnabled(false);

        textFieldPrice = new TextField("Ticket price");
        textFieldPrice.setPattern("[0-9]*.[0]");
        textFieldPrice.setPreventInvalidInput(true);

        departDate = new FlightTimeField("Departure");
        arriveDate = new FlightTimeField("Arrival");
        verticalLayout = new VerticalLayout();

        /////////////////


        //Filling list for tourists table
        touristDtoList.clear();
        restApi.getReservationsByFlightId(thisFlight.getId())
                .getBody()
                .stream()
                .forEach(flRes -> touristDtoList.add(new TouristDto(
                        flRes.getTourist().getId(),
                        flRes.getTourist().getName(),
                        flRes.getTourist().getSurname()
                )));

        touristDtoGrid.setItems(touristDtoList);


        GridMenuItem<TouristDto> delete = contextMenuDelete.addItem("Delete",
                event -> {

                    Dialog dialogDel = new Dialog();
                    dialogDel.add(new Label("Really want to delete?"));
                    dialogDel.open();

                    ////////////
                    dialogDel.add(new NativeButton("Confirm",
                            e -> {
                                dialogDel.close();

                                //get id for both
                                FlightReservation deletedFlight = restApi.getByFlightIdAndAndTouristId(thisFlight.getId(),
                                        event.getItem().get().getId()).getBody();

                                restApi.deleteReservation(deletedFlight.getId());

                                //refresh table
                                touristDtoList.remove(new TouristDto(
                                        deletedFlight.getTourist().getId(),
                                        deletedFlight.getTourist().getName(),
                                        deletedFlight.getTourist().getSurname()
                                ));
                                touristDtoGrid.setItems(touristDtoList);

                                textFieldTouristAmount.setValue(String.valueOf(restApi.getReservationsByFlightId(thisFlight.getId()).getBody().size()));
                            }));
                });
        ///////////////


        //add tourist to flight
        buttonAddTourist.addClickListener(clickEvent -> {
            Dialog addTouristDialog = new Dialog();
            ComboBox<TouristDto> allTouristsComboBox = new ComboBox<>();


            if (Integer.parseInt(textFieldTouristAmount.getValue()) >= thisFlight.getSeatsAmount()){
                addTouristDialog.add(new Label("No more seats available!"));
                addTouristDialog.open();
            }

            else {


            //populate combobox!
            allTouristDtoSet.clear();
            restApi.getAllTourists().getBody().stream().forEach(tourist -> allTouristDtoSet.add(new TouristDto(
                    tourist.getId(),
                    tourist.getName(),
                    tourist.getSurname()
            )));
            allTouristDtoSet.removeAll(touristDtoList);
            allTouristsComboBox.setItems(allTouristDtoSet);
            addTouristDialog.add(allTouristsComboBox);

            //


            //add reservation
            addTouristDialog.add(new NativeButton("Add", event -> {

                    FlightReservation flightReservation = new FlightReservation(thisFlight,
                            restApi.getTouristById(allTouristsComboBox.getValue().getId()).getBody());
                    restApi.addReservation(flightReservation);

                    //refresh table
                    touristDtoList.add(allTouristsComboBox.getValue());
                    touristDtoGrid.setItems(touristDtoList);

                    textFieldTouristAmount.setValue(String.valueOf(restApi.getReservationsByFlightId(thisFlight.getId()).getBody().size()));
                    addTouristDialog.close();


            })); //event end
            }

            addTouristDialog.open();

        });


        ////////////////////////////////////////////////////FINAL STEP
            nativeButtonConfirm = new NativeButton("Confirm", event ->
            {

                thisFlight.setDepartureDate(departDate.generateModelValue());
                thisFlight.setArrivalDate(arriveDate.generateModelValue());
                thisFlight.setSeatsAmount(Integer.parseInt(textFieldSeatAmount.getValue()));
                thisFlight.setTouristAmount(Integer.parseInt(textFieldTouristAmount.getValue()));
                thisFlight.setTicketPrice(Float.parseFloat(textFieldPrice.getValue()));

                restApi.updateFlight(id, thisFlight);

                dialogUpdateFlight.close();
                flightGrid.setItems(restApi.getAllFlights().getBody());
            });


            //

        //

        verticalLayout.add(departDate, arriveDate, textFieldSeatAmount, textFieldTouristAmount,
                    textFieldPrice, buttonAddTourist, touristDtoGrid, nativeButtonConfirm);
            dialogUpdateFlight.add(verticalLayout);

    }
}
