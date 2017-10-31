package com.rimidev.jam_1537681_1.controllers;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;


/**
 * Controller class for the Appointment form.
 * It controls all the business logic that the Appointment form will process
 * Such as the creation, update, deletion, and reading of Appointments.
 * @author Maxime Lacasse
 * @version 1.5
 */
public class AptFormController {

    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());

    // Database access
    private iAgendaDAO agendaDAO;
    private Appointment apt;
    //Id counter --> for onNext/onPrev
    private int currentId;
    private int deletedId;

    //Filling choice boxes choices
    ObservableList<String> aptGroupChoices = FXCollections.observableArrayList();
    ObservableList<String> hourChoice = FXCollections.observableArrayList();
    ObservableList<String> minuteChoice = FXCollections.observableArrayList();

    /**
     * Simple constructor calling super.
     */
    public AptFormController() {
        super();
    }
  
    /**
     * All the FXML resources that we need to manipulate!
     */

    @FXML // fx:id="Group"
    private ChoiceBox<String> Group;

    @FXML // fx:id="WDno"
    private RadioButton WDno;

    @FXML // fx:id="WDyes"
    private RadioButton WDyes;

    @FXML // fx:id="Alarmoff"
    private RadioButton Alarmoff;

    @FXML // fx:id="Details"
    private TextArea Details;

    @FXML // fx:id="Alarm"
    private ToggleGroup Alarm;

    @FXML // fx:id="AptTitle"
    private TextField AptTitle;

    @FXML // fx:id="Alarmon"
    private RadioButton Alarmon;

    @FXML // fx:id="WholeDay"
    private ToggleGroup WholeDay;

    @FXML // fx:id="Location"
    private TextField Location;

    @FXML
    private Label valid;

    @FXML
    private ChoiceBox<String> startHour;

    @FXML
    private ChoiceBox<String> startMinute;

    @FXML
    private ChoiceBox<String> endHour;

    @FXML
    private ChoiceBox<String> endMinute;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;
    
    @FXML
    private TextField Id;
    
    @FXML
    private DialogPane aptDialog;
    
   /**
    * Initilize the form when popped up.
    * Creates all the options for the choice boxes -> Group, hours, minutes.
    */
    
    @FXML
    void initialize() throws SQLException {
        createAptGroupChoices();
        createHourChoice();
        createMinuteChoice();
    }   
    
    /**
    * When the 'next' button is clicked, it will make sure that there is 
    * another appointment to be displayed.
    * It will then proceed to find the next Appointment by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onNext(ActionEvent event){
        valid.setText("");
        currentId++;
        try {
            if (agendaDAO.maxApts() > currentId-deletedId){               
            apt = agendaDAO.findAppointmentbyId(currentId);
                if (apt.getId() == 0){
                    deletedId++;
                    onNext(new ActionEvent());
                }
            if (currentId > 0){
                setAptInFields();                                 
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
    * another appointment to be displayed.
    * It will then proceed to find the next Appointment by Id.
    * Then it will set the text of all the fields with the appropriate text found.
    */
    @FXML
    void onPrevious(ActionEvent event){
        currentId--;
        try {
            if (agendaDAO.minApts() < currentId){               
            apt = agendaDAO.findAppointmentbyId(currentId);
                if (apt.getId() == 0){
                    deletedId--;
                    onPrevious(new ActionEvent());
                }
            
            if (currentId > 0){               
            setAptInFields();

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
    * If invalid, update Invalid text on top to --> 'Invalid fields!'
    * then it will grab all the fields and create an appointment with it, 
    * and using the agendaDAO object, creates that appointment into the database.
    * Once successfully created, update invalid text on top to --> 'Created!'
    */
    @FXML
    void onSave(ActionEvent event) throws SQLException {
    if ("-1".equals(Id.getText())){
        if (validation()) {
            //create
            createApt(); //Create the apt locally.
            addAppointment(apt); //Create the apt in database.            
            valid.setText("Created!");
        } else {
            valid.setText("Invalid fields!");
        }
        
    } else {
        if (validation()){
            //update           
            createApt(); //Updates apt locally.
            agendaDAO.update(apt); //Updates apt in database.
                    
            valid.setText("Updated!");
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
        AptTitle.setText("");
        Location.setText("");
        Details.setText("");
        WDyes.setSelected(false);
        WDno.setSelected(false);
        Alarmon.setSelected(false);
        Alarmoff.setSelected(false);
        startHour.getSelectionModel().clearSelection();
        startMinute.getSelectionModel().clearSelection();
        endHour.getSelectionModel().clearSelection();
        endMinute.getSelectionModel().clearSelection();
        Group.getSelectionModel().clearSelection();
        valid.setText("");
        startDate.setValue(null);
        endDate.setValue(null);
        Id.setText("-1");
        currentId = 0;
        deletedId = 0;
    }

    /**
     * Simple exit statement.
     * @param event 
     */
    
    @FXML
    void onExit(ActionEvent event) throws SQLException {
       final Stage stage = (Stage) aptDialog.getScene().getWindow();
        stage.close();
    }
    
    /**
     * When the 'delete' button is clicked, it will check if the Id is not equal
     * to -1. -1 being in creation mode, thus cannot be deleted. \
     * If not -1, then it will delete the appointment.
     * Set the invalidText on top to --> 'deleted!'
     * @param event 
     */
    @FXML
    void onDelete(ActionEvent event){
       if (currentId > 0 && (!"-1".equals(Id.getText()))){
           //Delete
           try{
             agendaDAO.deleteAppointmentbyId(Integer.valueOf(Id.getText())); 
             valid.setText("Deleted!");
           } catch (SQLException ex){
            log.error("Delete: SQL EXCEPTION", ex); 
            valid.setText("Invalid database!");
        }
           
       } else {
           log.debug("No delete?");
           valid.setText("Cannot delete this!");
       }
    }
     
    /**
     * Places all options in Appointment Group choice box.
     */   
    public void createAptGroupChoices() {


        //CANNOT PUT THIS INTO INITALIZE OR ELSE IT WILL NOT OPEN
        //ERROR WILL BE IN THE AGENDA APP FILE.
        
//        List<AppointmentGroup> aptGrps = new ArrayList<>();
//            aptGrps = agendaDAO.findAllAppointmentGroups();
//            
//        for (int i = 0; i < aptGrps.size();i++){
//            aptGroupChoices.add(aptGrps.get(i).getGroupName());
//        }
        aptGroupChoices.add("Family");
        aptGroupChoices.add("Work");
        aptGroupChoices.add("Meeting");
        aptGroupChoices.add("Fun");

        Group.setItems(aptGroupChoices);
    }
    
    /**
    * Places all options in the hour choice box.
    */
    public void createHourChoice() {
        for (int i = 0; i < 25; i++) {
            if (i < 10) {
                hourChoice.add("0" + i);
            } else {
                hourChoice.add(i + "");
            }
        }
        startHour.setItems(hourChoice);
        endHour.setItems(hourChoice);
        startHour.setValue(hourChoice.get(0));
        endHour.setValue(hourChoice.get(0));
    }

    /**
    * Places all options in the minute choice box.
    */
    public void createMinuteChoice() {
        for (int i = 0; i < 61; i++) {
            if (i < 10) {
                minuteChoice.add("0" + i);
            } else {
                minuteChoice.add(i + "");
            }
        }
        startMinute.setItems(minuteChoice);
        endMinute.setItems(minuteChoice);
        startMinute.setValue(minuteChoice.get(0));
        endMinute.setValue(minuteChoice.get(0));
    }
 
    /**
     * Validation for adding an appointment to the database.
     * @return 
     */   
    public boolean validation() {
        boolean valid;
        if (aptTitleValidate()) {
            valid = true;
            log.debug("valid---FALSE: TITLE");
        } else {
            return false;
        }
        if (LocationValidation()) {
            valid = true;
        } else {
            return false;
        }
        if (DetailsValidation()) {
            valid = true;
        } else {
            return false;
        }
        if (TimeValidation()) {
            valid = true;
        } else {
            return false;
        }
        if (AptGroupValidation()) {
            valid = true;
        } else {
            return false;
        }
        if (WholeDayValidation()) {
            valid = true;
        } else {
            return false;
        }
        if (AlarmValidation()) {
            valid = true;
        } else {
            return false;
        }
        return valid;

    }

    /**
     * Regex match with alphanumeric to confirm appropriate appointment title.
     * @return true/false
     */
    public boolean aptTitleValidate() {
        if (AptTitle.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }   
     /**
     * Regex match with alphanumeric to confirm appropriate appointment location.
     * @return true/false
     */
    public boolean LocationValidation() {
        if (Location.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Regex match with alphanumeric to confirm appropriate appointment details.
     * @return true/false
     */
    public boolean DetailsValidation() {
        if (Details.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")) {
            return true;
        } else {
            return false;
        }
    }
     /**
     * Checks if the date picker has been selected with a date!
     * @return true/false
     */
    public boolean TimeValidation() {
        boolean valid;
        if (startDate.getValue().toString() == null) {
            return false;
        }
        if (endDate.getValue().toString() == null) {
            return false;
        }
        return true;
    }
     /**
     * Checks whether the appointment group choice box has something selected.
     * @return true/false
     */
    public boolean AptGroupValidation() {
        if (Group.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Checks if the Whole Day toggle group has a selection.
     * @return true/false
     */
    public boolean WholeDayValidation() {
        if (WholeDay.getSelectedToggle().toString() == null) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the Alarm toggle group has a selection.
     * @return 
     */
    public boolean AlarmValidation() {
        if (Alarm.getSelectedToggle().toString() == null) {
            return false;
        }
        return true;
    }    
    
    /**
     * Helper methods for the radio buttons, date picker, and few choice boxes.
     * This is mostly used for the shifting of the appointments from the database
     * so that these tools can match according to the values in database.
     * A few methods that help with the creation of appointments, and setting
     * of appointments in the text fields.
     */
    
    /*
    * Get the start date from the datepicker.
    */
    String getStartDate(){
        String ts = apt.getStartTime().toString();
        String date = ts.substring(0,10);
        return date;
    }
    /**
    * Get the end date from the datepicker.
    */
    String getEndDate(){
        String ts = apt.getEndTime().toString();
        String date = ts.substring(0,10);
        return date;
    }
    /**
     * Get the start time from the timeStamp.
     * @return 
     */
    String getStartHourMins(int format){
        
        String time;
        String ts = apt.getStartTime().toString();
        
        if (format == 0){
            time = ts.substring(11,13); //hour
        } else {
            time = ts.substring(14,16); //mins
        }
        return time;
    }
    /**
    * Get the end time from the timeStamp.
    */
    String getEndHourMins(int format){
        
        String time;
        String ts = apt.getEndTime().toString();
        
        if (format == 0){
            time = ts.substring(11,13); //hour
        } else {
            time = ts.substring(14,16); //mins
        }
        return time;
    }    
    /**
    * Creates the timeStamp string for start time.
    */
    private String getStartTime(){
        String tsS = startDate.getValue().toString()
                    + " " + startHour.getValue()
                    + ":" + startMinute.getValue()
                    + ":" + "00";
        return tsS;
    }
    /**
    * Creates the timeStamp string for end time.
    */
    private String getEndTime(){
        String tsE = endDate.getValue().toString()
                    + " " + endHour.getValue()
                    + ":" + endMinute.getValue()
                    + ":" + "00";
        return tsE;
    }
    /**
    * Checks the Whole day toggle group to see which one is selected -> yes/no.
    */
    private boolean getWholeDay(){
        boolean wd = false;
        
        RadioButton wdRB = (RadioButton) WholeDay.getSelectedToggle();
        String wdValue = wdRB.getText();
        if ("wdYes".equals(wdValue)) {
            wd = true;
        }
        return wd;
    }
    /**
    * Checks the Alarm toggle group to see which one is selected -> on/off.
    */
    private boolean getAlarm(){
        boolean alarm = true;
        RadioButton alarmRB = (RadioButton) Alarm.getSelectedToggle();
        String alarmValue = alarmRB.getText();
        if (alarmValue.equals("alarmOn")) {
            alarm = false;
        }
        return alarm;
        
    }
    
     /**
     * Sets an appointment object into the text fields of the form.
     */
    public void setAptInFields(){
        Id.setText(apt.getId() + "");
        AptTitle.setText(apt.getTitle());
        Location.setText(apt.getLocation());
        Details.setText(apt.getDetails());
        startDate.setValue(LocalDate.parse(getStartDate()));
        endDate.setValue(LocalDate.parse(getEndDate()));
        startHour.getSelectionModel().select(getStartHourMins(0));
        endHour.getSelectionModel().select(getEndHourMins(0));
        startMinute.getSelectionModel().select(getStartHourMins(1));
        endMinute.getSelectionModel().select(getEndHourMins(1));
        Group.getSelectionModel().select(apt.getAppointmentGroup());
        
        //radio buttons selections
            if (apt.getWholeDay()){
                WholeDay.selectToggle(WDyes);
            } else {
                WholeDay.selectToggle(WDno);
            }
            if (apt.getAlarm()){
                Alarm.selectToggle(Alarmon);
            } else {
                Alarm.selectToggle(Alarmoff);
            }
               
    }
    /**
    * Creates or updates the local apt variable in the class.
    */
    public void createApt(){        
        apt.setTitle(AptTitle.getText());
        apt.setLocation(Location.getText());
        apt.setStartTime(Timestamp.valueOf(getStartTime()));
        apt.setEndTime(Timestamp.valueOf(getEndTime()));
        apt.setDetails(Details.getText());
        apt.setAppointmentGroup(aptGroupChoices.indexOf(Group.getValue()));
        apt.setWholeDay(getWholeDay());
        apt.setAlarm(getAlarm());
        
        
    }
    
    /**
     * Sets a reference to the agendaDAO object that retrieves data from the
     * database.
     */
    public void setAgendaDAO(Appointment apt, AgendaDAO agendaDAO) throws SQLException {
        this.agendaDAO = agendaDAO;
        this.apt = apt;
    }

    public void addAppointment(Appointment apt) throws SQLException {
        this.agendaDAO.create(apt);
    }

    public void setAgendaDAOData(Appointment apt, iAgendaDAO agendaDAO) {
        this.apt = apt;
        this.agendaDAO = agendaDAO;
    }
}
