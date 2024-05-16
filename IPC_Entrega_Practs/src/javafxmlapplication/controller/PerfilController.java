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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
import model.User;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class PerfilController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField email;
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
    private Button cancelarButton;
    @FXML
    private Button confirmarBotton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private File f;
    private Image avatar;
    private User user = null;
    @FXML
    private MenuButton toolbarUsername;
    @FXML
    private ImageView toolbarImage;
    @FXML
    private TextField password2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (Exception e) {
            error.setText("Error al obtener el usuario registrado");
            System.err.println(e);
            System.exit(-1);
        }
        toolbarImage.setImage(user.getImage());
        toolbarUsername.setText(user.getNickName());

        perfil.setImage(user.getImage());
        nombre.setText(user.getName());
        apellidos.setText(user.getSurname());
        email.setText(user.getEmail());
    }

    @FXML
    private void confirmar(ActionEvent event) throws AcountDAOException, IOException {
        if (nombre.getText().isBlank()) {
            error.setText("Debes introducir un nombre.");
        } else if (apellidos.getText().isBlank()) {
            error.setText("Debes introducir los apellidos.");
        } else if (email.getText().isBlank()) {
            error.setText("Debes introducir un email.");
        } else if (!password.getText().equals(password2.getText())) {
            error.setText("Las contraseñas deben ser iguales");
        } else {
            user.setImage(perfil.getImage());
            user.setName(nombre.getText());
            user.setSurname(apellidos.getText());
            user.setEmail(email.getText());
            if (!password.getText().isBlank()) {
                user.setPassword(password.getText());
            }

            Parent root = FXMLLoader.load(getClass().getResource("../view/MainApp.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root,((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
            stage.setScene(scene);
            stage.setResizable(true);
        }

    }

    @FXML
    private void singleFileChooser(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        f = fc.showOpenDialog(null);
        String url = f.getAbsolutePath();
        avatar = new Image(new FileInputStream(url));
        perfil.setImage(avatar);

    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainApp.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root,((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
            stage.setScene(scene);
            stage.setResizable(true);
    }

    @FXML
    private void fondoClicked(MouseEvent event) {
        fondo.requestFocus();
    }

    @FXML
    private void nextNombre(ActionEvent event) {
        if (!nombre.getText().isBlank()) {
            apellidos.requestFocus();
        }
    }

    @FXML
    private void nextApellidos(ActionEvent event) {
        if (!apellidos.getText().isBlank()) {
            email.requestFocus();
        }
    }

    @FXML
    private void nextEmail(ActionEvent event) {
        if (!email.getText().isBlank()) {
            password.requestFocus();
        }
    }

    @FXML
    private void nextPassword(ActionEvent event) {
        if (!usuario.getText().isBlank()) {
            password2.requestFocus();
        }
    }

    @FXML
    private void nextPassword2(ActionEvent event) throws AcountDAOException, IOException {
        if (!password.getText().isBlank()) {
            confirmar(event);
        }
    }

    @FXML
    private void logout(ActionEvent event) throws AcountDAOException, IOException {
        if (Acount.getInstance().logOutUser()) {
            Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root,((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
            stage.setScene(scene);
            stage.setResizable(true);
        }
    }
}
