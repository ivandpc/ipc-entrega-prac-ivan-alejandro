/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Acount;
import model.AcountDAOException;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class RegistroController implements Initializable {

    @FXML
    private Button registroboton;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField email;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField password;
    @FXML
    private Button seleccion;
    @FXML
    private ImageView perfil;
    @FXML
    private Label error;
    @FXML
    private GridPane fondo;
    @FXML
    private Button cancelarBotton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void aceptarRegistro(ActionEvent event) throws IOException {
        if (nombre.getText().isBlank()) {
            error.setText("Debes introducir tu nombre");
        } else if (apellidos.getText().isBlank()) {
            error.setText("Debes introducir tus apellidos");
        } else if (email.getText().isBlank()) {
            error.setText("Debes introducir tu email");
        } else if (usuario.getText().isBlank() || (usuario.getText().indexOf(' ') == -1)) {
            error.setText("Debes introducir un nombre de usuario valido");
        } else if (password.getText().length() < 6) {
            error.setText("La contraseña debe tener más de 6 carácteres");
        } else if (nombre.getText() != null || apellidos.getText() != null || email.getText() != null || usuario.getText() != null || password.getText() != null || LocalDate.now() != null) {
            try {
                if (Acount.getInstance().registerUser(nombre.getText(), apellidos.getText(), email.getText(), usuario.getText(), password.getText(), avatar, LocalDate.now())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root,((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
                    stage.setScene(scene);
                }
            } catch (AcountDAOException e) {
                error.setText("El usuario ya existe");
                System.err.println(e);
            }
        }
    }
    private File file;
    private Image avatar;

    @FXML
    private void singleFileChooser(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        String url = file.getAbsolutePath();
        avatar = new Image(new FileInputStream(url));
        perfil.setImage(avatar);
        
    }

    @FXML
    private void fondoClicked(MouseEvent event) {
        fondo.requestFocus();
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root,((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
        stage.setScene(scene);
    }

    @FXML
    private void nextNombre(ActionEvent event) {
        if (!nombre.getText().isBlank()) apellidos.requestFocus();
    }

    @FXML
    private void nextApellidos(ActionEvent event) {
        if (!apellidos.getText().isBlank()) email.requestFocus();
    }

    @FXML
    private void nextEmail(ActionEvent event) {
        if (!email.getText().isBlank()) usuario.requestFocus();
    }

    @FXML
    private void nextUser(ActionEvent event) {
        if (!usuario.getText().isBlank()) password.requestFocus();
    }

    @FXML
    private void nextPassword(ActionEvent event) throws IOException {
        if (!usuario.getText().isBlank()) aceptarRegistro(event);
    }

}
