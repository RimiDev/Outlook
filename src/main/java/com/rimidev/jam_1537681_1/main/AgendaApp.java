package com.rimidev.jam_1537681_1.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rimidev.jam_1537681_1.controllers.AgendaFormController;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import com.rimidev.jam_1537681_1.controllers.SampleController;
import java.util.Locale;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Basic class for starting a JavaFX application
 *
 * #KFCStandard and JavaFX8
 *
 * @author Ken Fogel
 */
public class AgendaApp extends Application {

    // Real programmers use logging, not System.out.println
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    // The primary window or frame of this application
    private Stage primaryStage;
    private iAgendaDAO agendaDAO;
    private final Locale currentLocale;

    /**
     * Constructor
     */
    public AgendaApp() {
        super();
        currentLocale = new Locale("en", "CA");
        agendaDAO = new AgendaDAO();
    }

    /**
     * The application starts here
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        log.info("Program Begins");

        // The Stage comes from the framework so make a copy to use elsewhere
        this.primaryStage = primaryStage;
        // Create the Scene and put it on the Stage
        configureStage();

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
    private void configureStage() {
        try {
            // Instantiate the FXMLLoader
            FXMLLoader loader = new FXMLLoader();

            // Set the location of the fxml file in the FXMLLoader
            loader.setLocation(AgendaApp.class.getResource("/fxml/createApt.fxml"));

            // Localize the loader with its bundle
            // Uses the default locale and if a matching bundle is not found
            // will then use MessagesBundle.properties
            loader.setResources(ResourceBundle.getBundle("MessageBundle", currentLocale));

            // Parent is the base class for all nodes that have children in the
            // scene graph such as AnchorPane and most other containers
            Parent parent = (GridPane) loader.load();

            // Load the parent into a Scene
            Scene scene = new Scene(parent);

            // Put the Scene on Stage
            primaryStage.setScene(scene);

            // Retrieve a reference to the controller so that you can pass a
            // reference to the persistence object
           AgendaFormController controller = loader.getController();
//            controller.setFishDAO(fishDAO);

        } catch (IOException ex) { // getting resources or files
            // could fail
            log.error(null, ex);
            System.exit(1);
        }
    }

    /**
     * Where it all begins
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
