/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.jam_1537681_1.persistence;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.entities.Email;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * Interface for all procedures
 *
 * @author Max
 * @version 1.0
 */
public interface iAgendaDAO {

    //Create
    public int create(Email email) throws SQLException;

    public int create(Appointment appointment) throws SQLException;

    public int create(AppointmentGroup appointmentGroup) throws SQLException;

    //Read
    public List<Email> findAllEmails() throws SQLException;

    public Email findEmail(String name) throws SQLException;
    
    public Email findEmailByDefault(Boolean isDefault) throws SQLException;

    public List<Appointment> findAllAppointments() throws SQLException;

    public Appointment findAppointmentbyId(int id) throws SQLException;

    public Appointment findAppointmentbyTitle(String title) throws SQLException;

    public List<Appointment> findAppointmentByStartTime(Timestamp starttime) throws SQLException;
    
    public List<Appointment> findAppointmentsFromStartBetween5Mins(Timestamp starttime) throws SQLException;

    public Appointment findAppointmentByEndTime(Timestamp endtime) throws SQLException;
    
    public List<Appointment> findAppointmentForADay(LocalDate day) throws SQLException;
    
    public List<Appointment> findAppointmentForAWeek(LocalDate day) throws SQLException;
    
    public List<Appointment> findAppointmentForAMonth(LocalDate monthStart) throws SQLException;

    public List<AppointmentGroup> findAllAppointmentGroups() throws SQLException;

    public AppointmentGroup findAppointmentGroup(int groupNumber) throws SQLException;

    //Update
    public int update(Email email) throws SQLException;

    public int update(Appointment appointment) throws SQLException;

    public int update(AppointmentGroup appointmentGroup) throws SQLException;

    //Delete
    public int deleteEmail(String name) throws SQLException;

    public int deleteAppointmentbyId(int id) throws SQLException;

    public int deleteAppointmentbyTitle(String title) throws SQLException;

    public int deleteAppointmentGroup(int groupNumber) throws SQLException;

}
