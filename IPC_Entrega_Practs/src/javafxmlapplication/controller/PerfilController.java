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
    private Label username;
    @FXML
    private PasswordField password1;

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
        toolbarUsername.textProperty().bindBidirectional(username.textProperty());
        toolbarUsername.setText(user.getNickName());

        perfil.setImage(user.getImage());
        nombre.setPromptText(user.getName());
        apellidos.setPromptText(user.getSurname());
        email.setPromptText(user.getEmail());
        
        nombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 25) nombre.setText(oldValue);
        });
        apellidos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 25) apellidos.setText(oldValue);
        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 25) email.setText(oldValue);
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 25) password.setText(oldValue);
        });
        password1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 25) password1.setText(oldValue);
        });
    }

    @FXML
    private void confirmar(ActionEvent event) throws IOException {
        if (!password.getText().isEmpty() && password.getText().length() < 6) {
            error.setText("La contraseña debe tener más de 6 caracteres");
        } else {
            user.setImage(perfil.getImage());
            if (!nombre.getText().isBlank()) {
                user.setName(nombre.getText());
            }
            if (!apellidos.getText().isBlank()) {
                user.setSurname(apellidos.getText());
            }
            if (!email.getText().isBlank()) {
                user.setEmail(email.getText());
            }

            if (password.getText().equals(password1.getText())) {
                if (!password.getText().isEmpty()) user.setPassword(password.getText());

                Parent root = FXMLLoader.load(getClass().getResource("../view/MainApp.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, ((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
                stage.setScene(scene);
                stage.setResizable(true);
            } else {
                error.setText("Las contraseñas no coinciden");
            }
        }
    }

    @FXML
    public void singleFileChooser(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File file = fc.showOpenDialog(null);

        if (file != null) {
            String url = file.getAbsolutePath();
            Image avatar = new Image(new FileInputStream(url));
            perfil.setImage(avatar);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainApp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, ((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
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
    private void nextPassword(ActionEvent event) throws IOException {
    }

    @FXML
    private void logout(ActionEvent event) throws AcountDAOException, IOException {
        if (Acount.getInstance().logOutUser()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            error.getScene().getWindow().hide();
        }
    }
}
