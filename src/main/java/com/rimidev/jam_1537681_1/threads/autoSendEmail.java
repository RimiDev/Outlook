package com.rimidev.jam_1537681_1.threads;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import com.rimidev.jam_1537681_1.sendInfo.SMTPsend;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to thread a task for sending automatic email every minute
 * about the upcoming appointments within a 2 hour range.
 * @author Max
 * @version 1.5
 */
public class autoSendEmail {
    
    
    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());
    
        Timer timer = new Timer();
        SMTPsend emailSend = new SMTPsend();
        iAgendaDAO agenda = new AgendaDAO();
    
    public autoSendEmail(){
    }
   
    /**
     * Sends an email automically every minute about appointments in the next
     * 2 hour range.
     */
    public void sendAutoEmail(){
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                try {
                List<Appointment> apts;
                apts = agenda.findAppointmentsFromStartBetween2Hours(LocalDateTime.now());
                emailSend.sendEmail(apts);
                } catch (SQLException e){
                    log.debug("Problem with sendEmail: SQLException --> Cannot find apts?");
                }
            }
        };
        //Sends a reminder email of appointsments in 2 hour range --> 1 minute intervals, annoying???
        timer.scheduleAtFixedRate(task, 60000, 60000);
        
    }
    
    public void cancelSend(){
        timer.cancel();
    }
    
}
