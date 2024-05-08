/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image;
import model.Acount;
import model.AcountDAOException;

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
    private ImageView fotos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void aceptarRegistro(ActionEvent event) throws AcountDAOException, IOException {

        String url = File.separator + "images" + File.separator + "woman.PNG";
        Image avatar = new Image(new FileInputStream(url));
        fotos.imageProperty().setValue(new image(new FileInputStream(url)));
        Acount.getInstance().registerUser(nombre.getText(), apellidos.getText(), email.getText(), usuario.getText(), password.getText(), fotos.getImage(), LocalDate.MAX);
    }

}
