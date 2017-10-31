package com.rimidev.jam_1537681_1.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *  The calendarRows bean that represent the rows used in the monthly and weekly view.
 *  Typically for the days of the month and all the appointments that go with it.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class CalendarRows {
    
    private StringProperty col1;
    private StringProperty col2;
    private StringProperty col3;
    private StringProperty col4;
    private StringProperty col5;
    private StringProperty col6;
    private StringProperty col7;

    //Default contructor
    public CalendarRows() {
        this("0", "0", "0", "0", "0", "0", "0");
    }

    //Constructor with appropriate fields.
    public CalendarRows(String col1, String col2, String col3, String col4, String col5, String col6, String col7) {
        this.col1 = new SimpleStringProperty(col1);
        this.col2 = new SimpleStringProperty(col2);
        this.col3 = new SimpleStringProperty(col3);
        this.col4 = new SimpleStringProperty(col4);
        this.col5 = new SimpleStringProperty(col5);
        this.col6 = new SimpleStringProperty(col6);
        this.col7 = new SimpleStringProperty(col7);
    }
    
    /**
     * Simple setter and getters. 
     */
    public CalendarRows (String col){
        this.col1 = new SimpleStringProperty(col);
    }

    public void setCol1(String data) {
        col1.set(data);
    }

    public String getCol1() {
        return col1.get();
    }

    public StringProperty col1Property() {
        return col1;
    }

    public void setCol2(String data) {
        col2.set(data);
    }

    public String getCol2() {
        return col2.get();
    }

    public StringProperty col2Property() {
        return col2;
    }

    public void setCol3(String data) {
        col3.set(data);
    }

    public String getCol3() {
        return col3.get();
    }

    public StringProperty col3Property() {
        return col3;
    }

    public void setCol4(String data) {
        col4.set(data);
    }

    public String getCol4() {
        return col4.get();
    }

    public StringProperty col4Property() {
        return col4;
    }

    public void setCol5(String data) {
        col5.set(data);
    }

    public String getCol5() {
        return col5.get();
    }

    public StringProperty col5Property() {
        return col5;
    }

    public void setCol6(String data) {
        col6.set(data);
    }

    public String getCol6() {
        return col6.get();
    }

    public StringProperty col6Property() {
        return col6;
    }

    public void setCol7(String data) {
        col7.set(data);
    }

    public String getCol7() {
        return col7.get();
    }

    public StringProperty col7Property() {
        return col7;
    }
    
    public String getWeek() {
        return col1.get() + " to " + col7.get();
    }

    //Hash
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.col1);
        hash = 59 * hash + Objects.hashCode(this.col2);
        hash = 59 * hash + Objects.hashCode(this.col3);
        hash = 59 * hash + Objects.hashCode(this.col4);
        hash = 59 * hash + Objects.hashCode(this.col5);
        hash = 59 * hash + Objects.hashCode(this.col6);
        hash = 59 * hash + Objects.hashCode(this.col7);
        return hash;
    }
    //equals
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
        final CalendarRows other = (CalendarRows) obj;
        if (!Objects.equals(this.col1, other.col1)) {
            return false;
        }
        if (!Objects.equals(this.col2, other.col2)) {
            return false;
        }
        if (!Objects.equals(this.col3, other.col3)) {
            return false;
        }
        if (!Objects.equals(this.col4, other.col4)) {
            return false;
        }
        if (!Objects.equals(this.col5, other.col5)) {
            return false;
        }
        if (!Objects.equals(this.col6, other.col6)) {
            return false;
        }
        if (!Objects.equals(this.col7, other.col7)) {
            return false;
        }
        return true;
    }
 
    //Simple toString
    @Override
    public String toString() {
        return "RowBean{" + "col1=" + col1 + ", col2=" + col2 + ", col3=" + col3 + ", col4=" + col4 + ", col5=" + col5 + ", col6=" + col6 + ", col7=" + col7 + '}';
    }

    
    
    
}
