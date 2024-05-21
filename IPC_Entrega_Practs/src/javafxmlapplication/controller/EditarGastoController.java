/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
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
    private ChoiceBox<Category> categoriaText;
    @FXML
    private TextField coste;
    @FXML
    private TextField unidadesText;
    @FXML
    private DatePicker fechaGasto;
    @FXML
    private Text errorGasto;
    @FXML
    private Button facturaButton;
    @FXML
    private ImageView factura;
    private Charge charge;
    @FXML
    private Button confirmarButton;
    @FXML
    private Button cancelarButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setValues(Charge charge) throws AcountDAOException, IOException {
        this.charge = charge;
        nombreText.setText(charge.getName());
        descripcion.setText(charge.getDescription());
        List<Category> categories = Acount.getInstance().getUserCategories();
        categoriaText.setItems(FXCollections.observableArrayList(categories));
        categoriaText.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        categoriaText.setValue(charge.getCategory());
        coste.setText(String.valueOf(charge.getCost()));
        unidadesText.setText(String.valueOf(charge.getUnits()));
        fechaGasto.setValue(charge.getDate());
    }

    @FXML
    private void a(MouseEvent event) {
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
    private void nextCoste(ActionEvent event) {
    }

    @FXML
    private void nextUnidades(ActionEvent event) {
    }

    @FXML
    private void nextFecha(ActionEvent event) {
    }

    @FXML
    private void añadirFactura(ActionEvent event) {
    }

    @FXML
    private void nuevoGastoPanelClicked(MouseEvent event) {
    }

    @FXML
    private void confirmar(ActionEvent event) throws AcountDAOException, IOException, InstantiationException, IllegalAccessException {

        charge.setName(nombreText.getText());
        charge.setDescription(descripcion.getText());
        charge.setCategory(categoriaText.getValue());
        charge.setCost(Double.parseDouble(coste.getText()));
        charge.setUnits(Integer.parseInt(unidadesText.getText()));
        charge.setDate(fechaGasto.getValue());
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
