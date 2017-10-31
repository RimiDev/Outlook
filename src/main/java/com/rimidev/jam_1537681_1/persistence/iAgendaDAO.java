package com.rimidev.jam_1537681_1.persistence;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.entities.SMTP;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;

/**
 *
 * Interface for all the bean CRUD.
 * @author Maxime Lacasse
 * @version 1.5
 */
public interface iAgendaDAO {

    //Create
    public int create(SMTP email) throws SQLException;

    public int create(Appointment appointment) throws SQLException;

    public int create(AppointmentGroup appointmentGroup) throws SQLException;

    //Read
    public List<SMTP> findAllEmails() throws SQLException;

    public SMTP findEmail(String name) throws SQLException;
    
    public SMTP findEmailById(int id) throws SQLException;
    
    public SMTP findEmailByDefault(Boolean isDefault) throws SQLException;

    public List<Appointment> findAllAppointments() throws SQLException;

    public Appointment findAppointmentbyId(int id) throws SQLException;

    public Appointment findAppointmentbyTitle(String title) throws SQLException;

    public List<Appointment> findAppointmentByStartTime(Timestamp starttime) throws SQLException;
    
    public List<Appointment> findAppointmentByStartTime(LocalTime starttime ) throws SQLException;
    
    public List<Appointment> findAppointmentsFromStartBetween5Mins(Timestamp starttime) throws SQLException;
    
    public List<Appointment> findAppointmentsFromStartBetween29Mins(Timestamp starttime) throws SQLException;
    
    public List<Appointment> findAppointmentsFromStartBetween2Hours(LocalDateTime time) throws SQLException;

    public Appointment findAppointmentByEndTime(Timestamp endtime) throws SQLException;
    
    public List<Appointment> findAppointmentForADay(LocalDate day) throws SQLException;
    
    public List<Appointment> findAppointmentForAWeek(LocalDate day) throws SQLException;
    
    public List<Appointment> findAppointmentForAMonth(LocalDate monthStart) throws SQLException;

    public List<AppointmentGroup> findAllAppointmentGroups() throws SQLException;

    public AppointmentGroup findAppointmentGroup(int groupNumber) throws SQLException;

    //Update
    public int update(SMTP email) throws SQLException;

    public int update(Appointment appointment) throws SQLException;

    public int update(AppointmentGroup appointmentGroup) throws SQLException;

    //Delete
    public int deleteEmail(String name) throws SQLException;
    
    public int deleteEmailById(int id) throws SQLException;

    public int deleteAppointmentbyId(int id) throws SQLException;

    public int deleteAppointmentbyTitle(String title) throws SQLException;

    public int deleteAppointmentGroup(int groupNumber) throws SQLException;
    
    //Helper methods --> for daily view.
    public int maxApts() throws SQLException;
    public int minApts() throws SQLException;
    public int maxAptGrps() throws SQLException;
    public int minAptGrps() throws SQLException; 
    public int maxEmails() throws SQLException;
    public int minEmails() throws SQLException;
    
    //Accessing properties files
    public Properties getDBcredits();

}
