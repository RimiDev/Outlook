package com.rimidev.jam_1537681_1.entities;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the daily bean that represents the daily view of the program.
 * It will display times from 0-24 hours in 30 minute intervals with their
 * corresponding appointments.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class dailyBean {
    
    private StringProperty time;
    private StringProperty apt;
    private List<Appointment> apts;

    public dailyBean(String col1, String col2, List<Appointment> apts){
        this.time = new SimpleStringProperty(col1);
        this.apt = new SimpleStringProperty(col2);
        this.apts = apts;
    }
    
    public dailyBean(){
        this.time = new SimpleStringProperty("");
        this.apt = new SimpleStringProperty("");
    }

    public void setTime(String data) {
        time.set(data);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setApt(String data) {
        apt.set(data);
    }

    public String getApt() {
        return apt.get();
    }

    public StringProperty aptProperty() {
        return apt;
    }  
    
    public void setDailyApts(Appointment apt){
        apts.add(apt);
    }
    
    public List<Appointment> getDailyApts(){
        return apts;
    }
    
    @Override
    public String toString(){
        return "Appointment: " + apts.size();
    }    
    
}

