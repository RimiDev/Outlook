package com.rimidev.jam_1537681_1.controllers;

import com.rimidev.jam_1537681_1.entities.Appointment;
import com.rimidev.jam_1537681_1.entities.CalendarRows;
import com.rimidev.jam_1537681_1.entities.dailyBean;
import com.rimidev.jam_1537681_1.persistence.AgendaDAO;
import com.rimidev.jam_1537681_1.persistence.iAgendaDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Maxime Lacasse
 * @version 1.5
 */
public class ViewController {

    private final Logger log = LoggerFactory.getLogger(this.getClass()
            .getName());
    
    private iAgendaDAO agendaDAO;
    
    private Dialog dialogAptGrp;
    private Dialog dialogApt;
    private Dialog dialogEmail;
    private Dialog dialogConfig;
    
    
    private ObservableList<TablePosition> theCells;
    
    ObservableList<dailyBean> daily = FXCollections.observableArrayList();
    ObservableList<dailyBean> monthly = FXCollections.observableArrayList();
    
    private List months = new ArrayList();
    private List CalMonths = new ArrayList();
    private int currentMonth = 1;
    private int currentWeek = 1;
    private int currentDay = 1;
    private int format = 1;

    @FXML
    private TableView<CalendarRows> monthlyTable;
     
    @FXML
    private TableView<CalendarRows> weeklyTable;
      
    @FXML
    private TableView <dailyBean> dailyTable;

    @FXML
    private TableColumn<CalendarRows, String> col1Col;

    @FXML
    private TableColumn<CalendarRows, String> col2Col;

    @FXML
    private TableColumn<CalendarRows, String> col3Col;

    @FXML
    private TableColumn<CalendarRows, String> col4Col;

    @FXML
    private TableColumn<CalendarRows, String> col5Col;

    @FXML
    private TableColumn<CalendarRows, String> col6Col;

    @FXML
    private TableColumn<CalendarRows, String> col7Col;
    
    @FXML
    private Label monthLabel;
    
    @FXML
    private TableColumn<dailyBean, String> dailyDay;
    
    @FXML
    private TableColumn<dailyBean, String> dailyTime;
    
    
    
    /**
     * Sets up all the cell binding, as well as adjusting the columns, collecting
     * of data, and rendering of cells.
     * 
     */
    @FXML
    void initialize() {
        
        //Monthly View property setting
        col1Col.setCellValueFactory(cellData -> cellData.getValue()
                .col1Property());
        col2Col.setCellValueFactory(cellData -> cellData.getValue()
                .col2Property());
        col3Col.setCellValueFactory(cellData -> cellData.getValue()
                .col3Property());
        col4Col.setCellValueFactory(cellData -> cellData.getValue()
                .col4Property());
        col5Col.setCellValueFactory(cellData -> cellData.getValue()
                .col5Property());
        col6Col.setCellValueFactory(cellData -> cellData.getValue()
                .col6Property());
        col7Col.setCellValueFactory(cellData -> cellData.getValue()
                .col7Property());
        
        
        //Daily view property setting
        dailyTime.setCellValueFactory(cellData -> cellData.getValue()
                .timeProperty());
        
        dailyDay.setCellValueFactory(cellData -> cellData.getValue()
                .aptProperty());
        
        // Set the column widths of the table
        adjustColumnWidths(format);
        // Use the custome renderer for a specific column
        renderCell(col1Col);

        // Set selection to a single cell
        monthlyTable.getSelectionModel().setCellSelectionEnabled(true);
        weeklyTable.getSelectionModel().setCellSelectionEnabled(true);
        dailyTable.getSelectionModel().setCellSelectionEnabled(true);

        // Listen for selection changes and show the selected fishData details
        // when a new row is selected.
        monthlyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showColDetails(newValue));
        weeklyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showColDetails(newValue));
        dailyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showColDetails(newValue));
        

        // Observable list of TablePosition objects that represent single cells
        theCells = monthlyTable.getSelectionModel().getSelectedCells();

        // Listen for selection changes for a single cell amongst the selected cells
        theCells.addListener(this::showSingleCellDetails);
        
        theCells = weeklyTable.getSelectionModel().getSelectedCells();
        theCells.addListener(this::showSingleCellDetails);
        
        //Months
        for (int i = 0;i<months.size();i++){
            if (i<10){
                months.add(i, "0"+i);
            }
            months.add(i);
        } 
        
        monthLabel.setText(monthWords(currentMonth));
        
        
        //Hide weekly/daily for now
        weeklyTable.setVisible(false);
        dailyTable.setVisible(false);
    }
    
     /**
     * Clicking on configuration will grant you the configuration form.
     * @param event 
     */
    @FXML
    void onConfig(ActionEvent event) {
        dialogConfig.showAndWait();
    }

    /**
     * A simple exiting of the whole program.
     * @param event 
     */
    @FXML
    void onExit(ActionEvent event) {
        
        Platform.exit();
    }

    /**
     * Clicking on appointment will grant you the appointment form.
     * @param event 
     */    
    @FXML
    void onAptForm(ActionEvent event) throws SQLException {
        dialogApt.showAndWait();
    }

    /**
     * Clicking on appointment group form will grant you the appointment group form.
     * @param event 
     */    
    @FXML
    void onAptGroupForm(ActionEvent event) {
        dialogAptGrp.showAndWait();
        
    }

    /**
     * Clicking on email will grant you the email form.
     * @param event 
     */
    @FXML
    void onEmailForm(ActionEvent event) {
        dialogEmail.showAndWait();
    }

     /**
     * Clicking on the weekly in the toolbar will grant you the weekly view.
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onWeeklyView(ActionEvent event) throws SQLException {
        format = 2;
        //monthlyTable.getItems().clear();
        displayTheTable(format,0);
        adjustColumnWidths(format);
        monthlyTable.setVisible(false);
        dailyTable.setVisible(false);
        weeklyTable.setVisible(true);  
    }

    /**
     * Clicking on the monthly in the toolbar will grant you the monthly view.
     * @param event
     * @throws SQLException 
     */    
    @FXML
    void onMonthlyView(ActionEvent event) throws SQLException {
        format = 1;
        monthLabel.setText(monthWords(currentMonth));
        //monthlyTable.getItems().clear();
        displayTheTable(format,0);
        adjustColumnWidths(format);
        weeklyTable.setVisible(false);
        dailyTable.setVisible(false);
        monthlyTable.setVisible(true);
    }

    /**
     * Clicking on the daily in the toolbar will grant you the daily view.
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onDailyView(ActionEvent event) throws SQLException {
        format = 3;
        currentDay = 1;
        currentMonth = 1;
        if (currentDay == 1){
            monthLabel.setText(currentDay + "st " + monthWords(currentMonth) + " 2017");
            } else {
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");   
            }
        displayTheTable(format,0);
        //adjustColumnWidths(format);
        weeklyTable.setVisible(false);
        monthlyTable.setVisible(false);
        dailyTable.setVisible(true);
    }

    @FXML
    void onHelpContent(ActionEvent event) {

    }

    @FXML
    void onAbout(ActionEvent event) {

    }
   
    /**
     * Clicking the 'next' button will increase the current month/week/day count
     * depending on the format associated with the current view.
     * @param event
     * @throws SQLException 
     */    
    @FXML
    void onNext(ActionEvent event) throws SQLException {
    if (format == 1){
        if (currentMonth < 12) {   
        currentMonth++;      
        monthlyTable.getItems().clear();
        displayTheTable(1,1);
        monthLabel.setText(monthWords(currentMonth));
    } else {
        log.debug("end of months next");
    }
    } 
    if (format == 2){
      if (!(currentMonth == 12 && currentWeek == 5)){  
        if (currentWeek < 5){
            currentWeek++;
            monthlyTable.getItems().clear();
            displayTheTable(2,1);
            monthLabel.setText(monthWords(currentMonth));
        } else {
            currentMonth++;
            currentWeek = 1;
            monthlyTable.getItems().clear();
            displayTheTable(2,1);
            monthLabel.setText(monthWords(currentMonth));
        }
      }else{
          log.debug("next-week-endofstream");
      }   
    }
    if (format == 3){
        if (currentDay < 31){
            currentDay++;
            dailyTable.getItems().clear();
            displayTheTable(3,1);
            if (currentDay == 1){
            monthLabel.setText(currentDay + "st " + monthWords(currentMonth) + " 2017");
            } else {
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");   
            }
        } else {
            currentMonth++;
            currentDay = 1;
            dailyTable.getItems().clear();
            displayTheTable(3,1);
            if (currentDay == 1){
            monthLabel.setText(currentDay + "st " + monthWords(currentMonth) + " 2017");
            } else {
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");   
            }
        }
    }
        
    }
    
    /**
     * Clicking the 'previous' button will decrease the current month/week/day count
     * depending on the format associated with the current view.
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onPrevious(ActionEvent event) throws SQLException {
    if (format == 1){
        if (currentMonth > 1){    
            currentMonth--;
            monthlyTable.getItems().clear();
            displayTheTable(1,2);
            monthLabel.setText(monthWords(currentMonth));
        } else {
            log.debug("end of months prev");
        }   
    }
    if (format == 2){
      if(!(currentMonth == 1 && currentWeek == 1)){
        if (currentWeek > 1){
            currentWeek--;
            monthlyTable.getItems().clear();
            displayTheTable(2,2);
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");
        } else {
            currentMonth--;
            currentWeek = 5;
            monthlyTable.getItems().clear();
            displayTheTable(2,2);
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");
        }
      } else {
          log.debug("prev-week-endofstream");
      }
    }
    
    if (format == 3){
        if (currentDay > 1){
            currentDay--;
            dailyTable.getItems().clear();
            displayTheTable(3,2);
            if (currentDay == 1){
            monthLabel.setText(currentDay + "st " + monthWords(currentMonth) + " 2017");
            } else if (currentDay == 2){
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");   
            } else {
             monthLabel.setText(currentDay + "rd " + monthWords(currentMonth) + " 2017");     
            }
        } else {
            currentMonth--;
            currentDay = 31;
            dailyTable.getItems().clear();
            displayTheTable(3,2);
            if (currentDay == 1){
            monthLabel.setText(currentDay + "st " + monthWords(currentMonth) + " 2017");
            } else if (currentDay == 2) {
            monthLabel.setText(currentDay + "nd " + monthWords(currentMonth) + " 2017");   
            } else {
            monthLabel.setText(currentDay + "rd " + monthWords(currentMonth) + " 2017");      
            }
        }
    }
    
}
    
    
        /**
         * This method will display the monthly view correctly with the right
         * day placements as well as the correct maximum days each month contains.
         * @param month
         * @return
         * @throws SQLException 
         */
        private List<CalendarRows> correctRows(int month) throws SQLException {
        List<CalendarRows> row = new ArrayList<>();
        switch(month){
            case (1):                 
                row.add(new CalendarRows("1","2","3","4","5","6","7"));
                /*
                * SETTING UP APPOINTMENTS IN EACH DAY OF THE CALENDAR ROW (WEEK)
                * Need 7 lists to track all appointments for each day of the row?
                * Easier way to do it?
                * - Oct 27th
                */
//                  apts = agendaDAO.findAppointmentForAWeek(LocalDate.of(2017, currentMonth, 1));                 
//                String concatedTitles = "";
//                List<Appointment> sameDayApts = new ArrayList<>();
//                for (int z = 0; z<apts.size();z++){                                    
//                    concatedTitles += apts.get(z).getTitle() + "\n";  
//                    sameDayApts.add(apts.get(z));                                    
//                }
//                    monthly.add(new CalendarRows( concatedTitles, apts));
                
                row.add(new CalendarRows("8","9","10","11","12","13","14"));
                row.add(new CalendarRows("15","16","17","18","19","20","21"));
                row.add(new CalendarRows("22","23","24","25","26","27","28"));                
                row.add(new CalendarRows("29","30","31","","","",""));
                break;
            case (2):
                row.add(new CalendarRows("", "", "", "1", "2", "3", "4"));
                row.add(new CalendarRows("5","6","7","8","9","10","11"));
                row.add(new CalendarRows("12","13","14","15","16","17","18"));
                row.add(new CalendarRows("19","20","21","22","23","24","25"));                
                row.add(new CalendarRows("26","27","28","","","",""));
                break;
            case (3): 
                row.add(new CalendarRows("", "", "", "1", "2", "3", "4"));
                row.add(new CalendarRows("5","6","7","8","9","10","11"));
                row.add(new CalendarRows("12","13","14","15","16","17","18"));
                row.add(new CalendarRows("19","20","21","22","23","24","25"));                
                row.add(new CalendarRows("26","27","28","29","30","31",""));
                break;
            case (4): 
                row.add(new CalendarRows("", "", "", "", "", "", "1"));
                row.add(new CalendarRows("2","3","4","5","6","7","8"));
                row.add(new CalendarRows("9","10","11","12","13","14","15"));
                row.add(new CalendarRows("16","17","18","19","20","21","22"));                
                row.add(new CalendarRows("23","24","25","26","27","28","29"));
                row.add(new CalendarRows("30","","","","","",""));
                break;
            case (5): 
                row.add(new CalendarRows("", "1", "2", "3", "4", "5", "6"));
                row.add(new CalendarRows("7","8","9","10","11","12","13"));
                row.add(new CalendarRows("14","15","16","17","18","19","20"));
                row.add(new CalendarRows("21","22","23","24","25","26","27"));                
                row.add(new CalendarRows("28", "29", "30", "31", "", "", ""));
                break;
            case (6):
                row.add(new CalendarRows("", "", "", "", "1", "2", "3"));
                row.add(new CalendarRows("4","5","6","7","8","9","10"));
                row.add(new CalendarRows("11","12","13","14","15","16","17"));
                row.add(new CalendarRows("18","19","20","21","22","23","24"));                
                row.add(new CalendarRows("25","26","27","28","29","30",""));
                break;
            case (7):
                row.add(new CalendarRows("", "", "", "", "", "", "1"));
                row.add(new CalendarRows("2","3","4","5","6","7","8"));
                row.add(new CalendarRows("9","10","11","12","13","14","15"));
                row.add(new CalendarRows("16","17","18","19","20","21","22"));                
                row.add(new CalendarRows("23","24","25","26","27","28","29"));
                row.add(new CalendarRows("30","31","","","","",""));
                break;
            case (8): 
                row.add(new CalendarRows("", "", "1", "2", "3", "4", "5"));
                row.add(new CalendarRows("6","7","8","9","10","11","12"));
                row.add(new CalendarRows("13","14","15","16","17","18","19"));
                row.add(new CalendarRows("20","21","22","23","24","25","26"));                
                row.add(new CalendarRows("27","28","29","30","31","",""));
                break;
            case (9):
                row.add(new CalendarRows("", "", "", "", "", "1", "2"));
                row.add(new CalendarRows("3","4","5","6","7","8","9"));
                row.add(new CalendarRows("10","11","12","13","14","15","16"));
                row.add(new CalendarRows("17","18","19","20","21","22","23"));                
                row.add(new CalendarRows("24","25","26","27","28","29","30"));
                break;
            case (10):
                row.add(new CalendarRows("1","2","3","4","5","6","7"));
                row.add(new CalendarRows("8","9","10","11","12","13","14"));
                row.add(new CalendarRows("15","16","17","18","19","20","21"));
                row.add(new CalendarRows("22","23","24","25","26","27","28"));                
                row.add(new CalendarRows("29","30","31","","","",""));
                break;
            case (11): 
                row.add(new CalendarRows("", "", "", "1", "2", "3", "4"));
                row.add(new CalendarRows("5","6","7","8","9","10","11"));
                row.add(new CalendarRows("12","13","14","15","16","17","18"));
                row.add(new CalendarRows("19","20","21","22","23","24","25"));                
                row.add(new CalendarRows("26","27","28","29","30","",""));
                break;
            case (12): 
                row.add(new CalendarRows("", "", "", "", "", "1", "2"));
                row.add(new CalendarRows("3","4","5","6","7","8","9"));
                row.add(new CalendarRows("10","11","12","13","14","15","16"));
                row.add(new CalendarRows("17","18","19","20","21","22","23"));                
                row.add(new CalendarRows("24","25","26","27","28","29","30"));
                row.add(new CalendarRows("31","","","","","",""));
                break;
            default: log.debug("Something went wrong with correctRows");                   
        }
        return row;
    }
    
    /**
     * A simple list of months associated with their numeric value.
     * Mostly used by the correctRows() method.
     * @param month
     * @return 
     */
    public String monthWords(int month){
        String word;
        switch(month){
            case (1): word = "January";
                break;
            case (2): word = "February";
                break;
            case (3): word = "March";
                break;
            case (4): word = "April";
                break;
            case (5): word = "May";
                break;
            case (6): word = "June";
                break;
            case (7): word = "July";
                break;
            case (8): word = "August";
                break;
            case (9): word = "September";
                break;
            case (10): word = "October";
                break;
            case (11): word = "November";
                break;
            case (12): word = "December";
                break;
            default: log.debug("Something went wrong - monthWORDS");
            word = "Error";
        }
        return word;
    }
    
    
    /**
     * Gets the maximum day of the month, for the next/prev button to prevent
     * going into the unknown.
     * @param onnextprev
     * @return 
     */
    public int maxDayMonth(int onnextprev){
        
      try {
          return LocalDate.of(2017, currentMonth, currentDay).lengthOfMonth();
      } catch (DateTimeException e){
          try {
          return LocalDate.of(2017, currentMonth, --currentDay).lengthOfMonth();
          } catch (DateTimeException e2){
              try {
              return LocalDate.of(2017, currentMonth, --currentDay).lengthOfMonth();                  
                } catch (DateTimeException e3){
                  return LocalDate.of(2017, currentMonth, --currentDay).lengthOfMonth();
              }
          }
      }
        
    }
  
    /**
     * Whenever a single cell is selected, it will change the value of the cell.
     * @param change 
     */
    private void showSingleCellDetails(ListChangeListener.Change<? extends TablePosition> change) {
        if (theCells.size() > 0) {
            TablePosition selectedCell = theCells.get(0);
            TableColumn column = selectedCell.getTableColumn();
            int rowIndex = selectedCell.getRow();
            Object data = column.getCellObservableValue(rowIndex).getValue();
            dialogSingleCellDetails((String) data);
            log.info("value = " + (String) data);
        }
    }

    /**
     * Whenever a single cell is clicked, this will be displayed.
     * @param data 
     */
    private void dialogSingleCellDetails(String data) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        // Non-modal setting
        dialog.initModality(Modality.NONE);
        dialog.setTitle("Single cell");
        dialog.setHeaderText("Single cell");
        dialog.setContentText(data);
        dialog.show();
    }
    
    /**
     * Whenever a cell is clicked in the monthly view, all this information
     * will show.
     * @param rowBean 
     */
    private void showColDetails(CalendarRows rowBean) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        // Non-modal setting
        dialog.initModality(Modality.NONE);
        dialog.setTitle("Appointments for " + rowBean.getWeek() + " of " + currentMonth + " 2017");
        dialog.setHeaderText("Appointments");
        dialog.show();
    }
      
    /**
     * Whenever a cell is clicked in the daily view, a bunch of information about
     * the appointment is displayed according to the appointment that is in 
     * the cell.
     * @param dailyBean 
     */
    private void showColDetails(dailyBean dailyBean){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Appointments");
        if (!dailyBean.getDailyApts().isEmpty()){
        for (int i = 0; i < dailyBean.getDailyApts().size(); i++){
            dialog.setContentText(i + ") " + dailyBean.getDailyApts().get(i).getStartTime() +
                    "\n" +
                    dailyBean.getDailyApts().get(i).getTitle() +
                    " at " +
                    dailyBean.getDailyApts().get(i).getLocation() +
                    "\n"); 
        dialog.setHeaderText("Appointments at " + dailyBean.getTime());    
        dialog.show();
        }

    }
    }
    
    
    /**
     * Whenever the cell is clicked, it will be highlighted and cannot be 
     * clicked again until another is clicked upon.
     * @param tc 
     */
    private void renderCell(TableColumn tc) {

        tc.setCellFactory(column -> {
            return new TableCell<Appointment, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setTextFill(Color.CHOCOLATE);
                        //setStyle("-fx-background-color: yellow");
                    }
                }
            };
        });

    }
    
    /**
     * Fills the daily view calendar with the appropriate 00:00-24:00 times as 
     * well as the appointments associated to them.
     * @param onnextprev
     * @return
     * @throws SQLException 
     */
    private ObservableList<dailyBean> fillDailyCalendar(int onnextprev) throws SQLException {
        List<Appointment> apts = null;
        List<Appointment> aptsForTime;

        for (int i = 0; i < 24; i++){
           for (int j = 0; j <= 1; j++){                                                  
                   try {
                        apts = new ArrayList<>(); 
                        apts = agendaDAO.findAppointmentForADay(LocalDate.of(2017, currentMonth, currentDay));
                    
                   } catch (DateTimeException e){
                       if (onnextprev == 1){
                           currentDay = 1;                       
                           currentMonth++;
                       }
                       if (onnextprev == 2){                           
                       currentDay = maxDayMonth(2);                     
                       }
                   }
                   
                   if (!apts.isEmpty()) {
                       aptsForTime = new ArrayList<>();                    
                       if (j == 0){       
                           
                           aptsForTime = agendaDAO.findAppointmentsFromStartBetween29Mins(Timestamp.valueOf(LocalDateTime.of(2017, currentMonth, currentDay, i, 0))); 
                          
                            if (!aptsForTime.isEmpty()){
                                    String concatedTitles = "";
                                    List<Appointment> dailyApts = new ArrayList<>();
                                for (int z = 0; z<aptsForTime.size();z++){                                    
                                    concatedTitles += aptsForTime.get(z).getTitle() + "\n";  
                                    dailyApts.add(aptsForTime.get(z));                                    
                                }
                                daily.add(new dailyBean(i + ":00", concatedTitles, dailyApts));
                                
                            } else {
                                daily.add(new dailyBean(i + ":00", "", new ArrayList<Appointment>()));
                            }
                       } else {
                           
                           aptsForTime = agendaDAO.findAppointmentsFromStartBetween29Mins(Timestamp.valueOf(LocalDateTime.of(2017, currentMonth, currentDay, i, 30))); 

                            if (!aptsForTime.isEmpty()){
                                String concatedTitles = "";
                                List<Appointment> dailyApts = new ArrayList<>();
                                for (int z = 0; z<aptsForTime.size();z++){
                                    concatedTitles += aptsForTime.get(z).getTitle() + "\n";
                                    dailyApts.add(aptsForTime.get(z));
                                }
                                daily.add(new dailyBean(i + ":30", concatedTitles, dailyApts));
                                
                            } else {
                       daily.add(new dailyBean(i + ":30", "", new ArrayList<Appointment>()));
                       }
                        
                   }

                   } else {
                   if (j == 0){
                           daily.add(new dailyBean(i + ":00", "", new ArrayList<Appointment>()));
                       } else {
                           daily.add(new dailyBean(i + ":30", "", new ArrayList<Appointment>()));
                       }
           
        }
        
    } // end of :00/:30
 } // end of 24hr
        return daily;
} // end of func

    
    
   /**
    * Fills the calendar according to which format is selected.
    * 1 -> Monthly || 2 -> Weekly
    * Using the correctRows() method to figure out how to fill the calendarRows.
    * @param format
    * @param week
    * @return 
    * @throws SQLException 
    */ 
    private ObservableList<CalendarRows> fillCalendar(int format, int week) throws SQLException {
        ObservableList<CalendarRows> rowBean = FXCollections
                .observableArrayList();
        List<CalendarRows> rows = correctRows(currentMonth);
    if (format == 1){ //Monthly view
            rows = correctRows(currentMonth);
            for (int i = 0; i < rows.size();i++){
            rowBean.add(rows.get(i));
            }
            return rowBean;
    } if (format == 2){ //weeklyView
        rows = correctRows(currentMonth);
        for (int i = 0; i < rows.size();i++){
            if (week == i){
            rowBean.add(rows.get(i));
            }
            }
            return rowBean;      
    }
    return rowBean;
    }

    /**
     * Adjusting the colum widths according to either monthly/weekly view.
     * @param format 
     */
    private void adjustColumnWidths(int format) {
    
    double width = monthlyTable.getPrefWidth();
    
    if (format == 1){
        col1Col.setPrefWidth(width / 5.0);
        col2Col.setPrefWidth(width / 5.0);
        col3Col.setPrefWidth(width / 5.0);
        col4Col.setPrefWidth(width / 5.0);
        col5Col.setPrefWidth(width / 5.0);
        col6Col.setPrefWidth(width / 5.0);
        col7Col.setPrefWidth(width / 5.0);

        monthlyTable.setFixedCellSize(width / 5.0);
    }
    if (format == 2){
        col1Col.setPrefWidth(width / 2.0);
        col2Col.setPrefWidth(width / 2.0);
        col3Col.setPrefWidth(width / 2.0);
        col4Col.setPrefWidth(width / 2.0);
        col5Col.setPrefWidth(width / 2.0);
        col6Col.setPrefWidth(width / 2.0);
        col7Col.setPrefWidth(width / 2.0);
       

        weeklyTable.setFixedCellSize(width);
    
    } 
    
    }
    
    /**
     * 
     * @param format
     * @param onnextprev 1-> onNext() || 2-> onPrevious()
     * @throws SQLException 
     */
     public void displayTheTable(int format, int onnextprev) throws SQLException {
        if (format == 1) {
            monthlyTable.setItems(fillCalendar(format,0));
        }
        if (format == 2){
            weeklyTable.setItems(fillCalendar(format,currentWeek));
        }
        if (format == 3){
            dailyTable.setItems(fillDailyCalendar(onnextprev));
        }
    }
   
    
  
   /**
     * Sets a reference to the agendaDAO object that retrieves data from the
     * database.
     */

    public void setAgendaDAO(iAgendaDAO agendaDAO) {         
        this.agendaDAO = agendaDAO;
    }
    
    
    public void setAgendaDAOData(iAgendaDAO agendaDAO) {
        this.agendaDAO = agendaDAO;
    }
    
    
    /**
     * Dialog setters for AgendaApp class.
     * Shows the dialog when the onForm clicks are clicked.
     * @param dialog 
     */
    
    public void setAptDialog(Dialog dialog){
        this.dialogApt = dialog;        
    }
    public void setAptGrpDialog(Dialog dialog){
        this.dialogAptGrp = dialog;
    }
    public void setEmailDialog(Dialog dialog){
        this.dialogEmail = dialog;
    }
    public void setConfigDialog(Dialog dialog){
        this.dialogConfig = dialog;
    }
    
    
    
    
  
}

