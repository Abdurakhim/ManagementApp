
package managementsystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import managementsystem.db.DataBaseHandler;


public class AddGuestController implements Initializable {
   

    @FXML
    private JFXComboBox<String> roomNumber;
    @FXML
    private JFXComboBox<String> roomType;
    
    DataBaseHandler databaseHandler;
    
    ObservableList<String> list1 = FXCollections.observableArrayList("single" , "twin", "triple", "quad");
    ObservableList<String> list2 = FXCollections.observableArrayList("1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9", "10","11","12","14","15","16","17");
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField surname;
    @FXML
    private JFXTextField country;
    @FXML
    private JFXButton checkin;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXDatePicker date1;
    @FXML
    private AnchorPane rootPane;
    private Boolean isInEditMode = Boolean.FALSE;
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        databaseHandler = DataBaseHandler.getInstance();
        
        checkData();
        
        roomNumber.setItems(list2);
        roomType.setItems(list1);
     
    }    


  

    @FXML
    public void addGuest(ActionEvent event) {
        
        String guestName = name.getText();
        String gSurname = surname.getText();
        String gCountry = country.getText();
        String gRoomNumber = roomNumber.getValue();
        String gRoomType = roomType.getValue();
        LocalDate checkIn = date1.getValue();

        
//        if (gRoomNumber.isEmpty() || guestName.isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("Please fill all fields");
//            alert.showAndWait();
//            return;
//        }
//        
      
//                stmt.executeUpdate("CREATE TABLE GUESTS (name VARCHAR(12), "
//                        + " surname VARCHAR(12), country varchar(200),"
//                        + "roomnumber varchar(20), roomtype varchar(50), mydate date)");
//                System.out.println("Created Table");
//  
        
        if (isInEditMode){
            handleEditOperation();
            return;
        }

        String qu = "INSERT INTO GUESTS VALUES ("+
                  "'" + guestName +"',"+
                  "'" + gSurname +"',"+
                  "'" + gCountry +"',"+
                  "'" + gRoomNumber +"',"+
                  "'" + gRoomType +"',"+
                  "'" + checkIn +"'"+
                ")";
        
        System.out.println(qu);
        if (databaseHandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private void checkData() {
        String qu = "SELECT NAME FROM GUESTS";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while(rs.next()){
                String namex = rs.getString("NAME");
                System.out.println(namex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddGuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }



    private static class Combobox {

        public Combobox() {
        }
    }
    
      @FXML
    private void mainwindow(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ESCAPE)){
      
        }
    }
    
    public void inflateUI(MainController.Guests guest)
    {
        name.setText(guest.getName());
        surname.setText(guest.getSurname());
        country.setText(guest.getCountry());
        roomNumber.setValue(guest.getRoomnumber());
        roomType.setValue(guest.getRoomtype());
        date1.setValue(LocalDate.parse(guest.getDate()));
        isInEditMode = Boolean.TRUE;
    }
    
        private void handleEditOperation() {
        MainController.Guests guests = new MainController.Guests(name.getText(), surname.getText(), country.getText(), roomNumber.getValue(), roomType.getValue(), date1.getValue().toString());
        if (databaseHandler.updateGuest(guests)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED");
            alert.showAndWait();
        }
        }
}
