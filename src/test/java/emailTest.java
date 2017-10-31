
import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import com.rimidev.jam_1537681_1.sendInfo.SMTPsend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Testing to see if the sendEmail in the SMTPsend java class works correctly.
 * @author Maxime Lacasse
 * @version 1.2
 *
 */
@Ignore
public class emailTest {

    // Real programmers use logging
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    iAgendaDAO agenda = new AgendaDAO();
    Properties dbCreds = agenda.getDBcredits();
    

    /**
     * Testing the sendEmail in the SMTPsend java class.
     * @throws SQLException 
     */   
    @Test(timeout=20000)
    public void testSendEmail() throws SQLException {
        SMTPsend sender = new SMTPsend();
        sender.createApts();
        List<Appointment> mail = sender.findAllApts();
        sender.sendEmail(mail);
  
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
        try (Connection connection = DriverManager.getConnection(dbCreds.getProperty("url"), dbCreds.getProperty("user"), dbCreds.getProperty("password"))) {
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
