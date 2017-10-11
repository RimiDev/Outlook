/**
 * Sample Skeleton for 'createApt.fxml' Controller Class
 */

package com.rimidev.jam_1537681_1.controllers;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgendaFormController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());

    // Database access
    private AgendaDAO agendaDAO;
    //private Appointment appointment;  
    
    ObservableList<String> aptGroupChoices = FXCollections.observableArrayList();
    ObservableList<String> hourChoice = FXCollections.observableArrayList();
    ObservableList<String> minuteChoice = FXCollections.observableArrayList();
    ObservableList<String> secondChoice = FXCollections.observableArrayList();
    private Appointment apt;
    
    public AgendaFormController() {
        super();
        log.info("Default Constructor");
    }
    
            

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Add"
    private Button Add; // Value injected by FXMLLoader

    @FXML // fx:id="Group"
    private ChoiceBox<String> Group; // Value injected by FXMLLoader

    @FXML // fx:id="WDno"
    private RadioButton WDno; // Value injected by FXMLLoader

    @FXML // fx:id="createAptFormGrid"
    private GridPane createAptFormGrid; // Value injected by FXMLLoader

    @FXML // fx:id="Clear"
    private Button Clear; // Value injected by FXMLLoader

    @FXML // fx:id="WDyes"
    private RadioButton WDyes; // Value injected by FXMLLoader

    @FXML // fx:id="Alarmoff"
    private RadioButton Alarmoff; // Value injected by FXMLLoader

    @FXML // fx:id="Details"
    private TextArea Details; // Value injected by FXMLLoader

    @FXML // fx:id="Alarm"
    private ToggleGroup Alarm; // Value injected by FXMLLoader

    @FXML // fx:id="AptTitle"
    private TextField AptTitle; // Value injected by FXMLLoader

    @FXML // fx:id="Alarmon"
    private RadioButton Alarmon; // Value injected by FXMLLoader

    @FXML // fx:id="WholeDay"
    private ToggleGroup WholeDay; // Value injected by FXMLLoader

    @FXML // fx:id="Location"
    private TextField Location; // Value injected by FXMLLoader

    @FXML // fx:id="Exit"
    private Button Exit; // Value injected by FXMLLoader
    
    @FXML
    private Label Invalid;
    
    @FXML
    private ChoiceBox<String> startHour;
    
    @FXML
    private ChoiceBox<String> startMinute;
    
    @FXML
    private ChoiceBox<String> startSecond;
    
    @FXML
    private ChoiceBox<String> endHour;
    
    @FXML
    private ChoiceBox<String> endMinute;
    
    @FXML
    private ChoiceBox<String> endSecond;
    
    @FXML
    private DatePicker startDate;
    
    @FXML
    private DatePicker endDate;

    @FXML
    void AddApt(ActionEvent event) throws SQLException {
        
        if (validation()){
            
        Appointment apt = new Appointment();    
            
        String tsS = startDate.getValue().toString()
                + " " + startHour.getValue()
                + ":" + startMinute.getValue() 
                + ":" + startSecond.getValue();
        
        String tsE = endDate.getValue().toString()
                + " " + endHour.getValue()
                + ":" + endMinute.getValue() 
                + ":" + endSecond.getValue();
        
        RadioButton wdRB = (RadioButton) WholeDay.getSelectedToggle();
        String wdValue = wdRB.getText();
        
        RadioButton alarmRB = (RadioButton) Alarm.getSelectedToggle();
        String alarmValue = alarmRB.getText();
        
        boolean wd = false;
        boolean alarm = false;
        if (wdValue == "yes"){
            wd = true;
        }
        if (alarmValue == "on"){
            alarm = true;
        }
           log.debug("VALUE: TITLE " + AptTitle.getText()); 
        apt.setTitle(AptTitle.getText());       
        apt.setLocation(Location.getText());        
        apt.setStartTime(Timestamp.valueOf(tsS));
        apt.setEndTime(Timestamp.valueOf(tsE));
        apt.setDetails(Details.getText());
        apt.setAppointmentGroup(aptGroupChoices.indexOf(Group.getValue()));
        apt.setWholeDay(wd);
        apt.setAlarm(alarm);
        
        addAppointment(apt); //Create the apt.
        
        Invalid.setText("Created!");
        
        } else {
            Invalid.setText("Invalid fields");
        }               
    }
    
    public boolean validation(){
        boolean valid;
        if (aptTitleValidate()){
            valid = true;
            log.debug("valid---FALSE: TITLE");
        } else {
            return false;
        }
        if (LocationValidation()){
            valid = true;
            log.debug("valid---FALSE: LOCATION");
        } else {
            return false;
        }
        if (DetailsValidation()){
            valid = true;
            log.debug("valid---FALSE: DETAILS");
        } else {
            return false;
        }
        if (TimeValidation()){
            valid = true;
            log.debug("valid---FALSE: TIME");
        } else {
            return false;
        }
        if (AptGroupValidation()){
            valid = true;
            log.debug("valid---FALSE: GROUP");
        } else {
            return false;
        }
        if (WholeDayValidation()){
            valid = true;
            log.debug("valid---FALSE: WD");
        } else {
            return false;
        }
        if (AlarmValidation()){
            valid = true;
            log.debug("valid---FALSE: ALARM");
        } else {
            return false;
        }
        return valid;
    
    }
    
    public boolean aptTitleValidate(){
        if (AptTitle.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean LocationValidation(){
        if (Location.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean DetailsValidation(){
        if (Details.getText().matches("[a-zA-Z0-9 @!+,.$()\"'&-]+")){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean TimeValidation(){
        boolean valid;
        if (startDate.getValue().toString() == null){
            return false;
        }
        if (startHour.getValue() == null){
            return false;
        } 
        if (startMinute.getValue() == null){
            return false;
        }
        if (startSecond.getValue() == null){
            return false;
        }
        if (endDate.getValue().toString() == null){
            return false;
        }
        if (endHour.getValue() == null){
            return false;
        }
        if (endMinute.getValue() == null){
            return false;
        }
        if (endSecond.getValue() == null){
            return false;
        } 
        
        return true;
    }
    
    public boolean AptGroupValidation(){
        if (Group.getValue() == null){
            return false;
        } else {
            return true;
        }
    }
    
    public boolean WholeDayValidation(){
       if (WholeDay.getSelectedToggle().toString() == null){
            return false;
       }
            return true;
    }
    
    public boolean AlarmValidation(){
        if (Alarm.getSelectedToggle().toString() == null){
            return false;
       }
            return true;
    }

    @FXML
    void ClearForm(ActionEvent event) {
        AptTitle.setText("");
        Location.setText("");
        Details.setText("");
        WDyes.setSelected(false);
        WDno.setSelected(false);
        Alarmon.setSelected(false);
        Alarmoff.setSelected(false);
        startHour.getSelectionModel().clearSelection();
        startMinute.getSelectionModel().clearSelection();
        startSecond.getSelectionModel().clearSelection();
        endHour.getSelectionModel().clearSelection();
        endMinute.getSelectionModel().clearSelection();
        endSecond.getSelectionModel().clearSelection();
        Group.getSelectionModel().clearSelection();
        Invalid.setText("");
        
    }

    @FXML
    void Exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        createAptGroupChoices();
        createHourChoice();
        createMinuteChoice();
        createSecondChoice();
    } 
    /**
     * Sets a reference to the agendaDAO object that retrieves data from the
     * database.
     *
     * @param agendaDAO
     * @throws SQLException
     */
    public void setAgendaDAO(Appointment apt, AgendaDAO agendaDAO) throws SQLException {
        this.agendaDAO = agendaDAO;
    }
    
    public void addAppointment(Appointment apt) throws SQLException {
        this.agendaDAO.create(apt);
    }
    
    public void createAptGroupChoices(){      
            aptGroupChoices.add("Family");
            aptGroupChoices.add("Work");
            aptGroupChoices.add("Meeting");
            aptGroupChoices.add("Fun");

            Group.setItems(aptGroupChoices);       
    }
    
    public void createHourChoice(){
        for(int i=0;i<25;i++){
            if (i < 10){
                hourChoice.add("0"+i);
            } else {
            hourChoice.add(i+"");
            }
        }       
        startHour.setItems(hourChoice); 
        endHour.setItems(hourChoice);
        startHour.setValue(hourChoice.get(0));
        endHour.setValue(hourChoice.get(0));
    }
    
    public void createMinuteChoice(){
        for(int i=0;i<61;i++){
            if (i < 10){
                minuteChoice.add("0"+i);
            } else {
            minuteChoice.add(i+"");
            }
        }
        startMinute.setItems(minuteChoice);
        endMinute.setItems(minuteChoice);
        startMinute.setValue(minuteChoice.get(0));
        endMinute.setValue(minuteChoice.get(0));
    }
    
    public void createSecondChoice(){
        for(int i=0;i<61;i++){
            if (i < 10){
                secondChoice.add("0"+i);
            } else {
            secondChoice.add(i+"");
            }
        }
        startSecond.setItems(secondChoice);
        endSecond.setItems(secondChoice);
        startSecond.setValue(secondChoice.get(0));
        endSecond.setValue(secondChoice.get(0));      
    }
    
    
    
    
    public void setAgendaDAOData(Appointment apt, AgendaDAO agendaDAO) {
        this.apt = apt;
        try {
            this.agendaDAO = agendaDAO;
            agendaDAO.create(apt);
        } catch (SQLException ex) {
            log.error("SQL Error", ex);
        }
    }
    
    
//    private void doBindings() {
//
//        // Two way binding
//        //Bindings.bindBidirectional(idTextField.textProperty(), fishData.idProperty(), new NumberStringConverter());
//        Bindings.bindBidirectional(AptTitle.textProperty(), apt.titleProperty());
//        Bindings.bindBidirectional(Location.textProperty(), apt.locationProperty());
//        //Bindings.bindBidirectional(StartTime.textProperty(), apt.startTimeProperty());
//        //Bindings.bindBidirectional(EndTime.textProperty(), apt.endTimeProperty());
//        Bindings.bindBidirectional(Details.textProperty(), apt.detailsProperty());
//        Bindings.bindBidirectional(WDyes.toggleGroupProperty(), apt.wholeDayProperty());
//        Bindings.bindBidirectional(WDno.toggleGroupProperty(), apt.wholeDayProperty());
//        Bindings.bindBidirectional(Alarm.textProperty(), fishData.speciesOriginProperty());
//        Bindings.bindBidirectional(tankSizeTextField.textProperty(), fishData.tankSizeProperty());
//        Bindings.bindBidirectional(stockingTextField.textProperty(), fishData.stockingProperty());
//        Bindings.bindBidirectional(dietTextField.textProperty(), fishData.dietProperty());

        // One Way Binding. Bind from bean property to TextField
//        idTextField.textProperty().bind(fishData.idProperty().asString());
//        commonNameTextField.textProperty().bind(fishData.commonNameProperty());
//        latinTextField.textProperty().bind(fishData.latinProperty());
//        phTextField.textProperty().bind(fishData.phProperty());
//        khTextField.textProperty().bind(fishData.khProperty());
//        tempTextField.textProperty().bind(fishData.tempProperty());
//        fishSizeTextField.textProperty().bind(fishData.fishSizeProperty());
//        speciesOriginTextField.textProperty().bind(fishData.speciesOriginProperty());
//        tankSizeTextField.textProperty().bind(fishData.tankSizeProperty());
//        stockingTextField.textProperty().bind(fishData.stockingProperty());
//        dietTextField.textProperty().bind(fishData.dietProperty());
    }

   
