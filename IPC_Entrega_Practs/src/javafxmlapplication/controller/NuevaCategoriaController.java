/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class NuevaCategoriaController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private Button conf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void nextUser(ActionEvent event) {
    }

    @FXML
    private void confirmar(ActionEvent event) throws AcountDAOException, IOException {
        if (Acount.getInstance().registerCategory(nombre.getText(), descripcion.getText())) {
            Stage stage = (Stage) conf.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }


}
