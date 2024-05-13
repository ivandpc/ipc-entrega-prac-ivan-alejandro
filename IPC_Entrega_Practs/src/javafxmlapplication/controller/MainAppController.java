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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.Category;
import model.Charge;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author ivand
 */
public class MainAppController implements Initializable {

    @FXML
    private TableColumn<Charge, String> nombre;
    @FXML
    private TableColumn<Charge, Category> categoria;
    @FXML
    private TableColumn<Charge, Integer> unidades;
    @FXML
    private TableColumn<Charge, Double> precio;
    @FXML
    private TableView<Charge> tabla;
    private ObservableList<Charge> datos = null; // Colección vinculada a la vista.
    @FXML
    private TableColumn<?, ?> fecha;
    @FXML
    private MenuButton toolbarUsername;
    @FXML
    private ImageView toolbarImage;
    
    private Acount acount = null;
    private User user = null;
    @FXML
    private TextField buscarText;
    @FXML
    private Button filtroButton;
    @FXML
    private StackPane nuevoGastoPanel;
    @FXML
    private TextField descripcion;
    @FXML
    private Button añadirCategoriaButton;
    @FXML
    private TextField coste;
    @FXML
    private TextField unidades1;
    @FXML
    private DatePicker fecha1;
    @FXML
    private Button añadirGastoButton;
    @FXML
    private StackPane añadirCategoriaPanel;
    @FXML
    private TextField nombreCategoria;
    @FXML
    private TextField descripcionCategoria;
    @FXML
    private Button confirmarCategoriaButton;
    @FXML
    private StackPane filtroPanel;
    @FXML
    private ImageView factura;
    @FXML
    private TextField nombreText;
    @FXML
    private Button facturaButton;
    @FXML
    private Text errorGasto;
    @FXML
    private ChoiceBox<String> categoriaText;
    @FXML
    private Text errorCategoria;
    
    private void actualizarCategorias() throws AcountDAOException {
        List<Category> categories = acount.getUserCategories();
            List<String> cat = new ArrayList<>();
            for (Category c : categories) {
                cat.add(c.getName());
            }
            categoriaText.setItems(FXCollections.observableArrayList(cat));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            acount = Acount.getInstance();
            user = Acount.getInstance().getLoggedUser();
            
            toolbarImage.setImage(user.getImage());
            toolbarUsername.setText(user.getNickName());
            
            categoria.setCellValueFactory(new PropertyValueFactory<>("Category"));
            fecha.setCellValueFactory(new PropertyValueFactory<>("date"));
            precio.setCellValueFactory(new PropertyValueFactory<>("cost"));
            unidades.setCellValueFactory(new PropertyValueFactory<>("units"));
            nombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            actualizarCategorias();
            
            List<Charge> charges = acount.getUserCharges();
            tabla.setItems(FXCollections.observableList(charges));
        } catch (AcountDAOException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void perfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/perfil.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) tabla.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        tabla.getScene().getWindow().hide();
    }
    
    private boolean nuevoGasto = false;
    @FXML
    private void nuevoGasto(ActionEvent event) throws IOException {
        nuevoGasto = !nuevoGasto;
        nuevoGastoPanel.setVisible(nuevoGasto);
        añadirCategoriaPanel.setVisible(false);
    }
    
    private boolean filtro = false;
    @FXML
    private void filtro(ActionEvent event) {
        filtro = !filtro;
        filtroPanel.setVisible(filtro);
    }

    private boolean añadirCategoria = false;
    @FXML
    private void añadirCategoria(ActionEvent event) {
        añadirCategoria = !añadirCategoria;
        añadirCategoriaPanel.setVisible(añadirCategoria);
    }
    
    @FXML
    private void confirmarCategoria(ActionEvent event) {
        if (nombreCategoria.getText().isBlank()) {
            errorCategoria.setText("Debes introducir un nombre");
        } else {
            try {
                if (!acount.registerCategory(nombreCategoria.getText(), descripcionCategoria.getText())) {
                    errorCategoria.setText("La categoria ya existe");
                } else {
                    nombreCategoria.setText("");
                    descripcionCategoria.setText("");
                    añadirCategoriaPanel.setVisible(false);
                    actualizarCategorias();
                }
            } catch (AcountDAOException ex) {
                errorCategoria.setText("Error al añadir la categoria");
                System.err.println(ex);
            }
        }
    }
    
    private File file;
    private Image avatar;
    @FXML
    private void añadirFactura(ActionEvent event) {
        FileChooser fc = new FileChooser();
        
        try {
            file = fc.showOpenDialog(null);
            String url = file.getAbsolutePath();
            if (!url.contains(".png")) throw new FileNotFoundException();
            avatar = new Image(new FileInputStream(url));
            factura.setImage(avatar);
            factura.setFitWidth(20);
            factura.setFitHeight(20);
        } catch (FileNotFoundException ex) {
            errorGasto.setText("No se ha podido importar la factura");
        } catch (Exception e) {}
    }
    
    @FXML
    private void añadirGasto(ActionEvent event) {
        if (nombre.getText().isBlank()) {
            errorGasto.setText("Debes introducir tu nombre");
        } else if (categoria.getText().isBlank()) {
            errorGasto.setText("Debes introducir una categoria");
        } else if (coste.getText().isBlank()) {
            errorGasto.setText("Debes introducir el gasto");
        } else if (unidades.getText().isBlank()) {
            errorGasto.setText("Debes introducir las unidades");
        } else if (fecha.getText().isBlank()) {
            errorGasto.setText("Debes introducir una fecha");
        } else {
            
        }
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
    private void nextNombreCategoria(ActionEvent event) {
    }
    @FXML
    private void nextDescripcionCategoria(ActionEvent event) {
    }
    
    @FXML
    private void nuevoGastoPanelClicked(MouseEvent event) {
        nuevoGastoPanel.requestFocus();
        añadirCategoriaPanel.setVisible(false);
        añadirCategoria = false;
    }

    


}
