package managementsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managementsystem.db.DataBaseHandler;



public class MainController implements Initializable {

    ObservableList<Guests> list = FXCollections.observableArrayList();

    
    @FXML
    private TableView<Guests> tableview;
    @FXML
    private TableColumn<Guests, String> nameCol;
    @FXML
    private TableColumn<Guests, String> surnameCol;
    @FXML
    private TableColumn<Guests, String> countryCol;
    @FXML
    private TableColumn<Guests, String> roomnumberCol;
    @FXML
    private TableColumn<Guests, String> roomtypeCol;
    @FXML
    private TableColumn<Guests, LocalDate> checkinCol;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initCol();
        
        loadData();
       
    }    
    
     private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        roomnumberCol.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        roomtypeCol.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
        checkinCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
     
      private void loadData() {
          list.clear();
          
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM GUESTS";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String naMe = rs.getString("NAME");
                String surnaMe = rs.getString("SURNAME");
                String countRy = rs.getString("COUNTRY");
                String roomNumber = rs.getString("ROOMNUMBER");
                String roomType = rs.getString("ROOMTYPE");
                String daTe = rs.getString("CHECKIN");
                
                list.add(new Guests(naMe, surnaMe, countRy, roomNumber, roomType, daTe));
             
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddGuestController.class.getName()).log(Level.SEVERE, null, ex);
    }
        tableview.setItems(list);
        
    }

    @FXML
    private void addGuestAction(ActionEvent event) {
           try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managementsystem/gui/AddGuest.fxml"));
            Parent parent = loader.load();
            
            AddGuestController controller = (AddGuestController) loader.getController();
            
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Book Number");
            stage.setScene(new Scene(parent));
            stage.show();
            
            stage.setOnCloseRequest( (e)->{
                handleRefresh(new ActionEvent());
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } }

//        loadWindow("/managementsystem/gui/AddGuest.fxml", "Book Now");
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/managementsystem/gui/AddGuest.fxml", "Book Now"));
//            Stage stage = new Stage(StageStyle.DECORATED);
//            stage.setTitle("Add Guest");
//            stage.setScene(new Scene(parent));
//            stage.show();
//             stage.setOnCloseRequest( (e)->{
//                handleRefresh(new ActionEvent());
//            });
//        } catch (IOException ex){
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
 

    

    @FXML
    private void editGuestAction(ActionEvent event) {
         Guests selectedforediting = tableview.getSelectionModel().getSelectedItem();
        if (selectedforediting == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Guest Selected");
            alert.showAndWait();
            
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managementsystem/gui/AddGuest.fxml"));
            Parent parent = loader.load();
            
            AddGuestController controller = (AddGuestController) loader.getController();
            controller.inflateUI(selectedforediting);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Guest");
            stage.setScene(new Scene(parent));
            stage.show();
            
            stage.setOnCloseRequest( (e)->{
                handleRefresh(new ActionEvent());
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteGuestAction(ActionEvent event) {
        
        Guests selectedfordeleting = tableview.getSelectionModel().getSelectedItem();
        if (selectedfordeleting == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No Guest Selected");
            alert.showAndWait();
            
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Guest");
        alert.setContentText("Are you sure for deleting " + selectedfordeleting.getName() + "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DataBaseHandler.getInstance().deleteGuest(selectedfordeleting);
            if (result) {
            Alert alerT = new Alert(Alert.AlertType.INFORMATION);
            alerT.setHeaderText(null);
            alerT.setContentText("Guest " + selectedfordeleting.getName() +  " deleted successfully");
            alerT.showAndWait();
            list.remove(selectedfordeleting);
            } else {
            Alert aler = new Alert(Alert.AlertType.ERROR);
            aler.setHeaderText(null);
            aler.setContentText("Failed");
            aler.showAndWait();
                
            }
            
        } else {
            Alert alErt = new Alert(Alert.AlertType.INFORMATION);
            alErt.setHeaderText(null);
            alErt.setContentText("Operation cancelled");
            alErt.showAndWait();
        }
    }

    @FXML
    private void logoutAction(ActionEvent event) throws Exception {
        
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/managementsystem/gui/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        
        
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
        
    }

     
        public static class Guests {
        private final  SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty country;
        private final SimpleStringProperty roomnumber;
        private final SimpleStringProperty roomtype;
        private final SimpleStringProperty date;
        
        Guests(String naMe, String surnaMe, String countRy, String roomNumber, String roomType, String daTe) {
            this.name = new SimpleStringProperty(naMe);
            this.surname = new SimpleStringProperty(surnaMe);
            this.country = new SimpleStringProperty(countRy);
            this.roomnumber = new SimpleStringProperty(roomNumber);
            this.roomtype = new SimpleStringProperty(roomType);
            this.date = new SimpleStringProperty(daTe);
        }

        public String getName() {
            return name.get();
        }

        public String getSurname() {
            return surname.get();
        }

        public String getCountry() {
            return country.get();
        }

        public String getRoomnumber() {
            return roomnumber.get();
        }

        public String getRoomtype() {
            return roomtype.get();
        }

        public String getDate() {
            return date.get();
        }
        
    }
    
}
