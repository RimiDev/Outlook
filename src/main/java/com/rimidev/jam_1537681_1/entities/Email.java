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
 * This class is the model for the email table
 *
 * @author Max
 */
public class Email {

    //Email fields
    private String name;
    private String email;
    private String password;
    private String URL;
    private int port;

    //Default constructer setting an empty Email object
    public Email() {
        this("", "", "", "", -1);
    }

    //Constructor to initilize an Email object with wanted parameters.
    public Email(String name, String email, String password, String URL, int port) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.URL = URL;
        this.port = port;
    }

    /**
     * Simple getters and setters for all the fields
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.URL);
        hash = 97 * hash + this.port;
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
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.URL, other.URL)) {
            return false;
        }
        return true;
    }
} // End of email class
