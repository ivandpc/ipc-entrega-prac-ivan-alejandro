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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
    private Button facturaButton;
    @FXML
    private ImageView factura;
    private Charge charge;
    @FXML
    private Button confirmarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private GridPane fondo;
    @FXML
    private Label error;
    @FXML
    private Button añadirCategoriaButton;
    @FXML
    private StackPane añadirCategoriaPanel;
    @FXML
    private TextField nombreCategoria;
    @FXML
    private TextField descripcionCategoria;
    @FXML
    private Button confirmarCategoriaButton;
    @FXML
    private Label errorCategoria;
    Acount acount;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            acount = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            System.err.println(ex);
        }
        coste.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*(\\.\\d{0,2})?")) {
                coste.setText(oldValue);
            }
        });
        unidadesText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                unidadesText.setText(oldValue);
            }
        });
        nombreText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) nombreText.setText(oldValue);
        });
        descripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) descripcion.setText(oldValue);
        });
        nombreCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) nombreCategoria.setText(oldValue);
        });
        descripcionCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) descripcionCategoria.setText(oldValue);
        });
        coste.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*(\\.\\d{0,2})?")) {
                coste.setText(oldValue);
            }
            if (newValue.length() > 10) coste.setText(oldValue);
        });
        unidadesText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                unidadesText.setText(oldValue);
            }
            if (newValue.length() > 10) unidadesText.setText(oldValue);
        });
        añadirCategoriaPanel.setVisible(false);
    }
    
    private void inicializarCategorias() {
        
        try {
            List<Category> categories = acount.getUserCategories();
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
        } catch (AcountDAOException e) {
            error.setText("Error al actualizar la categoria");
            System.err.println(e);
        }
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
        if (charge.getImageScan() != null) {
            factura.setImage(charge.getImageScan());
            factura.setFitWidth(200);
            factura.setFitHeight(375);
        }
    }

    @FXML
    private void nextNombre(ActionEvent event) {
        descripcion.requestFocus();
    }

    @FXML
    private void nextDescripcion(ActionEvent event) {
        categoriaText.requestFocus();
    }

    @FXML
    private void nextCoste(ActionEvent event) {
        unidadesText.requestFocus();
    }

    @FXML
    private void nextUnidades(ActionEvent event) {
        fechaGasto.requestFocus();
    }

    @FXML
    private void nextFecha(ActionEvent event) {
        facturaButton.requestFocus();
    }

    private File file;
    private Image avatar;

    @FXML
    private void añadirFactura(ActionEvent event) {
        FileChooser fc = new FileChooser();

        try {
            file = fc.showOpenDialog(null);
            String url = file.getAbsolutePath();
            if (!url.contains(".png")) {
                throw new FileNotFoundException();
            }
            avatar = new Image(new FileInputStream(url));
            factura.setImage(avatar);
            factura.setFitWidth(200);
            factura.setFitHeight(375);
        } catch (FileNotFoundException ex) {
            error.setText("No se ha podido importar la factura");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void nuevoGastoPanelClicked(MouseEvent event) {
        nuevoGastoPanel.requestFocus();
    }

    @FXML
    private void confirmar(ActionEvent event) throws AcountDAOException, IOException, InstantiationException, IllegalAccessException {

        if (!nombreText.getText().isEmpty()) {
            charge.setName(nombreText.getText());
        }
        if (!descripcion.getText().isEmpty()) {
            charge.setDescription(descripcion.getText());
        }
        charge.setCategory(categoriaText.getValue());
        if (!coste.getText().isEmpty()) {
            charge.setCost(Double.parseDouble(coste.getText()));
        }
        if (!unidadesText.getText().isEmpty()) {
            charge.setUnits(Integer.parseInt(unidadesText.getText()));
        }
        charge.setDate(fechaGasto.getValue());
        if (factura.getImage() != null) {
            charge.setImageScan(factura.getImage());
        }
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void fondoClicked(MouseEvent event) {
        fondo.requestFocus();
    }

    

    @FXML
    private void añadirCategoria(ActionEvent event) {
        añadirCategoriaPanel.setVisible(!añadirCategoriaPanel.visibleProperty().getValue());
    }

    @FXML
    private void nextNombreCategoria(ActionEvent event) {
        
    }

    @FXML
    private void nextDescripcionCategoria(ActionEvent event) {
        
    }

    @FXML
    private void confirmarCategoria(ActionEvent event) {
        if (nombreCategoria.getText().isBlank()) {
            errorCategoria.setText("Debes introducir un nombre");
        } else {
            try {
                if (!acount.registerCategory(nombreCategoria.getText(), descripcionCategoria.getText())) {
                    errorCategoria.setText("Error al añadir la categoria");
                } else {
                    nombreCategoria.setText("");
                    descripcionCategoria.setText("");
                    añadirCategoriaPanel.setVisible(false);
                    inicializarCategorias();
                }
            } catch (AcountDAOException ex) {
                errorCategoria.setText("La categoria ya existe");
            }
        }
    }
}
