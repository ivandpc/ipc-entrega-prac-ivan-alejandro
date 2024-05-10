/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 *
 * @author I y A
 */
public class FXMLDocumentController implements Initializable {

    private Label labelMessage;
    @FXML
    private Button registro;
    @FXML
    private Label mensaje;
    @FXML
    private Button iniciosesion;
    @FXML
    private TextField usertext;
    @FXML
    private PasswordField passwordtext;

    //=========================================================
    // event handler, fired when button is clicked or 
    //                      when the button has the focus and enter is pressed
    private void handleButtonAction(ActionEvent event) {
        labelMessage.setText("Hello, this is your first JavaFX project - IPC");
    }

    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML

    private void incioSesion(ActionEvent event) throws AcountDAOException, IOException {
        if (Acount.getInstance().logInUserByCredentials(usertext.getText(), passwordtext.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void registro(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}
