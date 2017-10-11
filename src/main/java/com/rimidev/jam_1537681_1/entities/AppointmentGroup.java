/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.*;

/**
 *
 * @author Max
 */
public class AppointmentGroup {

    //AppointmentGroup fields
    private final IntegerProperty groupNumber;
    private final StringProperty groupName;
    private final StringProperty color;

    //Default Constructor setting nothing.
    public AppointmentGroup() {
        this.groupNumber = new SimpleIntegerProperty(0);
        this.groupName = new SimpleStringProperty("");
        this.color = new SimpleStringProperty("");
    }

    //Constructor initilizing as wanted
    public AppointmentGroup(int groupNumber, String groupName, String color) {
        this.groupNumber = new SimpleIntegerProperty(groupNumber);
        this.groupName = new SimpleStringProperty(groupName);
        this.color = new SimpleStringProperty(color);
    }

    /**
     * Simple setters and getters
     */
    public final int getGroupNumber() {
        return groupNumber.get();
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber.set(groupNumber);
    }

    public String getGroupName() {
        return groupName.get();
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }    

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    //binds
    public final IntegerProperty groupNumberProperty() {
        return groupNumber;
    }
    
    public final StringProperty groupNameProperty() {
        return groupName;
    }
    
    public final StringProperty colorProperty() {
        return color;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.groupNumber.get();
        hash = 59 * hash + Objects.hashCode(this.groupName.get());
        hash = 59 * hash + Objects.hashCode(this.color.get());
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
        final AppointmentGroup other = (AppointmentGroup) obj;
        if (this.groupNumber.get() != other.groupNumber.get()) {
            return false;
        }
        if (!Objects.equals(this.groupName.get(), other.groupName.get())) {
            return false;
        }
        if (!Objects.equals(this.color.get(), other.color.get())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AppointmentGroup{" + "groupNumber=" + groupNumber + ", groupName=" + groupName + ", color=" + color + '}';
    }


} // End of AppointmentGroup class
