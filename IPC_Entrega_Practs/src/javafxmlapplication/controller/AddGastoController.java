/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class AddGastoController implements Initializable {

    @FXML
    private GridPane fondo;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private ChoiceBox<?> categoria;
    @FXML
    private TextField coste;
    @FXML
    private TextField unidades;
    @FXML
    private DatePicker fecha;
    @FXML
    private Button facturr;
    @FXML
    private Button iniciosesionButton;

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
    private void inicioSesion(ActionEvent event) {
    }

    @FXML
    private void fondoClicked(MouseEvent event) {
    }
    
}
