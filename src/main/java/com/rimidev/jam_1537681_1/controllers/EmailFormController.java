
package com.rimidev.jam_1537681_1.controllers;

import com.rimidev.jam_1537681_1.entities.SMTP;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The email controller that holds the business logic for the
 * email form.
 * This form will create/update/delete and read the emails from
 * the database.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class EmailFormController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());

    // Database access
    private iAgendaDAO agendaDAO;
    private SMTP email;
    
    // Counters for next/prev.
    private int currentId;
    private int deletedId;
    
    public EmailFormController() {
        super();
    }
        
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="DUyes"
    private RadioButton DUyes;

    @FXML // fx:id="DUno"
    private RadioButton DUno;

    @FXML // fx:id="Ryes"
    private RadioButton Ryes;

    @FXML // fx:id="Rno"
    private RadioButton Rno;
    
    @FXML
    private ToggleGroup Default;
    
    @FXML
    private ToggleGroup Reminder;

    @FXML // fx:id="SMTP"
    private TextField Email;

    @FXML // fx:id="Valid"
    private Label valid;

    @FXML // fx:id="Port"
    private TextField Port;

    @FXML // fx:id="URL"
    private TextField URL;

    @FXML // fx:id="Name"
    private TextField Name;

    @FXML // fx:id="Password"
    private TextField Password;
    
    @FXML
    private TextField Id;
    
    @FXML
    private DialogPane emailDialog;
    
    /**
    * When the 'next' button is clicked, it will make sure that there is 
    * another email to be displayed.
    * It will then proceed to find the next email by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onNext(ActionEvent event) {
        currentId++;
        try {
            //Selecting the next email in the database
            if (agendaDAO.maxEmails() > currentId-deletedId){               
            email = agendaDAO.findEmailById(currentId);
                if (email.getId() == 0){
                    deletedId++;
                    onNext(new ActionEvent());
                }
            //Setting email into fields.
            if (currentId > 0){
            Id.setText(currentId+"");
            Name.setText(email.getName());
            Email.setText(email.getEmail());
            Password.setText(email.getPassword());
            URL.setText(email.getURL());
            Port.setText(email.getPort()+"");
            
            //radio buttons selections
            if (email.getIsDefault()){
                Default.selectToggle(DUyes);
            } else {
                Default.selectToggle(DUno);
            }
            if (email.getReminder() == 1){
                Reminder.selectToggle(Ryes);
            } else {
                Reminder.selectToggle(Rno);
            }
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
    * another email to be displayed.
    * It will then proceed to find the next email by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onPrevious(ActionEvent event) {
        currentId--;
        try{
            if (agendaDAO.minEmails() < currentId){               
            email = agendaDAO.findEmailById(currentId);
                if (email.getId() == 0){
                    deletedId--;
                    onPrevious(new ActionEvent());
                }

            if (email.getName() != ""){
            Id.setText(currentId+"");
            Name.setText(email.getName());
            Email.setText(email.getEmail());
            Password.setText(email.getPassword());
            URL.setText(email.getURL());
            Port.setText(email.getPort()+"");
            
            //radio buttons selections
            if (email.getIsDefault()){
                Default.selectToggle(DUyes);
            } else {
                Default.selectToggle(DUno);
            }
            if (email.getReminder() == 1){
                Reminder.selectToggle(Ryes);
            } else {
                Reminder.selectToggle(Rno);
            }
            } else {
                currentId++;
                valid.setText("No more records!");
            }
            } else {
                currentId++;
                valid.setText("No more records!");
            }
            
        } catch (SQLException ex){
            log.error("prev: SQL EXCEPTION", ex);  
            valid.setText("Invalid database!");
        }
    }

    /**
    * When the button 'save' is clicked, it will validate all the fields 
    * If invalid, update valid text on top to --> 'Invalid fields!'
    * then it will grab all the fields and create an email with it, 
    * and using the agendaDAO object, creates that email into the database.
    * Once successfully created, update invalid text on top to --> 'Created!'
    */
    @FXML
    void onSave(ActionEvent event) {
        valid.setText("");
        if ("-1".equals(Id.getText())){
            //Create
            if (validation()){
            SMTP newEmail = new SMTP();
            newEmail.setName(Name.getText());
            newEmail.setEmail(Email.getText());
            newEmail.setPassword(Password.getText());
            newEmail.setURL(URL.getText());
            newEmail.setPort(Integer.valueOf(Port.getText()));
            newEmail.setIsDefault(getDefault());
                try {
                    agendaDAO.create(newEmail); 
                    valid.setText("Created!");
                } catch (SQLException ex){
                    log.error("CREATE EMAIL- SQLEXCEPTION");
                }
            } else {
                valid.setText("Invalid fields!");
            }
        } else {
            //Update
            if (validation()){               
                try {
                    email.setName(Name.getText());
                    email.setEmail(Email.getText());
                    email.setPassword(Password.getText());
                    email.setURL(URL.getText());
                    email.setPort(Integer.valueOf(Port.getText()));
                    email.setIsDefault(getDefault());
                    if (getReminder() == true){
                        email.setReminder(1);
                    } else {
                        email.setReminder(0);
                    }
                    
                    
                    agendaDAO.update(email);
                    
                    valid.setText("Updated!");
                } catch (SQLException ex){
                    log.error("UPDATE EMAIL- SQLEXCEPTION");
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
        currentId = 0;
        Id.setText("-1");
        Name.setText("");
        Email.setText("");
        Password.setText("");
        Port.setText("");
        URL.setText("");
        Ryes.setSelected(false);
        Rno.setSelected(false);
        DUyes.setSelected(false);
        DUno.setSelected(false);
        deletedId = 0;
      
    }

    
     /**
     * Simple exit statement. 
     */
    @FXML
    void onExit(ActionEvent event) {
        final Stage stage = (Stage) emailDialog.getScene().getWindow();
        stage.close();
    }
    
     /**
     * When the 'delete' button is clicked, it will check if the Id is not equal
     * to -1. -1 being in creation mode, thus cannot be deleted. \
     * If not -1, then it will delete the email.
     * Set the validText on top to --> 'deleted!'
     * @param event 
     */
    @FXML
    void onDelete(ActionEvent event){
        if (currentId > 0 && (!"-1".equals(Id.getText()))){
           //Delete
           try{
             agendaDAO.deleteEmailById(Integer.valueOf(Id.getText()));
             valid.setText("Deleted!");
           } catch (SQLException ex){
            log.error("Delete: SQL EXCEPTION", ex); 
            valid.setText("Invalid database!");
        }
           
       } else {
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
        if (!nameValidation()) {
            return false;
        }
        if (!emailValidation()){
            return false;
        }
        if (!passwordValidation()){
            return false;
        }
        if (!urlValidation()){
            return false;
        }
        if (!defaultValidation()){
            return false;
        }
        if (!reminderValidation()){
            return false;
        }
        return valid;
    }
    
    /*
    * Validates the name field by regex of alphanumeric.
    */
    
    private boolean nameValidation(){
        if (Name.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
    * Validates the email field by regex of an approriate email.
    * such as xxxxx@gmail.com
    */
    private boolean emailValidation(){
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        if (Email.getText().matches(regex+"")){
            return true;
        } else {
            return false;
        }
    }
    
    /*
    * Validates the password field by regex of alphanumeric.
    */
    private boolean passwordValidation(){
        if (Password.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
    * Validates the URL field by regex of alphanumeric.
    */
    private boolean urlValidation(){
        if (URL.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
    * Validates if the isDefault toggle has at least one selection.
    */
    private boolean defaultValidation(){
        if (Default.getSelectedToggle().toString() == null) {
            return false;
        }
        return true;
    }
    /*
    * Validates if the reminder toggle has at least one selection.
    */
    private boolean reminderValidation(){
        if (Reminder.getSelectedToggle().toString() == null) {
            return false;
        }
        return true;
    }
   
    /**
     * Helper methods for the toggle selection
     * isDefault and Reminder radio buttons helpers.
     */
    
    /**
     * Gets the selection of the isDefault toggle group.
     * @return 
     */    
    public boolean getDefault(){
        boolean def = false;
        
        RadioButton defaultRB = (RadioButton) Default.getSelectedToggle();
        String defaultValue = defaultRB.getText();
        
        if (defaultValue == "yes") {
            def = true;
        }
        return def;
    }
    
    /**
     * Gets the selection of the reminder toggle group.
     * @return 
     */ 
    public boolean getReminder(){
        boolean rem = false;
        
        RadioButton reminderRB = (RadioButton) Reminder.getSelectedToggle();
        String reminderValue = reminderRB.getText();
        
        if (reminderValue == "yes"){
            rem = true;
        }
        return rem;
    }
    
    
    
    /**
     * Sets a reference to the agendaDAO object that retrieves data from the
     * database.
     */
    public void setAgendaDAO(SMTP email, AgendaDAO agendaDAO) throws SQLException {
        this.agendaDAO = agendaDAO;
    }

    public void setAgendaDAOData(SMTP email, iAgendaDAO agendaDAO) {
        this.email = email;
        this.agendaDAO = agendaDAO;
    }
    
    
    
}
