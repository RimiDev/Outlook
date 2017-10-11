
import com.rimidev.jam_1537681_1.entities.Appointment;

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
 *
 */
public class SMTPsend {

    // Real programmers use logging
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    private final String smtpServerName = "smtp.gmail.com";
    //private final String imapServerName = "imap.gmail.com";
    private final String emailSend = "JAM1537681@gmail.com";
    private final String emailSendPwd = "JAMproject";
    private final String emailReceive = "JAM1537681@gmail.com";
    //private final String emailReceivePwd = "JAMproject";
    //private final String emailCC1 = "";
    //private final String emailCC2 = "";

    // You will need a folder with this name or change it to another
    // existing folder
    //private final String attachmentFolder = "C:\\Temp\\Attach\\";

    /**
     * Standard send routine using Jodd. Jodd knows about GMail so no need to
     * include port information
     * @param apt
     */
    public void sendEmail(List<Appointment> apt) {

        
        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(smtpServerName)
                .authenticateWith(emailSend, emailSendPwd);

        // Display Java Mail debug conversation with the server
        smtpServer.debug(true);
        
        log.debug("apt.size " + apt.size());
        
        for (int i = 0; i < apt.size(); i++){
            if (apt.get(i).getAlarm() == true){
                   log.debug("reminder |" + i + "| passed");

        // Using the fluent style of coding create a plain text message
        Email email = Email.create().from(emailSend)
                .to(emailReceive)
                .subject(apt.get(i).getTitle()).addText(apt.get(i).getDetails());

            // Like a file we open the session, send the message and close the
            // session
            try ( // A session is the object responsible for communicating with the server
                    SendMailSession session = smtpServer.createSession()) {
                // Like a file we open the session, send the message and close the
                // session
                session.open();
                session.sendMail(email);
            } 
        
        } else {
                   log.debug("No alarm set, no email");

        }
    }
}




    
}
