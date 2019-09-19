
package managementsystem.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import static javafx.scene.paint.Color.RED;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label label;
    
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception {
        if (username.getText().equals("admin") && password.getText().equals("12345")){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/managementsystem/gui/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setTitle("Main Window");
            stage.setScene(scene);
            stage.show();
        }else
        {
            label.setText("Try again");
            label.setTextFill(RED);
            label.setTextAlignment(TextAlignment.CENTER);
        }
    
    }    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

   

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void login(KeyEvent event) throws Exception {
        if(event.getCode().equals(KeyCode.ENTER)){
            if (username.getText().equals("admin") && password.getText().equals("12345")){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/managementsystem/gui/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setTitle("Main Window");
            stage.setScene(scene);
            stage.show();
        }else
        {
            label.setText("Try again");
            label.setTextFill(RED);
            label.setTextAlignment(TextAlignment.CENTER);
        }
        }
    }

    @FXML
    private void cancel(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ESCAPE)){
        System.exit(0);
        }
    }

 

    @FXML
    private void mainexit(KeyEvent event)throws Exception {
        if(event.getCode().equals(KeyCode.ESCAPE)){
        System.exit(0);
        }else{
            if(event.getCode().equals(KeyCode.ENTER)){
            if (username.getText().equals("admin") && password.getText().equals("12345")){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/managementsystem/gui/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setTitle("Main Window");
            stage.setScene(scene);
            stage.show();
        }else
        {
            label.setText("Try again");
            label.setTextFill(RED);
            label.setTextAlignment(TextAlignment.CENTER);
        }
        }
            
        }
    }
    
    
    
}
