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

/**
 *
 * @author Max
 */
public class Appointment {

    //Appointment fields
    private int id;
    private String title;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String details;
    private boolean wholeDay;
    private int appointmentGroup;
    private int reminder;
    private boolean alarm;

    //Default constructor setting to nothing.
    public Appointment() {
        this(-1, "", "", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                "", FALSE, -1, -1, FALSE);
    }

    //Constructor initilizing as wanted.
    public Appointment(int id, String title, String location, Timestamp startTime, Timestamp endTime,
            String details, boolean wholeDay, int appointmentGroup, int reminder, boolean alarm) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.details = details;
        this.wholeDay = wholeDay;
        this.appointmentGroup = appointmentGroup;
        this.reminder = reminder;
        this.alarm = alarm;
    }

    /**
     * Simple setters and getters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean getWholeDay() {
        return wholeDay;
    }

    public void setWholeDay(boolean wholeDay) {
        this.wholeDay = wholeDay;
    }

    public int getAppointmentGroup() {
        return appointmentGroup;
    }

    public void setAppointmentGroup(int appointmentGroup) {
        this.appointmentGroup = appointmentGroup;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.location);
        hash = 47 * hash + Objects.hashCode(this.startTime);
        hash = 47 * hash + Objects.hashCode(this.endTime);
        hash = 47 * hash + Objects.hashCode(this.details);
        hash = 47 * hash + (this.wholeDay ? 1 : 0);
        hash = 47 * hash + this.appointmentGroup;
        hash = 47 * hash + this.reminder;
        hash = 47 * hash + (this.alarm ? 1 : 0);
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
        if (this.id != other.id) {
            return false;
        }
        if (this.wholeDay != other.wholeDay) {
            return false;
        }
        if (this.appointmentGroup != other.appointmentGroup) {
            return false;
        }
        if (this.reminder != other.reminder) {
            return false;
        }
        if (this.alarm != other.alarm) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", title=" + title + ", location=" + location + ", startTime=" + startTime + ", endTime=" + endTime + ", details=" + details + ", wholeDay=" + wholeDay + ", appointmentGroup=" + appointmentGroup + ", reminder=" + reminder + ", alarm=" + alarm + '}';
    }


} // End of Appointment class
