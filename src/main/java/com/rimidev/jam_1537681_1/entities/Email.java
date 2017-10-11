/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.entities;

import static java.lang.Boolean.FALSE;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.*;
/**
 * This class is the model for the email table
 *
 * @author Max
 */
public class Email {

    //Email fields
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty URL;
    private final IntegerProperty port;
    private final BooleanProperty isDefault;
    private final IntegerProperty reminder;

    //Default constructer setting an empty Email object
    public Email() {
       this.name = new SimpleStringProperty("");
       this.email = new SimpleStringProperty("");
       this.password = new SimpleStringProperty("");
       this.URL = new SimpleStringProperty("");
       this.port = new SimpleIntegerProperty(0);
       this.isDefault = new SimpleBooleanProperty(false);
       this.reminder = new SimpleIntegerProperty(0);
    }

    //Constructor to initilize an Email object with wanted parameters.
    public Email(String name, String email, String password, String URL, int port, Boolean isDefault, int reminder) {
       this.name = new SimpleStringProperty(name);
       this.email = new SimpleStringProperty(email);
       this.password = new SimpleStringProperty(password);
       this.URL = new SimpleStringProperty(URL);
       this.port = new SimpleIntegerProperty(port);
       this.isDefault = new SimpleBooleanProperty(isDefault);
       this.reminder = new SimpleIntegerProperty(reminder);
    }

    /**
     * Simple getters and setters for all the fields
     */
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getURL() {
        return URL.get();
    }

    public void setURL(String URL) {
        this.URL.set(URL);
    }

    public int getPort() {
        return port.get();
    }

    public void setPort(int port) {
        this.port.set(port);
    }
    
    public Boolean getIsDefault() {
        return isDefault.get();
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault.set(isDefault);
    }
    
    public int getReminder() {
        return reminder.get();
    }

    public void setReminder(int reminder) {
        this.reminder.set(reminder);
    }
    
    //binds
    public final StringProperty nameProperty(){
        return name;
    }
    
    public final StringProperty emailProperty(){
        return email;
    }
    
    public final StringProperty passwordProperty(){
        return password;
    }
    
    public final StringProperty URLProperty(){
        return URL;
    }
    
    public final IntegerProperty portProperty(){
        return port;
    }
    
    public final BooleanProperty isDefaultProperty(){
        return isDefault;
    }
    
    public final IntegerProperty reminderProperty(){
        return reminder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.name.get());
        hash = 37 * hash + Objects.hashCode(this.email.get());
        hash = 37 * hash + Objects.hashCode(this.password.get());
        hash = 37 * hash + Objects.hashCode(this.URL.get());
        hash = 37 * hash + this.port.get();
        hash = 37 * hash + Objects.hashCode(this.isDefault.get());
        hash = 37 * hash + this.reminder.get();
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
        final Email other = (Email) obj;
        if (this.port.get() != other.port.get()) {
            return false;
        }
        if (this.reminder.get() != other.reminder.get()) {
            return false;
        }
        if (!Objects.equals(this.name.get(), other.name.get())) {
            return false;
        }
        if (!Objects.equals(this.email.get(), other.email.get())) {
            return false;
        }
        if (!Objects.equals(this.password.get(), other.password.get())) {
            return false;
        }
        if (!Objects.equals(this.URL.get(), other.URL.get())) {
            return false;
        }
        if (!Objects.equals(this.isDefault.get(), other.isDefault.get())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Email{" + "name=" + name + ", email=" + email + ", password=" + password + ", URL=" + URL + ", port=" + port + ", isDefault=" + isDefault + ", reminder=" + reminder + '}';
    }

   
    
    
    
} // End of email class
