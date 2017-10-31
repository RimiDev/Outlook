package com.rimidev.jam_1537681_1.entities;

import java.util.Objects;
import javafx.beans.property.*;
/**
 * This is the SMTP bean that is used to represent the emails in the program.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class SMTP {
    //Email fields
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty URL;
    private final IntegerProperty port;
    private final BooleanProperty isDefault;
    private final IntegerProperty reminder;

    //Default constructer setting an empty SMTP object
    public SMTP() {
       this.id = new SimpleIntegerProperty(0);
       this.name = new SimpleStringProperty("");
       this.email = new SimpleStringProperty("");
       this.password = new SimpleStringProperty("");
       this.URL = new SimpleStringProperty("");
       this.port = new SimpleIntegerProperty(0);
       this.isDefault = new SimpleBooleanProperty(false);
       this.reminder = new SimpleIntegerProperty(0);
    }

    //Constructor to initilize an SMTP object with wanted parameters.
    public SMTP(int id, String name, String email, String password, String URL, int port, Boolean isDefault, int reminder) {
       this.id = new SimpleIntegerProperty(id);
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
    public final int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
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
    public final IntegerProperty idProperty() {
        return id;
    }
    
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

    //Hash
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

    //Equals
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
        final SMTP other = (SMTP) obj;
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

    //Simple toString
    @Override
    public String toString() {
        return "Email{" + "name=" + name + ", email=" + email + ", password=" + password + ", URL=" + URL + ", port=" + port + ", isDefault=" + isDefault + ", reminder=" + reminder + '}';
    }   
} // End of email class
