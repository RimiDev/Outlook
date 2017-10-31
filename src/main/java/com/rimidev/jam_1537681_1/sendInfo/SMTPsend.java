package com.rimidev.jam_1537681_1.sendInfo;

import com.rimidev.jam_1537681_1.entities.SMTP;
import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;


/** 
 * Sending emails that have an alarm set to true to the default user.
 * @author Maxime Lacasse
 * @version 1.2
 */
public class SMTPsend {

    // Real programmers use logging
    private final Logger log = LoggerFactory.getLogger(getClass().getName()); 
    
    iAgendaDAO agenda = new AgendaDAO();
   
    /**
     * Standard send routine using Jodd.
     * @param apt
     * @throws SQLException
     */
    public void sendEmail(List<Appointment> apt) throws SQLException { 
        SMTP mail = grabDefaultMail();
        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(mail.getURL())
                .authenticateWith(mail.getEmail(), mail.getPassword());
        smtpServer.debug(true);
        
        for (int i = 0; i < apt.size(); i++){
            if (apt.get(i).getAlarm() == true){

        Email email = Email.create().from(mail.getEmail())
                .to(mail.getEmail())
                .subject(apt.get(i).getTitle()).addText(apt.get(i).getDetails());
        
            try ( 
                SendMailSession session = smtpServer.createSession()) {
                session.open();
                session.sendMail(email);
            } 
        
        } else {
            log.debug("No alarm set, no email");

        }
    }
} // end of send email.
       
    /**
     * Creating the appointments that will be used in the test
     * 3 regular appointments with reminder set to true -> should be sent to email.
     * 1 regular appointment with reminder set to false -> shouldn't be sent to email.
     * @throws SQLException 
     */
  public void createApts() throws SQLException{
        
        iAgendaDAO agenda = new AgendaDAO();
        
        int interval = agenda.findEmailByDefault(true).getReminder();
        
        LocalDateTime currentTimePlusInterval = LocalDateTime.now().plusMinutes(interval); //starttime
        LocalDateTime currentTimePlusInterval2 = LocalDateTime.now().plusMinutes(interval+interval); //endtime
      
        Appointment apt1 = new Appointment();
        apt1.setTitle("Washing dishes");
        apt1.setLocation("Home");
        apt1.setStartTime(Timestamp.valueOf(currentTimePlusInterval));
        log.debug("starttime1: " + apt1.getStartTime().toString());
        apt1.setEndTime(Timestamp.valueOf(currentTimePlusInterval2));
        apt1.setDetails("Oh lord..");
        apt1.setWholeDay(FALSE);
        apt1.setAppointmentGroup(1);
        apt1.setAlarm(TRUE);     
        
        Appointment apt2 = new Appointment();
        apt2.setTitle("Washing car");
        apt2.setLocation("Home");
        apt2.setStartTime(Timestamp.valueOf(currentTimePlusInterval));
        log.debug("starttime2: " + apt2.getStartTime().toString());
        apt2.setEndTime(Timestamp.valueOf(currentTimePlusInterval2));
        apt2.setDetails("Oh lord..");
        apt2.setWholeDay(FALSE);
        apt2.setAppointmentGroup(1);
        apt2.setAlarm(TRUE);
        
        Appointment apt3 = new Appointment();
        apt3.setTitle("Washing home");
        apt3.setLocation("Home");
        apt3.setStartTime(Timestamp.valueOf(currentTimePlusInterval));
        log.debug("starttime3: " + apt3.getStartTime().toString());
        apt3.setEndTime(Timestamp.valueOf(currentTimePlusInterval2));
        apt3.setDetails("Oh lord..");
        apt3.setWholeDay(FALSE);
        apt3.setAppointmentGroup(1);
        apt3.setAlarm(TRUE);
        
        //REMINDER WITH FALSE ALARM --> NO EMAIL
        Appointment apt4 = new Appointment();
        apt4.setTitle("Washing NO ALARM");
        apt4.setLocation("Home");
        apt4.setStartTime(Timestamp.valueOf(currentTimePlusInterval));
        log.debug("starttime4: " + apt4.getStartTime().toString());
        apt4.setEndTime(Timestamp.valueOf(currentTimePlusInterval2));
        apt4.setDetails("Oh lord..");
        apt4.setWholeDay(FALSE);
        apt4.setAppointmentGroup(1);
        apt4.setAlarm(FALSE);
          
        agenda.create(apt1);
         log.debug("apt1: " + apt1.getTitle());
        agenda.create(apt2);
        log.debug("apt2: " + apt2.getTitle());
        agenda.create(apt3);
        log.debug("apt3: " + apt3.getTitle());
        agenda.create(apt4);
        log.debug("apt4: " + apt4.getTitle());
            
    }
    
  
  /**
   * Finding all the test appointments that fall under the time range
   * of the test appointments and puts them into a List of appointments
   * to then send off to the sendEmail as a param.
   * @return List<Appointments>
   * @throws SQLException 
   */
    public List<Appointment> findAllApts() throws SQLException{
        iAgendaDAO agenda = new AgendaDAO();
        
        int interval = agenda.findEmailByDefault(true).getReminder();
        
        LocalDateTime currentTimePlusInterval = LocalDateTime.now().plusMinutes(interval); //starttime
        
        log.debug("ct: " + currentTimePlusInterval);
        
        Timestamp ctTs = Timestamp.valueOf(currentTimePlusInterval);
        
        log.debug("ctTs: " + ctTs);
        
        List<Appointment> mail = agenda.findAppointmentsFromStartBetween5Mins(ctTs);
        
        log.debug("mail size: " + mail.size());
        log.debug("mail0: " + mail.get(0).toString());
         log.debug("mail1: " + mail.get(1).toString());
         log.debug("mail2: " + mail.get(2).toString());
         log.debug("mail3: " + mail.get(3).toString());
   
        return mail;
    }
    
    
        public SMTP grabDefaultMail() throws SQLException {
        SMTP emailSender = agenda.findEmailByDefault(true);
        return emailSender;
    }
    

    
} // end of SMTPsend
