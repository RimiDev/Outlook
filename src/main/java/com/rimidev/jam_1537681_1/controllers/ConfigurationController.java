package com.rimidev.jam_1537681_1.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import static java.nio.file.Files.newOutputStream;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationController {
    
    
    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());
    private String url;
    

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="dbPort"
    private TextField dbPort;

    @FXML // fx:id="set"
    private Label set;

    @FXML // fx:id="dbName"
    private TextField dbName;

    @FXML // fx:id="dbUser"
    private TextField dbUser;

    @FXML // fx:id="dbUrl"
    private TextField dbUrl;

    @FXML // fx:id="dbPassword"
    private PasswordField dbPassword;
    
    @FXML
    private DialogPane configDialog;

    @FXML
    void onClear(ActionEvent event) {
        dbName.setText("");
        dbUrl.setText("");
        dbUser.setText("");
        dbPassword.setText("");
        dbPort.setText("");
        set.setText("");
    }

    @FXML
    void onSave(ActionEvent event) {
        Properties dbSettings = new Properties();
        /*
        * URL EXAMPLE: jdbc:mysql://localhost:3306/AGENDAdb?autoReconnect=true&useSSL=false
        */
        //url = "jdbc:mysql://localhost:" + dbPort.getText() + "/" + dbName.getText() + "?autoReconnect=true&useSSL=false";
        dbSettings.setProperty("url", dbUrl.getText());
        dbSettings.setProperty("user", dbUser.getText());
        dbSettings.setProperty("password", dbPassword.getText());
        Path dbCredsPath = get("src/main/resources/", "dbCredits.properties");
        // Add properties to the prop object
        try (OutputStream propFileStream = 
        newOutputStream(dbCredsPath, StandardOpenOption.CREATE);){
        dbSettings.store(propFileStream, "Database Properties");
        } catch (IOException e){
            log.debug("Error with writing to file: onSave.");
        }
        set.setText("Configuration saved!\n Database: " + dbName.getText());

    }
    
    @FXML
    void onExit(ActionEvent event){
        final Stage stage = (Stage) configDialog.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }
}
