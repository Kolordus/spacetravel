package com.kolak.spacetravel.view;


import com.google.common.collect.Sets;
import com.kolak.spacetravel.api.RestApi;
import com.kolak.spacetravel.model.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route("tourists")
public class ViewTourist extends VerticalLayout {

    private RestApi restApi;

    //main grid
    private Grid<Tourist> touristGrid;
    private GridContextMenu<Tourist> contextMenu;
    private Dialog dialogAddNewTourist;
    private Button buttonAdd;

    //update tourist things
    //grid and its list
    private Grid<FlightDto> flightDtoGrid;
    private Set<FlightDto> flightDtoList;

    //combobox
    private Set<FlightDto> allFlightDtoSet; //used to combobox
    private Set<Flight> flights; // this is transfered to flight dto


    private GridContextMenu<FlightDto> contextMenuDelete;

    private Dialog dialogUpdateTourist;
    private Button buttonAddFlight;

    private TextField textFieldName;
    private TextField textFieldSurame;
    private TextField textFieldCountry;
    private DatePicker datePicker;
    private NativeButton nativeButtonConfirm;
    private RadioButtonGroup<Sex> radioButtonGroup;

    private VerticalLayout verticalLayout;
    private HorizontalLayout horizontalLayout;

    @Autowired
    public ViewTourist(RestApi restApi) {
        this.restApi = restApi;
        initializeUI();

        Notification notification = new Notification(new Label("In order to change any table, right click on any row. REMEMBER TO SCROLL DOWN AND CLICK CONFIRM!"));
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();


        buttonAdd.addClickListener(clickEvent ->
        {
            createAddDialog();
            dialogAddNewTourist.open();
        });

        GridMenuItem<Tourist> update = contextMenu.addItem("Update",
                event -> {
                createUpdateDialog(event.getItem().get().getId());

                event.getItem().ifPresent(tourist -> {
                            textFieldName.setValue(tourist.getName());
                                    textFieldSurame.setValue(tourist.getSurname());
                                    radioButtonGroup.setValue(tourist.getSex());
                                    textFieldCountry.setValue(tourist.getCountry());
                                    datePicker.setValue(tourist.getBirthDate());

                                    dialogUpdateTourist.open();
                        });

                    touristGrid.setItems(restApi.getAllTourists().getBody());

        });

        contextMenu.addItem("Delete",
                event -> {
                    Dialog dialogDel = new Dialog();
                    dialogDel.add(new Label("Really want to delete?"));
                    dialogDel.open();
                    event.getItem().ifPresent(tourist ->
                            {
                                dialogDel.add(new NativeButton("Confirm",
                                        e ->  {
                                            restApi.deleteAllReservationsByTouristId(event.getItem().get().getId());
                                            restApi.deleteTourist(tourist.getId());
                                            touristGrid.setItems(restApi.getAllTourists().getBody());
                                            dialogDel.close();
                                        }));
                            });
                }
        );

        Button buttonReservations = new Button("Reservations");
        buttonReservations.addClickListener(clickEvent ->
                buttonReservations.getUI().ifPresent(ui -> ui.navigate("reservations")));

        Button buttonFlights = new Button("Flights");
        buttonFlights.addClickListener(clickEvent ->
                buttonFlights.getUI().ifPresent(ui -> ui.navigate("fligths")));


        add(buttonAdd, touristGrid, buttonReservations, buttonFlights);
    }

    public void initializeUI() {
        touristGrid = new Grid<>(Tourist.class);
        flightDtoGrid = new Grid<>(FlightDto.class);
        touristGrid.setItems(restApi.getAllTourists().getBody());
        touristGrid.removeColumnByKey("flights");
        contextMenu = new GridContextMenu<>(touristGrid);
        contextMenuDelete = new GridContextMenu<>(flightDtoGrid);
        touristGrid.setColumns("id", "name", "surname", "sex", "country", "birthDate");

        flightDtoList = Sets.newHashSet();

        buttonAdd = new Button("Add Tourist");


        allFlightDtoSet = Sets.newHashSet(); //used to combobox
        flights = Sets.newHashSet(); // this is transfered to flight dto


    }

    public void createAddDialog() {
        dialogAddNewTourist = new Dialog();

        textFieldName = new TextField("Name:");
        textFieldName.setPattern("[A-Z][a-z]*");
        textFieldName.setPreventInvalidInput(true);

        textFieldSurame = new TextField("Surname:");
        textFieldSurame.setPattern("[A-Z][a-z]*");
        textFieldSurame.setPreventInvalidInput(true);

        textFieldCountry = new TextField("Country:");
        textFieldCountry.setPattern("[A-Z][a-z]*");
        textFieldCountry.setPreventInvalidInput(true);

        datePicker = new DatePicker("Date of birth");

        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(Sex.MALE, Sex.FEMALE);

        verticalLayout = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();


        nativeButtonConfirm = new NativeButton("Confirm", event ->
        {
            restApi.addTourist(new Tourist(
                    textFieldName.getValue(),
                    textFieldSurame.getValue(),
                    radioButtonGroup.getValue(),
                    textFieldCountry.getValue(),
                    datePicker.getValue()));
            touristGrid.setItems(restApi.getAllTourists().getBody());
            dialogAddNewTourist.close();
        });

        dialogAddNewTourist.add(verticalLayout);
        verticalLayout.add(textFieldName,textFieldSurame,
                radioButtonGroup, textFieldCountry, datePicker, nativeButtonConfirm);
    }

    public void createUpdateDialog(Long id) {

        Tourist thisTourist = restApi.getTouristById(id).getBody();
        dialogUpdateTourist = new Dialog();

        buttonAddFlight = new Button("Add Flight");

        textFieldName = new TextField("Name:");
        textFieldName.setPattern("[A-Z][a-z]*");
        textFieldName.setPreventInvalidInput(true);

        textFieldSurame = new TextField("Surname:");
        textFieldSurame.setPattern("[A-Z][a-z]*");
        textFieldSurame.setPreventInvalidInput(true);

        textFieldCountry = new TextField("Country:");
        textFieldCountry.setPattern("[A-Z][a-z]*");
        textFieldCountry.setPreventInvalidInput(true);

        datePicker = new DatePicker("Date of birth");

        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(Sex.MALE, Sex.FEMALE);


        verticalLayout = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();
        ///////////////////////////////
//Filling list for tourists table

        flightDtoList.clear();
        restApi.getReservationsByTouristId(thisTourist.getId())
                .getBody()
                .stream()
                .forEach(flRes -> flightDtoList.add(new FlightDto(
                        flRes.getFlight().getId(),
                        flRes.getFlight().getTicketPrice(),
                        flRes.getFlight().getDepartureDate()
                )));

        flightDtoGrid.setItems(flightDtoList);


        GridMenuItem<FlightDto> delete = contextMenuDelete.addItem("Delete",
                event -> {

                    Dialog dialogDel = new Dialog();
                    dialogDel.add(new Label("Really want to delete?"));
                    dialogDel.open();

                    ////////////
                    dialogDel.add(new NativeButton("Confirm",
                            e -> {
                                dialogDel.close();

                                //get id for both
                                FlightReservation deletedFlight = restApi.getByFlightIdAndAndTouristId(
                                        event.getItem().get().getId(),
                                        thisTourist.getId())
                                        .getBody();

                                restApi.deleteReservation(deletedFlight.getId());

                                //refresh table
                                flightDtoList.remove(new FlightDto(
                                        deletedFlight.getFlight().getId(),
                                        deletedFlight.getFlight().getTicketPrice(),
                                        deletedFlight.getFlight().getDepartureDate()
                                ));
                                flightDtoGrid.setItems(flightDtoList);

//                                textFieldTouristAmount.setValue(String.valueOf(restApi.getReservationsByFlightId(thisFlight.getId()).getBody().size()));
                            }));
                });
        ///////////////


        //add flight  to tourist
        buttonAddFlight.addClickListener(clickEvent -> {
            Dialog addTouristDialog = new Dialog();
            ComboBox<FlightDto> allFlightsComboBox = new ComboBox<>();

            allFlightDtoSet.clear();
            restApi.getAllFlights().getBody().stream().forEach(flight -> allFlightDtoSet.add(new FlightDto(
                    flight.getId(),
                    flight.getTicketPrice(),
                    flight.getDepartureDate()
            )));



            restApi.getReservationsByTouristId(thisTourist.getId())
                    .getBody()
                    .stream()
                    .forEach(fliRes -> flightDtoList.add(new FlightDto(
                            fliRes.getFlight().getId(),
                            fliRes.getFlight().getTicketPrice(),
                            fliRes.getFlight().getDepartureDate()
                    )));

            allFlightDtoSet.removeAll(flightDtoList);
            allFlightsComboBox.setItems(allFlightDtoSet);
            addTouristDialog.add(allFlightsComboBox);




                //

                //add reservation
                addTouristDialog.add(new NativeButton("Add", event -> {
                    int touristAmount = restApi.getFlightById(allFlightsComboBox.getValue().getId()).getBody().getTouristAmount();
                    int seatsAmount = restApi.getFlightById(allFlightsComboBox.getValue().getId()).getBody().getSeatsAmount();

                    if (touristAmount >= seatsAmount) {
                        addTouristDialog.add(new Label("No more seats available!"));
                        addTouristDialog.open();
                    }
                    else {
                        FlightReservation flightReservation = new FlightReservation(
                                restApi.getFlightById(allFlightsComboBox.getValue().getId()).getBody(),
                                thisTourist
                        );

                        restApi.addReservation(flightReservation);



                        //refresh table
                        flightDtoList.add(allFlightsComboBox.getValue());
                        flightDtoGrid.setItems(flightDtoList);

                        addTouristDialog.close();

                    }




                })); //event end

            addTouristDialog.open();

        });



        ////////////////FINAL STEP
        nativeButtonConfirm = new NativeButton("Confirm", event ->
        {
            restApi.updateTourist(id, new Tourist(textFieldName.getValue(),
                    textFieldSurame.getValue(),
                    radioButtonGroup.getValue(),
                    textFieldCountry.getValue(),
                    datePicker.getValue()));
            dialogUpdateTourist.close();
            touristGrid.setItems(restApi.getAllTourists().getBody());
        });

        horizontalLayout.add(textFieldName,textFieldSurame, radioButtonGroup);
        verticalLayout.add(textFieldCountry,
                datePicker,buttonAddFlight, flightDtoGrid ,
                nativeButtonConfirm);

        dialogUpdateTourist.add(horizontalLayout, verticalLayout);

    }

}
