/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.entities;

import static java.lang.Boolean.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.*;

/**
 *
 * @author Max
 */
public class Appointment {

    //Appointment fields
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty location;
    private final ObjectProperty<Timestamp> startTime;
    private final ObjectProperty<Timestamp> endTime;
    private final StringProperty details;
    private final BooleanProperty wholeDay;
    private final IntegerProperty appointmentGroup;
    private final BooleanProperty alarm;

    //Default constructor setting to nothing.
    public Appointment() {
        this.id = new SimpleIntegerProperty(0);
        this.title = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
        this.startTime = new SimpleObjectProperty("");
        this.endTime = new SimpleObjectProperty("");
        this.details = new SimpleStringProperty("");
        this.wholeDay = new SimpleBooleanProperty(false);
        this.appointmentGroup = new SimpleIntegerProperty(0);
        this.alarm = new SimpleBooleanProperty(false);
    }

    //Constructor initilizing as wanted.
    public Appointment(int id, String title, String location, Timestamp startTime, Timestamp endTime,
            String details, boolean wholeDay, int appointmentGroup, boolean alarm) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.location = new SimpleStringProperty(location);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.details = new SimpleStringProperty(details);
        this.wholeDay = new SimpleBooleanProperty(wholeDay);
        this.appointmentGroup = new SimpleIntegerProperty(appointmentGroup);
        this.alarm = new SimpleBooleanProperty(alarm);
    }

    /**
     * Simple setters and getters
     * @return 
     */
    public final int getId() {
        return id.get();
    }

    public void setId(final int id) {
        this.id.set(id);
    }

    public final String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public final String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public final Timestamp getStartTime() {
        return startTime.get();
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime.set(startTime);
    }

    public final Timestamp getEndTime() {
        return endTime.get();
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime.set(endTime);
    }

    public final String getDetails() {
        return details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public final boolean getWholeDay() {
        return wholeDay.get();
    }

    public void setWholeDay(boolean wholeDay) {
        this.wholeDay.set(wholeDay);
    }

    public final int getAppointmentGroup() {
        return appointmentGroup.get();
    }

    public void setAppointmentGroup(int appointmentGroup) {
        this.appointmentGroup.set(appointmentGroup);
    }

    public final boolean getAlarm() {
        return alarm.get();
    }

    public void setAlarm(boolean alarm) {
        this.alarm.set(alarm);
    }
    
    //binding
    public final IntegerProperty idProperty() {
        return id;
    }
    
    public final StringProperty titleProperty() {
        return title;
    }
    
    public final StringProperty locationProperty() {
        return location;
    }
    
    public final ObjectProperty startTimeProperty() {
        return startTime;
    }
    
    public final ObjectProperty endTimeProperty() {
        return endTime;
    }
    
    public final StringProperty detailsProperty() {
        return details;
    }
    
    public final BooleanProperty wholeDayProperty() {
        return wholeDay;
    }
    
    public final IntegerProperty appointmentGroupProperty() {
        return appointmentGroup;
    }
    
    public final BooleanProperty alarmProperty() {
        return alarm;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id.get();
        hash = 47 * hash + Objects.hashCode(this.title.get());
        hash = 47 * hash + Objects.hashCode(this.location.get());
        hash = 47 * hash + Objects.hashCode(this.startTime.get());
        hash = 47 * hash + Objects.hashCode(this.endTime.get());
        hash = 47 * hash + Objects.hashCode(this.details.get());
        hash = 47 * hash + (this.wholeDay.get() ? 1 : 0);
        hash = 47 * hash + this.appointmentGroup.get();
        hash = 47 * hash + (this.alarm.get() ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Appointment other = (Appointment) obj;
        if (this.id.get() != other.id.get()) {
            return false;
        }
        if (this.wholeDay.get() != other.wholeDay.get()) {
            return false;
        }
        if (this.appointmentGroup.get() != other.appointmentGroup.get()) {
            return false;
        }
        if (this.alarm.get() != other.alarm.get()) {
            return false;
        }
        if (!Objects.equals(this.title.get(), other.title.get())) {
            return false;
        }
        if (!Objects.equals(this.location.get(), other.location.get())) {
            return false;
        }
        if (!Objects.equals(this.details.get(), other.details.get())) {
            return false;
        }
        if (!Objects.equals(this.startTime.get(), other.startTime.get())) {
            return false;
        }
        if (!Objects.equals(this.endTime.get(), other.endTime.get())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", title=" + title + ", location=" + location + ", startTime=" + startTime + ", endTime=" + endTime + ", details=" + details + ", wholeDay=" + wholeDay + ", appointmentGroup=" + appointmentGroup + ", alarm=" + alarm + '}';
    }


} // End of Appointment class
