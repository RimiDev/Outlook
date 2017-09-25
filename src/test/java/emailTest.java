
import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * This is to test the sendEmail in the SMTPemail java class to see if it
 * will send the correct email with the correct appointment.
 * @author Maxime Lacasse
 * @version 1.2
 *
 */

public class emailTest {

    // Real programmers use logging
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    
    private final String url = "jdbc:mysql://localhost:3306/AGENDAdb?autoReconnect=true&useSSL=false";
    private final String user = "Rimi";
    private final String password = "RimBoy";

  
    /**
     * Testing the sendEmail in the SMTPsend java class.
     * @throws SQLException 
     */
    
    @Test(timeout=1000)
    public void testSendEmail() throws SQLException {
        //SMTPsend mail = new SMTPsend();
        SMTPsend m = new SMTPsend();
        createApts();
        List<Appointment> mail = findAllApts();
        m.sendEmail(mail);
  
    }
    
    
    /**
     * Creating the appointments that will be used in the test
     * 3 regular appointments with reminder set to true -> should be sent to email.
     * 1 regular appointment with reminder set to false -> shouldn't be sent to email.
     * @throws SQLException 
     */
  private void createApts() throws SQLException{
        
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
    private List<Appointment> findAllApts() throws SQLException{
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
     
    /**
     * This routine recreates the database before every test. This makes sure
     * that a destructive test will not interfere with any other test. Does not
     * support stored procedures.
     *
     * This routine is courtesy of Bartosz Majsak, the lead Arquillian developer
     * at JBoss
     */
    @Before
    public void seedDatabase() {
        log.info("Seeding Database");
        final String seedDataScript = loadAsString("CreateAgendaTables.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                log.debug("Statement= " + statement);
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * The following methods support the seedDatabase method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
    
}
