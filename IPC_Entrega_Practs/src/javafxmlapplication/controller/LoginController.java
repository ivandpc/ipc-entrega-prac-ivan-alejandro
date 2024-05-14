/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author alexa
 */
public class LoginController implements Initializable {

    private Label labelMessage;
    @FXML
    private Label mensaje;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private TextField userText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Label error;
    @FXML
    private Button iniciosesionButton;
    @FXML
    private Button registroButton;
    @FXML
    private GridPane fondo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        SimpleBooleanProperty userVacio = new SimpleBooleanProperty(true);
        SimpleBooleanProperty passwordVacio = new SimpleBooleanProperty(true);
        
        userText.textProperty().addListener((obv, ant, act) -> {
            if (act.length() == 0) {
                userVacio.setValue(true);
                userText.setStyle("-fx-background-color: #ffa8a8;");
            }
            else {
                userVacio.setValue(false);
                userText.setStyle("");
            }
        });
        passwordText.textProperty().addListener((obv, ant, act) -> {
            if (act.length() == 0) { 
                passwordVacio.setValue(true);
                passwordText.setStyle("-fx-background-color: #ffa8a8;");
            }
            else {
                passwordVacio.setValue(false);
                passwordText.setStyle("");
            }
        });
        
        iniciosesionButton.disableProperty().bind(userVacio.or(passwordVacio));
        
    }

    @FXML
    private void inicioSesion(ActionEvent event) throws AcountDAOException, IOException {
        if (Acount.getInstance().logInUserByCredentials(userText.getText(), passwordText.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("../view/MainApp.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            error.setText("Error en las credenciales");
        }
    }

    @FXML
    private void registro(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/registro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void fondoClicked(MouseEvent event) {
        fondo.requestFocus();
    }

    @FXML
    private void nextUser(ActionEvent event) {
        if (!userText.getText().isBlank()) passwordText.requestFocus();
    }

    @FXML
    private void nextPassword(ActionEvent event) throws AcountDAOException, IOException {
        if (!passwordText.getText().isBlank()) inicioSesion(event);
    }

    
    
}
