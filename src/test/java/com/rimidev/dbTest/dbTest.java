package com.rimidev.dbTest;

import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.Email;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for AgendaDB database
 *
 * @Maxime Lacasse
 * @version 1.2
 */

@Ignore
public class dbTest {
    
    //Hard-coded parameters for the connection to the MySql database
    private final String url = "jdbc:mysql://localhost:3306/AGENDAdb?autoReconnect=true&useSSL=false";
    private final String user = "Rimi";
    private final String password = "RimBoy";
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());

    //TESTS---------------------------------------------------------------------
    //----EMAIL----
    @Test
    public void testEmailCreate() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();

        Email email = new Email();
        email.setName("TestName");
        email.setEmail("TestEmail@gmail.com");
        email.setPassword("TestPassword");
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        int records = agenda.create(email);

        Email emailTest = agenda.findEmail(email.getName());

        assertEquals("A record was created", email, emailTest);

    } // end of testEmailCreate()

    @Test (timeout = 1000, expected = SQLException.class)
    public void testEmailCreateWithOneNullParam() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email = new Email();
        email.setName("TestNameFailure");
        email.setEmail("TestEmailFailure@gmail.com");
        email.setPassword(null); //Should not get past this.
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        agenda.create(email);
       
        // If an exception was not thrown then the test failed
        fail("Password is not set to null --> no expections for some strange reason");
    }
    
    @Test
    public void testFindAllEmails() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        List<Email> email = agenda.findAllEmails();

        assertEquals("# of Emails", 3, email.size());
    }
    
    @Test
    public void testFindEmail() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email = new Email();
        email.setName("TestName");
        email.setEmail("TestEmailUpdate@gmail.com");
        email.setPassword("TestNameUpdatePassword");
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        agenda.create(email);
        
        Email emailTest = new Email();
        emailTest = agenda.findEmail("TestName");
        
        assertEquals("Emails matched", email, emailTest);
        
    }
    
     @Test 
    public void testFindEmailByDefault() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email =  new Email();
        email.setName("Max Lacasse");
        email.setEmail("JAM1537681@gmail.com");
        email.setPassword("JAMproject");
        email.setURL("smtp.gmail.com");
        email.setPort(587);
        email.setIsDefault(TRUE);
        email.setReminder(120);
        
        Email emailTest = new Email();
        emailTest = agenda.findEmailByDefault(TRUE);
        
        assertEquals("Email with default true found", email, emailTest);
  
    }
    
    
    
    @Test 
    public void testUpdateEmail() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email =  new Email();
        email.setName("Sebastian Gregoire");
        email.setEmail("TestEmailUpdate@gmail.com");
        email.setPassword("TestNameUpdatePassword");
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        
        int records = agenda.update(email);
        
        assertEquals("return of 1 is success", 1, records);
  
    }
    
    @Test (timeout = 1000, expected = SQLException.class)
    public void testUpdateEmailWithTooLongEmail() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email =  new Email();
        email.setName("Sebastian Gregoire"); 
        email.setEmail("THISEMAILISGOINGTOBEWAYTOLONGIMJUSTTYPINGUNTILICANTANYMORE@GMAIL.COM");
        email.setPassword("TestNameUpdatePassword");
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        
        int records = agenda.update(email);
        
        //If an expection was not thrown then the test failed.
        fail("The email is far too long");
    }
    
    @Test
    public void testDeleteEmail() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Email email = new Email();
        email.setName("Sebby Brown"); 
        email.setEmail("sebby@gmail.com");
        email.setPassword("TestNameUpdatePassword");
        email.setURL("smtp.gmail.com");
        email.setPort(123);
        email.setIsDefault(FALSE);
        email.setReminder(1);
        agenda.create(email);
         
        int records = agenda.deleteEmail("Sebby Brown");
        
        assertEquals("Return of 1 is success", 1, records);   
    }
    
    //----APPOINTMENTS----------------------------------------------------------    
    @Test
    public void testAppointmentCreate() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing Dishes");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:00"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        log.debug("record: " + records);
        
        
        assertEquals("A record was created", 1, records);    
    }
    
    @Test (timeout = 1000, expected = SQLException.class)
    public void testAppointmentCreateWithOneNullParam() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle(null); // Should not get past this!
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:00"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        fail("The record was not created as it has a null field");
    }
    
    @Test
    public void testFindAllAppointments() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        List<Appointment> apts = agenda.findAllAppointments();
        
        assertEquals("# of appointments", 50, apts.size());
    }
    
    @Test
    public void testFindAppointmentById() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing Dishes");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:00"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        Appointment aptTest = agenda.findAppointmentbyId(51);
        
        assertEquals("Appointment has been found!", apt, aptTest);
        
    }
    
    @Test
    public void testFindAppointmentByTitle() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing Dishes");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:00"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        Appointment aptTest = agenda.findAppointmentbyTitle("Washing Dishes");
        
        assertEquals("Appointments matched", apt, aptTest);
    }

    
    @Test
    public void testFindAppointByStartTime() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing Dishes");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:01"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:01"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        log.debug("records: " + records);
        
        List<Appointment> aptTest = agenda.findAppointmentByStartTime((Timestamp.valueOf("2017-07-07 12:00:01")));
        
        log.debug("aptTest1: " + aptTest.get(0));
        log.debug("aptTest size: " + aptTest.size());
        
        assertEquals("Appointments matched", apt, aptTest.get(0));
    }
    
    
    @Test
    public void testFindAppointmentByEndTime() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing Dishes");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-07-07 12:00:01"));
        apt.setEndTime(Timestamp.valueOf("2017-07-07 12:05:01"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        Appointment aptTest = agenda.findAppointmentByEndTime((Timestamp.valueOf("2017-07-07 12:05:01")));
        
        assertEquals("Appointments matched", apt, aptTest);
    }
    
    @Test
    public void testFindAppointmentByStartTimeBetween5Mins() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Washing");
        apt.setLocation("Home");
        apt.setStartTime(Timestamp.valueOf("2017-01-09 10:30:00"));
        apt.setEndTime(Timestamp.valueOf("2017-01-09 10:35:00"));
        apt.setDetails("Oh lord...");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(4);
        apt.setAlarm(TRUE);
        
        int records = agenda.create(apt);
        
        log.debug("records: " + records);
        
        Timestamp time = Timestamp.valueOf("2017-01-09 10:31:00");
        
        List<Appointment> aptTest = agenda.findAppointmentsFromStartBetween5Mins(time);
        
        
        log.debug("aptTest1: " + aptTest.get(0));
        log.debug("aptTest size: " + aptTest.size());
        
        assertEquals("Appointments matched", apt, aptTest.get(0));
    }
    
    @Test
    public void testFindAppointmentByDay() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String day = "2017-09-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(day, formatter);

        List<Appointment> apts = agenda.findAppointmentForADay(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());
        log.info("apt 3 " + apts.get(2).getTitle());
        log.info("apt 4: " + apts.get(3).getTitle());
        
        assertEquals("Appointments found for the day!", 4 , apts.size());
    }
    
    @Test(timeout=1000, expected=DateTimeParseException.class)
    public void testFindAppointmentByWrongFormatDay() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String day = "01-2017-02";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(day, formatter);

        List<Appointment> apts = agenda.findAppointmentForADay(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());
        log.info("apt 3 " + apts.get(2).getTitle());
        log.info("apt 4: " + apts.get(3).getTitle());
        
        fail("The record was not found because of bad date format.");
    }
    
    @Test
    public void testFindAppointmentByWeek() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String weekStart = "2017-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(weekStart, formatter);

        List<Appointment> apts = agenda.findAppointmentForAWeek(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());
        log.info("apt 3 " + apts.get(2).getTitle());
        
        assertEquals("Appointments found for the week!", 3 , apts.size());
    }
    
     @Test(timeout=1000, expected=DateTimeParseException.class)
    public void testFindAppointmentByWrongFormatWeek() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String weekStart = "01-2017-02";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(weekStart, formatter);

        List<Appointment> apts = agenda.findAppointmentForAWeek(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());
        log.info("apt 3 " + apts.get(2).getTitle());
        
        fail("The record was not found because of bad date format");
    }
    
    @Test
    public void testFindAppointmentByMonth() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String monthStart = "2017-02-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(monthStart, formatter);

        List<Appointment> apts = agenda.findAppointmentForAMonth(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());

        
        assertEquals("Appointments found for the month!", 2 , apts.size());
    }
    
    @Test(timeout=1000, expected=DateTimeParseException.class)
    public void testFindAppointmentByWrongFormatMonth() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        String monthStart = "2017-02-01-00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(monthStart, formatter);

        List<Appointment> apts = agenda.findAppointmentForAMonth(dateTime);
        
        
        log.info("Total apts: " + apts.size());
        log.info("apt 1: " + apts.get(0).getTitle());
        log.info("apt 2: " + apts.get(1).getTitle());

        
        fail("The record was not found because of bad date format");
    }
    
    @Test
    public void testUpdateAppointment() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setId(1);
        apt.setTitle("Test");
        apt.setLocation("Test");
        apt.setStartTime(Timestamp.valueOf("2017-09-01 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-09-01 14:00:00"));
        apt.setDetails("Test");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(1);
        apt.setAlarm(TRUE);
        
        int records = agenda.update(apt);
        
        assertEquals("return of 1 is success", 1, records);
    }
    
    @Test(timeout = 1000, expected = SQLException.class)
    public void testUpdateAppointmentWithTooLongTitle() throws SQLException {
       iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setId(1);
        apt.setTitle("TestTestTestTestTestTestTestTestTestTestTestTestTestTest" + 
                "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTest" + 
                "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTest");
        apt.setLocation("Test");
        apt.setStartTime(Timestamp.valueOf("2017-09-01 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-09-01 14:00:00"));
        apt.setDetails("Test");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(1);
        apt.setAlarm(TRUE);
        
        int records = agenda.update(apt);
        
        //If an expection was not thrown then the test failed.
        fail("The title is far too long");
    }
    
    @Test
    public void testDeleteAppointmentByID() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Test");
        apt.setLocation("Test");
        apt.setStartTime(Timestamp.valueOf("2017-09-01 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-09-01 14:00:00"));
        apt.setDetails("Test");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(1);
        apt.setAlarm(TRUE);
        agenda.create(apt);
        
        int records = agenda.deleteAppointmentbyId(51);
        
        log.debug("id is set to: " + apt.getId());
        log.debug("Record is: " + records);
        
        assertEquals("return of 1 is success", 1, records);
    }
    
    @Test
    public void testDeleteAppointmentByTitle() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        Appointment apt = new Appointment();
        apt.setTitle("Test");
        apt.setLocation("Test");
        apt.setStartTime(Timestamp.valueOf("2017-09-01 12:00:00"));
        apt.setEndTime(Timestamp.valueOf("2017-09-01 14:00:00"));
        apt.setDetails("Test");
        apt.setWholeDay(FALSE);
        apt.setAppointmentGroup(1);
        apt.setAlarm(TRUE);
        agenda.create(apt);
        
        int records = agenda.deleteAppointmentbyTitle("Test");
        
        log.debug("title is set to: " + apt.getTitle());
        log.debug("Record is: " + records);
        
        assertEquals("return of 1 is success", 1, records);
    }
    
    //----APPOINTMENTGROUP----
    @Test
    public void testAppointmentGroupCreate() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();

        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupName("Test Group");
        aptGrp.setColor("Pinky");
        
        int records = agenda.create(aptGrp);
        
        
        assertEquals("A record was created", 1, records);

    }
    
    @Test(timeout = 1000 ,expected = SQLException.class)
    public void testAppointmentGroupCreateWithNullParam() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupName(null);
        aptGrp.setColor("Pinky");
        agenda.create(aptGrp);
        
        fail("The appointment group was not created as the groupname was null.");
    }
    
    @Test
    public void testFindAllAppointmentGroups() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        List<AppointmentGroup> aptGrps = agenda.findAllAppointmentGroups();
        
        assertEquals("# of appointment groups", 4, aptGrps.size());
    }
    
    @Test
    public void testFindAppointmentGroup() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupName("Test");
        aptGrp.setColor("Pinky");
        agenda.create(aptGrp);
        
        AppointmentGroup aptGrpTest = agenda.findAppointmentGroup(5);
        
        assertEquals("Appointment group has been found!", aptGrp, aptGrpTest);
    }
    
    @Test
    public void testUpdateAppointmentGroup() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupName("Test");
        aptGrp.setColor("Pinky");
        
        int records = agenda.create(aptGrp);
        
        assertEquals("AppointmentGroup has been updated!", 1, records);
    }
    
    @Test(timeout = 1000, expected = SQLException.class)
    public void testUpdateAppointmentGroupWithTooLongGroupname() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupName("TestTestTestTestTestTestTestTestTestTestTest" + 
                "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTest" +
                "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTest");
        aptGrp.setColor("Pinky");
        
        int records = agenda.create(aptGrp);
        
        fail("The group name is far too long!");
    }
    
    @Test
    public void testDeleteAppointmentGroup() throws SQLException {
        iAgendaDAO agenda = new AgendaDAO();
        
        AppointmentGroup aptGrp = new AppointmentGroup();
        aptGrp.setGroupNumber(5);
        aptGrp.setGroupName("Test");
        aptGrp.setColor("Pinky");
        agenda.create(aptGrp);
        
        int records = agenda.deleteAppointmentGroup(5);
        
        assertEquals("AppointmentGroup has been deleted!", 1, records);
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

} // End of dbTest

