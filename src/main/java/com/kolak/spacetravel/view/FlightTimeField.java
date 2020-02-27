package com.kolak.spacetravel.view;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.sql.Timestamp;
import java.time.LocalTime;

public class FlightTimeField extends CustomField<Timestamp> {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private HorizontalLayout horizontalLayout;
    private Timestamp timestamp;



    public FlightTimeField(String name) {
        datePicker = new DatePicker();
        timePicker = new TimePicker();
        horizontalLayout = new HorizontalLayout();
        add(horizontalLayout);
        horizontalLayout.add(datePicker, timePicker);
        setLabel(name);

        datePicker.addValueChangeListener(evet ->
                timePicker.setValue(LocalTime.MIDNIGHT));
    }





    @Override
    protected Timestamp generateModelValue() {
        timestamp = Timestamp
                .valueOf(datePicker.getValue().atTime(timePicker.getValue()));
        return timestamp;
    }

    @Override
    protected void setPresentationValue(Timestamp timestamp) {
        datePicker.setValue(timestamp.toLocalDateTime().toLocalDate());
        timePicker.setValue(timestamp.toLocalDateTime().toLocalTime());
    }

}
