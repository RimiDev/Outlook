package com.rimidev.jam_1537681_1.main;

import com.rimidev.jam_1537681_1.threads.autoSendEmail;
import com.rimidev.jam_1537681_1.controllers.AptFormController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rimidev.jam_1537681_1.controllers.ViewController;
import com.rimidev.jam_1537681_1.controllers.AptGrpFormController;
import com.rimidev.jam_1537681_1.controllers.ConfigurationController;
import com.rimidev.jam_1537681_1.controllers.EmailFormController;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.entities.SMTP;
import java.util.Locale;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AgendaApp extends Application {

    // Real programmers use logging, not System.out.println
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    // The primary window or frame of this application
    private Stage primaryStage;
    private Parent rootPane;
    private final iAgendaDAO agendaDAO;
    private final Appointment apt;
    private final AppointmentGroup aptGrp;
    private final SMTP email;
    private Dialog dialogApt;
    private Dialog dialogAptGrp;
    private Dialog dialogEmail;
    private Dialog dialogConfig;
    private DialogPane formDialog;
 
    private final Locale currentLocale;

    /**
     * Constructor
     */
    public AgendaApp() {
        super();
        currentLocale = new Locale("en", "CA");
        agendaDAO = new AgendaDAO();
        apt = new Appointment();
        aptGrp = new AppointmentGroup();
        email = new SMTP();
    }

    /**
     * The application starts here
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // The Stage comes from the framework so make a copy to use elsewhere
        this.primaryStage = primaryStage;
        // Create the Scene and put it on the Stage
        configureStage();
        initRootLayout();

        // Set the window title
        this.primaryStage.setTitle(ResourceBundle.getBundle("MessageBundle", currentLocale).getString("Title"));
        // Raise the curtain on the Stage
        primaryStage.show();       
    }

    /**
     * Load the FXML and bundle, create a Scene and put the Scene on Stage.
     *
     * Using this approach allows you to use loader.getController() to get a
     * reference to the fxml's controller should you need to pass data to it.
     * Not used in this archetype.
     */
    private void configureStage() throws SQLException {
        
        
        /**
         * APPOINTMENT WINDOW
         */
        try {
            // Instantiate the FXMLLoader
            FXMLLoader loader = new FXMLLoader();

            // Set the location of the fxml file in the FXMLLoader
            loader.setLocation(AgendaApp.class.getResource("/fxml/AptForm.fxml"));

            // Localize the loader with its bundle
            // Uses the default locale and if a matching bundle is not found
            // will then use MessagesBundle.properties
            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));

            // Parent is the base class for all nodes that have children in the
            // scene graph such as AnchorPane and most other containers
            formDialog = (DialogPane) loader.load();

            // Load the parent into a Scene
            //Scene scene = new Scene(parent);

            // Put the Scene on Stage
            //primaryStage.setScene(scene);

            // Retrieve a reference to the controller so that you can pass a
            // reference to the persistence object
           AptFormController controller = loader.getController();
//            controller.setFishDAO(fishDAO);
            controller.setAgendaDAOData(apt, agendaDAO);
            
            dialogApt = new Dialog();
            dialogApt.setDialogPane(formDialog);

        } catch (IOException ex) { // getting resources or files
            // could fail
            log.error("Error w/ aptform", ex);
            Platform.exit();
        }
        
        /**
         * APPOINTMENT GROUP WINDOW
         */
        try {
            // Instantiate the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(AgendaApp.class.getResource("/fxml/AptGrpForm.fxml"));

            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));

            formDialog = (DialogPane) loader.load();

           AptGrpFormController controller = loader.getController();
            controller.setAgendaDAOData(aptGrp, agendaDAO);
            
            dialogAptGrp = new Dialog();
            dialogAptGrp.setDialogPane(formDialog);

        } catch (IOException ex) { // getting resources or files
            // could fail
            log.error("Error w/ aptgrpForm", ex);
            Platform.exit();
        }
        
        
        /**
         * EMAIL WINDOW
         */
        try {
            // Instantiate the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(AgendaApp.class.getResource("/fxml/EmailForm.fxml"));

            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));

            formDialog = (DialogPane) loader.load();

           EmailFormController controller = loader.getController();
            controller.setAgendaDAOData(email, agendaDAO);
            
            dialogEmail = new Dialog();
            dialogEmail.setDialogPane(formDialog);

        } catch (IOException ex) { // getting resources or files
            // could fail
            log.error("Error w/ aptgrpForm", ex);
            Platform.exit();
        }
        
        /**
         * CONFIGURATION WINDOW
         */
        try {
            // Instantiate the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(AgendaApp.class.getResource("/fxml/Configuration.fxml"));

            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));

            formDialog = (DialogPane) loader.load();

           ConfigurationController controller = loader.getController();
            //controller.setAgendaDAOData(email, agendaDAO);
            
            dialogConfig = new Dialog();
            dialogConfig.setDialogPane(formDialog);

        } catch (IOException ex) { // getting resources or files
            // could fail
            log.error("Error w/ configuration Window", ex);
            Platform.exit();
        }
        
        
    }
    
    
    
    
    public void initRootLayout() throws SQLException {
      
        //Loading the Main Window --> View
        try {
            // Instantiate a FXMLLoader object
            FXMLLoader loader = new FXMLLoader();

            // Configure the FXMLLoader with the i18n locale resource bundles
            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));
            // Connect the FXMLLoader to the fxml file that is stored in the jar
            loader.setLocation(AgendaApp.class
                    .getResource("/fxml/View.fxml"));

            // The load command returns a reference to the root pane of the fxml file
            rootPane = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootPane);

            // Put the Scene on the Stage
            primaryStage.setScene(scene);

            // Retrive a refernce to the controller from the FXMLLoader
            ViewController controller = loader.getController();
            controller.setAgendaDAO(agendaDAO);

            controller.setAptDialog(dialogApt);
            controller.setAptGrpDialog(dialogAptGrp);
            controller.setEmailDialog(dialogEmail);
            controller.setConfigDialog(dialogConfig);
            controller.displayTheTable(1,0);            

        } catch (IOException ex) {
            log.error("Error creating the stage", ex);
            Platform.exit();
        }  
    }
    
    

    public static void main(String[] args) {
        autoSendEmail autoEmailSender = new autoSendEmail();
        autoEmailSender.sendAutoEmail();
        launch(args);
        autoEmailSender.cancelSend();
    }
}
