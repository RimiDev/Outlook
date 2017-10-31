/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.persistence;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.entities.SMTP;
import java.io.FileInputStream;
import static java.lang.Boolean.FALSE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the iAgendaDAO interface!
 * The functionality of the Agenda Database.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class AgendaDAO implements iAgendaDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    Properties dbCreds = this.getDBcredits();

    private final String url = dbCreds.getProperty("url");
    private final String user = dbCreds.getProperty("user");
    private final String password = dbCreds.getProperty("password");

    //Default Constructor
    public AgendaDAO() {
        super();
    }

    //CREATE--------------------------------------------------------------------
    //----EMAIL----- 
    /**
     * Creates a record with the SMTP object's values
     * @param email
     * @return records -> 1 - row created || 0 - row not created
     * @throws SQLException 
     */
    @Override
    public int create(SMTP email) throws SQLException {
        int records;

        String query = "INSERT INTO EMAIL (UNAME,EMAIL,PASSWORD,URL,PORT,ISDEFAULT, REMINDER) values"
                + "(?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, email.getName());
            ps.setString(2, email.getEmail());
            ps.setString(3, email.getPassword());
            ps.setString(4, email.getURL());
            ps.setInt(5, email.getPort());
            ps.setBoolean(6, email.getIsDefault());
            ps.setInt(7, email.getReminder());
            records = ps.executeUpdate();
        }
        return records;
    }

    //----APPOINTMENT----
     /**
     * Creates a record with the Appointment object's values
     * @param appointment
     * @return records -> 1 - row created || 0 - row not created
     * @throws SQLException 
     */
    @Override
    public int create(Appointment appointment) throws SQLException {
        int records;

        String query = "INSERT INTO APPOINTMENT (TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM) values (?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getLocation());
            ps.setTimestamp(3, appointment.getStartTime());
            ps.setTimestamp(4, appointment.getEndTime());
            ps.setString(5, appointment.getDetails());
            ps.setBoolean(6, appointment.getWholeDay());
            ps.setInt(7, appointment.getAppointmentGroup());
            ps.setBoolean(8, appointment.getAlarm());
            records = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int recordId = -1;
            if (rs.next()) {
                recordId = rs.getInt(1);
            }
            appointment.setId(recordId);
        }
        return records;
    }

    //----APPOINTMENTGROUP---
    /**
     * Creates a record with the AppointmentGroup object's values
     * @param appointmentGroup
     * @return records -> 1 - row created || 0 - row not created
     * @throws SQLException 
     */
    @Override
    public int create(AppointmentGroup appointmentGroup) throws SQLException {
        int records;

        String query = "INSERT INTO APPOINTMENTGROUP (GROUPNAME,COLOR)"
                + "values (?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, appointmentGroup.getGroupName());
            ps.setString(2, appointmentGroup.getColor());
            records = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int recordId = -1;
            if (rs.next()) {
                recordId = rs.getInt(1);
            }
            appointmentGroup.setGroupNumber(recordId);
        }
        return records;
    }

    //READ----------------------------------------------------------------------
    //----EMAIL-----
    /**
     * Finds all the SMTP objects from the database and puts it into a List.
     * @return List<Email>
     * @throws SQLException 
     */
    @Override
    public List<SMTP> findAllEmails() throws SQLException {
        List<SMTP> emails = new ArrayList<>();

        String query = "SELECT ID,UNAME,EMAIL,PASSWORD,URL,PORT,ISDEFAULT,REMINDER FROM EMAIL";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { //Creating new email objects and placing into collection
                SMTP email = makeEmail(rs);
                emails.add(email);
            }
        }
        return emails;
    } // End of findAllEmails

    /**
     * Finds an SMTP object with the specified name.
     * @param name
     * @return SMTP
     * @throws SQLException 
     */
    @Override
    public SMTP findEmail(String name) throws SQLException {
        SMTP email = new SMTP();

        String query = "SELECT UNAME,EMAIL,PASSWORD,URL,PORT,ISDEFAULT,REMINDER FROM EMAIL "
                + "WHERE UNAME=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = makeEmail(rs);
                }

            }
        }
        return email;

    }
    
    /**
     * Finds an email by a given Id.
     * @param id
     * @return SMTP
     * @throws SQLException 
     */
    @Override
    public SMTP findEmailById(int id) throws SQLException {
        SMTP email = new SMTP();
        
        String query = "SELECT ID,UNAME,EMAIL,PASSWORD,URL,PORT,ISDEFAULT,REMINDER FROM EMAIL "
                + "WHERE ID =?";
        
        try (Connection conn = DriverManager.getConnection(url,user,password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1,id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = makeEmail(rs);
                }
            }
        }
        return email;
    }
    
    /**
     * Finds an email by the boolean isDefault.
     * @param isDefault
     * @return SMTP
     * @throws SQLException 
     */
    @Override
    public SMTP findEmailByDefault(Boolean isDefault) throws SQLException {
        SMTP email = new SMTP();
        
        String query = "SELECT ID,UNAME,EMAIL,PASSWORD,URL,PORT,ISDEFAULT,REMINDER FROM EMAIL "
                + "WHERE ISDEFAULT=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setBoolean(1, isDefault);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = makeEmail(rs);
                }

            }
        }
        return email;       
    }

    //----APPOINTMENT----
    /**
     * Finds all the Appointment objects from the database and puts it into a List
     * @return List<Appointment>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAllAppointments() throws SQLException {
        List<Appointment> apts = new ArrayList<>();

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        }
        return apts;
    }

    /**
     * Finds the Appointment object with the specified id
     * @param id
     * @return Appointment
     * @throws SQLException 
     */
    @Override
    public Appointment findAppointmentbyId(int id) throws SQLException {
        Appointment apt = new Appointment();

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE ID=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
         
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    apt = makeApt(rs);
                }

            }
        }
        return apt;
    }

    /**
     * Finds the Appointment object with the specified title
     * @param title
     * @return Appointment
     * @throws SQLException 
     */
    @Override
    public Appointment findAppointmentbyTitle(String title) throws SQLException {
        Appointment apt = new Appointment();

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE TITLE=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    apt = makeApt(rs);
                }

            }
        }
        return apt;
    }

    /**
     * Finds the Appointment object with the specified start time
     * @param starttime
     * @return Appointment
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentByStartTime(Timestamp starttime) throws SQLException {
        List<Appointment> apts = new ArrayList<>();

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE STARTTIME=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, starttime);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
    }
  }
    
    /**
     * Finds 1 or more appointments depending on the starttime.
     * @param starttime
     * @return List<Email>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentByStartTime(LocalTime starttime) throws SQLException {
        List<Appointment> apts = new ArrayList<>();
        
        Timestamp time = Timestamp.valueOf(String.valueOf(starttime));

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE STARTTIME=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {           
            ps.setTimestamp(1, time);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
    }
    }
    /**
     * Finds the Appointment object with the specified end time
     * @param endtime
     * @return Appointment
     * @throws SQLException 
     */
    @Override
    public Appointment findAppointmentByEndTime(Timestamp endtime) throws SQLException {
        Appointment apt = new Appointment();

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE ENDTIME=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, endtime);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    apt = makeApt(rs);
                }

            }
        }
        return apt;

    }
    
    /**
     * Finds 1 or more appointments depending on the 5-startTime AND 5+startTime
     * Used for SMTPsend incase of time errors.
     * @param starttime
     * @return List<Email>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentsFromStartBetween5Mins(Timestamp starttime) throws SQLException {
        List<Appointment> apts = new ArrayList<>();
        
        Timestamp plus5 = Timestamp.valueOf(starttime.toLocalDateTime().plusMinutes(5));
        Timestamp minus5 = Timestamp.valueOf(starttime.toLocalDateTime().minusMinutes(5));

        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE STARTTIME BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, minus5);
            ps.setTimestamp(2, plus5);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
        }
    }
    
    /**
     * Finds 1 or more appointments depending on startTime AND startTime+29
     * Used in daily view.
     * @param starttime
     * @return
     * @throws SQLException 
     */
     @Override
    public List<Appointment> findAppointmentsFromStartBetween29Mins(Timestamp starttime) throws SQLException {
        List<Appointment> apts = new ArrayList<>();
        
        Timestamp plus29 = Timestamp.valueOf(starttime.toLocalDateTime().plusMinutes(29));
        
        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE STARTTIME BETWEEN ? AND ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, starttime);
            ps.setTimestamp(2, plus29);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
        }
    }
    /**
     * For the autoSendEmail thread that automatically sends emails to the default email
     * according to LocalDateTime.now() TO LocalDateTime.now().plusHours(2);
     * @param time
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentsFromStartBetween2Hours(LocalDateTime time) throws SQLException {
        List<Appointment> apts = new ArrayList<>();       
        
        Timestamp plus2hrs = Timestamp.valueOf(time.plusHours(2));
        
        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE STARTTIME BETWEEN ? AND ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, Timestamp.valueOf(time));
            ps.setTimestamp(2, plus2hrs);
            ResultSet rs = ps.executeQuery();
            
            
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
        }
    }
    
    /**
     * Finds all the Appointments for a specified day
     * @param day
     * @return List<Appointment>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentForADay(LocalDate day) throws SQLException {
        String startofDay = "00:00:00";
        String endofDay = "23:59:59";
        
        LocalDateTime LDTstartOfDay = LocalDateTime.of(day,LocalTime.parse(startofDay));
        LocalDateTime LDTendOfDay = LocalDateTime.of(day, LocalTime.parse(endofDay));
        Timestamp tsStartofDay = Timestamp.valueOf(LDTstartOfDay);
        Timestamp tsEndofDay = Timestamp.valueOf(LDTendOfDay);
        

        List<Appointment> apts = new ArrayList<>();
        
        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE "
                + "((STARTTIME BETWEEN ? AND ?) AND (ENDTIME BETWEEN ? AND ?))";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, tsStartofDay);
            ps.setTimestamp(2, tsEndofDay);
            ps.setTimestamp(3, tsStartofDay);
            ps.setTimestamp(4, tsEndofDay);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
    }
    }
    
    /**
     * Finds all the Appointments for a specified week
     * @param weekStart
     * @return List<Appointment>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentForAWeek(LocalDate weekStart) throws SQLException {
        String startofDay = "00:00:00";
        String endofDay = "23:59:59";
        
        LocalDateTime LDTstartOfWeek = LocalDateTime.of(weekStart,LocalTime.parse(startofDay));
        LocalDateTime LDTendOfWeek = LocalDateTime.of(weekStart, LocalTime.parse(endofDay));
        LDTendOfWeek = LDTendOfWeek.plusDays(7);
        Timestamp tsStartofWeek = Timestamp.valueOf(LDTstartOfWeek);
        Timestamp tsEndofWeek = Timestamp.valueOf(LDTendOfWeek);      
        

        List<Appointment> apts = new ArrayList<>();
        
        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE "
                + "STARTTIME BETWEEN ? AND ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, tsStartofWeek);
            ps.setTimestamp(2, tsEndofWeek);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
    }
    }
    
    /**
     * Finds all the Appointments for a specified month
     * @param monthStart
     * @return List<Appointment>
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAppointmentForAMonth(LocalDate monthStart) throws SQLException {
        String startofDay = "00:00:00";
        String endofDay = "23:59:59";
        
        LocalDateTime LDTstartOfMonth = LocalDateTime.of(monthStart,LocalTime.parse(startofDay));
        LocalDate EndOfMonth = monthStart.with(lastDayOfMonth());
        LocalDateTime LDTendOfMonth = LocalDateTime.of(EndOfMonth, LocalTime.parse(endofDay));


        Timestamp tsStartofMonth = Timestamp.valueOf(LDTstartOfMonth);
        Timestamp tsEndofMonth = Timestamp.valueOf(LDTendOfMonth);
        
        

        List<Appointment> apts = new ArrayList<>();
        
        String query = "SELECT ID,TITLE,LOCATION,STARTTIME,ENDTIME,DETAILS,WHOLEDAY,"
                + "APPOINTMENTGROUP,ALARM FROM APPOINTMENT WHERE "
                + "STARTTIME BETWEEN ? AND ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setTimestamp(1, tsStartofMonth);
            ps.setTimestamp(2, tsEndofMonth);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //Creating new Appointment objects and placing into collection
                Appointment apt = makeApt(rs);
                apts.add(apt);
            }
        
        return apts;        
    }
    }

    //----APPOINTMENTGROUP----
    /**
     * Finds all the AppointmentGroup from the database and puts it into a List
     * @return List<AppointmentGroup>
     * @throws SQLException 
     */
    @Override
    public List<AppointmentGroup> findAllAppointmentGroups() throws SQLException {
        List<AppointmentGroup> aptGroups = new ArrayList<>();

        String query = "SELECT GROUPNUMBER, GROUPNAME, COLOR FROM APPOINTMENTGROUP";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppointmentGroup aptGroup = makeAptGroup(rs);
                aptGroups.add(aptGroup);
            }

        }
        return aptGroups;
    }

    /**
     * Finds an AppointmentGroup with a specific groupNumber.
     * @param groupNumber
     * @return AppointmentGroup
     * @throws SQLException 
     */
    @Override
    public AppointmentGroup findAppointmentGroup(int groupNumber) throws SQLException {
        AppointmentGroup aptGroup = new AppointmentGroup();

        String query = "SELECT GROUPNUMBER, GROUPNAME, COLOR FROM APPOINTMENTGROUP"
                + " WHERE GROUPNUMBER=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, groupNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aptGroup = makeAptGroup(rs);
                }

            }
        }
        return aptGroup;
    }

    //UUPDATE-------------------------------------------------------------------
    //----EMAIL-----
    /**
     * Updates an email that is already in the database
     * @param email
     * @return records -> 1 - row updated || 0 - row not updated
     * @throws SQLException 
     */
    @Override
    public int update(SMTP email) throws SQLException {
        int records;

        String query = "UPDATE EMAIL SET EMAIL = ?, PASSWORD = ?,"
                + "URL = ?, PORT = ?, ISDEFAULT = ?, REMINDER = ? WHERE UNAME = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, email.getEmail());
            ps.setString(2, email.getPassword());
            ps.setString(3, email.getURL());
            ps.setInt(4, email.getPort());
            ps.setBoolean(5, FALSE);
            ps.setInt(6, email.getReminder());
            ps.setString(7, email.getName());


            records = ps.executeUpdate();
        } catch (SQLException ex){
            log.debug("update email expection");
            throw ex;
        }
        return records;
    }

    //----APPOINTMENT----
    /**
     * Updates an Appointment that is already in the database
     * @param appointment
     * @return records -> 1 - row updated || 0 - row not updated
     * @throws SQLException 
     */
    @Override
    public int update(Appointment appointment) throws SQLException {
        int records;

        String query = "UPDATE APPOINTMENT SET TITLE = ?, LOCATION = ?,"
                + "STARTTIME = ?, ENDTIME = ?, DETAILS = ?, WHOLEDAY = ?,"
                + "APPOINTMENTGROUP = ?, ALARM = ?"
                + " WHERE ID = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getLocation());
            ps.setTimestamp(3, appointment.getStartTime());
            ps.setTimestamp(4, appointment.getEndTime());
            ps.setString(5, appointment.getDetails());
            ps.setBoolean(6, appointment.getWholeDay());
            ps.setInt(7, appointment.getAppointmentGroup());
            ps.setBoolean(8, appointment.getAlarm());
            ps.setInt(9, appointment.getId());
            records = ps.executeUpdate();
        } 
        catch(SQLException ex) {
            log.debug("Exception thrown");
            throw ex;
        }
        return records;
    }

    //----APPOINTMENTGROUP----
    /**
     * Updates an AppointmentGroup that is already in the database
     * @param appointmentGroup
     * @return records -> 1 - row updated || 0 - row not updated
     * @throws SQLException 
     */
    @Override
    public int update(AppointmentGroup appointmentGroup) throws SQLException {
        int records;

        String query = "UPDATE APPOINTMENTGROUP SET GROUPNAME = ?,"
                + "COLOR = ? WHERE GROUPNUMBER = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, appointmentGroup.getGroupName());
            ps.setString(2, appointmentGroup.getColor());
            ps.setInt(3, appointmentGroup.getGroupNumber());
            records = ps.executeUpdate();
        }
        return records;
    }

    //DELETE--------------------------------------------------------------------
    //----EMAIL----
    /**
     * Deletes a SMTP record in the database
     * @param name
     * @return records -> 1 - row deleted || 0 - row not deleted
     * @throws SQLException 
     */
    @Override
    public int deleteEmail(String name) throws SQLException {
        int records;
        
        String query = "DELETE FROM EMAIL WHERE UNAME=?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, name);

            records = ps.executeUpdate();
  
        }
            return records;
    }
    
    /**
     * Deletes an email by the given Id.
     * @param id
     * @return records
     * @throws SQLException 
     */
    @Override
    public int deleteEmailById(int id) throws SQLException {
        int records; 
        
        String query = "DELETE FROM EMAIL WHERE ID=?";
        
        try (Connection conn = DriverManager.getConnection(url,user,password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
            records = ps.executeUpdate();
        }
        return records;
    }

    //----APPOINTMENT----
    /**
     * Deletes an Appointment in the database specified by the Id.
     * @param id
     * @return records -> 1 - row deleted || 0 - row not deleted
     * @throws SQLException 
     */
    @Override
    public int deleteAppointmentbyId(int id) throws SQLException {
        int records;
        
        String query = "DELETE FROM APPOINTMENT WHERE ID=?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
            records = ps.executeUpdate();
        }
        return records;
    }

    /**
     * Delete an Appointment in the database specified by the title
     * @param title
     * @return records -> 1 - row deleted || 0 - row not deleted
     * @throws SQLException 
     */
    @Override
    public int deleteAppointmentbyTitle(String title) throws SQLException {
        int records;
        
        String query = "DELETE FROM APPOINTMENT WHERE TITLE=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, title);

            records = ps.executeUpdate();
        }
        return records;
    }

    //----APPOINTMENTGROUP----
    /**
     * Deletes an AppointmentGroup in the database specified by the groupNumber
     * @param groupNumber
     * @return records -> 1 - row deleted || 0 - row not deleted
     * @throws SQLException 
     */
    @Override
    public int deleteAppointmentGroup(int groupNumber) throws SQLException {
        int records;
        
        String query = "DELETE FROM APPOINTMENTGROUP WHERE GROUPNUMBER=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, groupNumber);

            records = ps.executeUpdate();
        }
        return records;
    }
    
    
    /**
     * SMTP object creator
     * @param rs
     * @return SMTP
     * @throws SQLException 
     */
    private SMTP makeEmail(ResultSet rs) throws SQLException {
        SMTP email = new SMTP();
        email.setId(rs.getInt("id"));
        email.setName(rs.getString("UNAME"));
        email.setEmail(rs.getString("EMAIL"));
        email.setPassword(rs.getString("PASSWORD"));
        email.setURL(rs.getString("URL"));
        email.setPort(rs.getInt("PORT"));
        email.setIsDefault(rs.getBoolean("ISDEFAULT"));
        email.setReminder(rs.getInt("REMINDER"));
        return email;
    }

    /**
     * Appointment object creator
     * @param rs
     * @return Appointment
     * @throws SQLException 
     */
    private Appointment makeApt(ResultSet rs) throws SQLException {
        Appointment apt = new Appointment();
        apt.setId(rs.getInt("ID"));
        apt.setTitle(rs.getString("TITLE"));
        apt.setLocation(rs.getString("LOCATION"));
        apt.setStartTime(rs.getTimestamp("STARTTIME"));
        apt.setEndTime(rs.getTimestamp("ENDTIME"));
        apt.setDetails(rs.getString("DETAILS"));
        apt.setWholeDay(rs.getBoolean("WHOLEDAY"));
        apt.setAppointmentGroup(rs.getInt("APPOINTMENTGROUP"));
        apt.setAlarm(rs.getBoolean("ALARM"));
        return apt;
    }

    /**
     * AppointmentGroup object creator
     * @param rs
     * @return AppointmentGroup
     * @throws SQLException 
     */
    private AppointmentGroup makeAptGroup(ResultSet rs) throws SQLException {
        AppointmentGroup aptGroup = new AppointmentGroup();
        aptGroup.setGroupNumber(rs.getInt("GROUPNUMBER"));
        aptGroup.setGroupName(rs.getString("GROUPNAME"));
        aptGroup.setColor(rs.getString("COLOR"));
        return aptGroup;
    }
      
    /*
    * Helper methods used for Next/Prev for all the forms.
    * Finding the min and max of all the beans, so that the program cannot 
    * proceed to next/prev if there is no approriate record to go with it.
    */
    @Override
    public int maxApts() throws SQLException {
        List<Appointment> apts = new ArrayList<>();
        apts = this.findAllAppointments();
        return apts.size()+1;
    }
    @Override
    public int minApts() throws SQLException {
        List<Appointment> apts = new ArrayList<>();
        apts = this.findAllAppointments();
        return apts.get(0).getId()-1;
    }
    @Override
    public int maxAptGrps() throws SQLException {
        List<AppointmentGroup> apts = new ArrayList<>();
        apts = this.findAllAppointmentGroups();
        return apts.size()+1;
    }
    @Override
    public int minAptGrps() throws SQLException {
        List<AppointmentGroup> apts = new ArrayList<>();
        apts = this.findAllAppointmentGroups();
        return apts.get(0).getGroupNumber()-1;
    }
    @Override
    public int maxEmails() throws SQLException {
        List<SMTP> emails = new ArrayList<>();
        emails = this.findAllEmails();
        return emails.size()+1;   
    }
    
    @Override
    public int minEmails() throws SQLException {
        List<SMTP> emails = new ArrayList<>();
        emails = this.findAllEmails();
        return emails.get(0).getId()-1;
    }
    /**
     * Accessing AgendaDB credientials. 
     * I put this in here because most of the classes that call the database
     * include this class, thus easier to call method here than create a new method
     * in every single class that needs db credientials.
     * @return 
     */
    @Override
    public Properties getDBcredits(){
        Properties dbCreds = new Properties();
        try {
        FileInputStream in = new FileInputStream("src/main/resources/dbCredits.properties");
        dbCreds.load(in);
        in.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
        return dbCreds;
    }
    

} // end of AgendaDAO
