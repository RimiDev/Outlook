package com.rimidev.jam_1537681_1.controllers;

import com.rimidev.jam_1537681_1.entities.AppointmentGroup;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The appointment group controller that holds the business logic for the
 * appointment group form.
 * This form will create/update/delete and read the appointment groups from
 * the database.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class AptGrpFormController {
    
    //for the color choice box.
    ObservableList colorChoices = FXCollections.observableArrayList();
    
    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());

    // Database access
    private iAgendaDAO agendaDAO;
    private AppointmentGroup aptGrp;
    private int currentId;
    private int deletedId;
       
    /**
     * Default constructor that calls super.
     */
    public AptGrpFormController() {
        super();
    }

    @FXML // fx:id="GroupName"
    private TextField GroupName;

    @FXML // fx:id="Valid"
    private Label valid;

    @FXML // fx:id="GroupNumber"
    private TextField GroupNumber;

    @FXML
    private TextField Color;
    
    @FXML
    private DialogPane aptGrpDialog;

    /**
    * When the 'next' button is clicked, it will make sure that there is 
    * another appointment group to be displayed.
    * It will then proceed to find the next AppointmentGroup by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onNext(ActionEvent event) {
        currentId++;
        try {
            if (agendaDAO.maxAptGrps() > currentId-deletedId){               
            aptGrp = agendaDAO.findAppointmentGroup(currentId);
                if (aptGrp.getGroupNumber() == 0){
                    deletedId++;
                    onNext(new ActionEvent());
                }

            if (currentId > 0){
            GroupNumber.setText(aptGrp.getGroupNumber()+"");
            GroupName.setText(aptGrp.getGroupName()); 
            Color.setText(aptGrp.getColor());
            } else {
                currentId--;
                valid.setText("No more records!");
            }
                } else {
                currentId--;
                valid.setText("No more records!"); 
                }
            
        } catch (SQLException ex){
            log.error("next: SQL EXCEPTION", ex);  
            valid.setText("Invalid database!");
        }
    }
    
    /**
    * When the 'previous' button is clicked, it will make sure that there is 
    * another appointment group to be displayed.
    * It will then proceed to find the next AppointmentGroup by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onPrevious(ActionEvent event) { 
        currentId--;
        try {
            if (agendaDAO.minAptGrps() < currentId){               
            aptGrp = agendaDAO.findAppointmentGroup(currentId);
                if (aptGrp.getGroupNumber() == 0){
                    deletedId--;
                    onPrevious(new ActionEvent());
                }
            if (currentId > 0){
            GroupNumber.setText(aptGrp.getGroupNumber()+"");
            GroupName.setText(aptGrp.getGroupName()); 
            Color.setText(aptGrp.getColor());
            } else {
                currentId++;
                valid.setText("No more records!");
            }
            } else {
                currentId++;
                valid.setText("No more records!");
            }
            
        } catch (SQLException ex){
            log.error("next: SQL EXCEPTION", ex);  
            valid.setText("Invalid database!");
        }
    }

    /**
    * When the button 'save' is clicked, it will validate all the fields 
    * If invalid, update valid text on top to --> 'Invalid fields!'
    * then it will grab all the fields and create an appointment group with it, 
    * and using the agendaDAO object, creates that appointment group into the database.
    * Once successfully created, update invalid text on top to --> 'Created!'
    */
    @FXML
    void onSave(ActionEvent event) {
        valid.setText("");
        if ("-1".equals(GroupNumber.getText())){
            //Create
            if (validation()){
                log.debug("valid");
            AppointmentGroup newAptGrp = new AppointmentGroup();
            newAptGrp.setGroupName(GroupName.getText());
            newAptGrp.setColor(Color.getText());
                try {
                    agendaDAO.create(newAptGrp); 
                    valid.setText("Created!");
                } catch (SQLException ex){
                    log.error("CREATE APTGRP- SQLEXCEPTION");
                }
            } else {
                valid.setText("Invalid fields!");
            }
        } else {
            //Update
            log.debug("update");
            if (validation()){               
                try {
                    aptGrp.setGroupName(GroupName.getText());
                    aptGrp.setColor(Color.getText());;
                    agendaDAO.update(aptGrp);
                    
                    valid.setText("Updated!");
                } catch (SQLException ex){
                    log.error("CREATE APTGRP- SQLEXCEPTION");
                }
            } else {
                valid.setText("Invalid fields!");
            }
            }
        }
    /**
    * Clear all the fields when the 'clear' button is clicked.
    */
    @FXML
    void onClear(ActionEvent event) {
        GroupNumber.setText("-1");
        GroupName.setText("");
        Color.setText("");
        currentId = 0;
        valid.setText("");
        deletedId = 0;
    }
  

    /**
     * Simple exit statement. 
     */
    @FXML
    void onExit(ActionEvent event) {
        final Stage stage = (Stage) aptGrpDialog.getScene().getWindow();
        stage.close();
    }
    
    /**
     * When the 'delete' button is clicked, it will check if the Id is not equal
     * to -1. -1 being in creation mode, thus cannot be deleted. \
     * If not -1, then it will delete the appointment group.
     * Set the validText on top to --> 'deleted!'
     * @param event 
     */
    @FXML
    void onDelete(ActionEvent event){
        if (currentId > 0 && (!"-1".equals(GroupNumber.getText()))){
           //Delete
           try{
             agendaDAO.deleteAppointmentGroup(Integer.valueOf(GroupNumber.getText()));
             valid.setText("Deleted!");
           } catch (SQLException ex){
            log.error("Delete: SQL EXCEPTION", ex);           
        }
           
       } else {
           log.debug("No delete?");
           valid.setText("Cannot delete this!");
       }
    }

    /**
     * Not used.
     */
    @FXML 
    void initialize() {

    }
    

    /**
     * Validation of all the text fields using helper methods.
     * @return true/false
     */
    private boolean validation(){
        boolean valid = true;
        if (!groupNameValidate()){
            return false;
        }
        if (!colorValidate()){
            return false;
        }
        return valid;
    }
    
    /**
     * Validation of the group name with regex of alphanumeric.
     * @return true/false
     */
    public boolean groupNameValidate() {
        if (GroupName.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Validation of the color with regex of alphanumeric
     * @return true/false
     */
    public boolean colorValidate(){
        if (Color.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
      
    /**
     * Sets a reference to the agendaDAO object that retrieves data from the
     * database.
     *
     * @param aptGrp
     * @param agendaDAO
     * @throws SQLException
     */
    public void setAgendaDAO(AppointmentGroup aptGrp, AgendaDAO agendaDAO) throws SQLException {
        this.agendaDAO = agendaDAO;
    }

    public void setAgendaDAOData(AppointmentGroup aptGrp, iAgendaDAO agendaDAO) {
        this.aptGrp = aptGrp;
        this.agendaDAO = agendaDAO;
    }
    
    
    
}
