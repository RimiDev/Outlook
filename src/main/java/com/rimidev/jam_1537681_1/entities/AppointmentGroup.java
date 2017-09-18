/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Max
 */
public class AppointmentGroup {

    //AppointmentGroup fields
    private int groupNumber;
    private String groupName;
    private String color;

    //Default Constructor setting nothing.
    public AppointmentGroup() {
        this(-1, "", "");
    }

    //Constructor initilizing as wanted
    public AppointmentGroup(int groupNumber, String groupName, String color) {
        this.groupNumber = groupNumber;
        this.groupName = groupName;
        this.color = color;
    }

    /**
     * Simple setters and getters
     */
    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.groupNumber;
        hash = 59 * hash + Objects.hashCode(this.groupName);
        hash = 59 * hash + Objects.hashCode(this.color);
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
        if (this.groupNumber != other.groupNumber) {
            return false;
        }
        if (!Objects.equals(this.groupName, other.groupName)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AppointmentGroup{" + "groupNumber=" + groupNumber + ", groupName=" + groupName + ", color=" + color + '}';
    }


} // End of AppointmentGroup class
