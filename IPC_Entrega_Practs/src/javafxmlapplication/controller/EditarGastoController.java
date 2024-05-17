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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class EditarGastoController implements Initializable {

    @FXML
    private AnchorPane anchorpanel;
    @FXML
    private StackPane nuevoGastoPanel;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField descripcion;
    @FXML
    private ChoiceBox<?> categoriaText;
    @FXML
    private Button añadirCategoriaButton;
    @FXML
    private TextField coste;
    @FXML
    private TextField unidadesText;
    @FXML
    private DatePicker fechaGasto;
    @FXML
    private Button añadirGastoButton;
    @FXML
    private Text errorGasto;
    @FXML
    private Button facturaButton;
    @FXML
    private ImageView factura;
    @FXML
    private Button borrarDatosButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void a(MouseEvent event) {
        ChargeHolder holder = ChargeHolder.getInstance();
        Charge charge = holder.getCharge();
        nombreText.setText(charge.getName());
        descripcion.setText(charge.getDescription());
        //categoriaText.setValue(charge.getCategory());
        //coste.setText((String)charge.getCost());

    }

    @FXML
    private void nextNombre(ActionEvent event) {
    }

    @FXML
    private void nextDescripcion(ActionEvent event) {
    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
    }

    @FXML
    private void nextCoste(ActionEvent event) {
    }

    @FXML
    private void nextUnidades(ActionEvent event) {
    }

    @FXML
    private void nextFecha(ActionEvent event) {
    }

    @FXML
    private void añadirGasto(ActionEvent event) {
    }

    @FXML
    private void añadirFactura(ActionEvent event) {
    }

    @FXML
    private void borrarDatos(ActionEvent event) {
    }

    @FXML
    private void nuevoGastoPanelClicked(MouseEvent event) {
    }
}
