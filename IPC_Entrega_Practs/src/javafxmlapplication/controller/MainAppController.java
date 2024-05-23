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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Acount;
import model.Charge;
import model.AcountDAOException;
import model.User;
import model.Category;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
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
    private ChoiceBox<Category> categoriaText;
    @FXML
    private Text errorCategoria;
    private TextField unidadestext;
    @FXML
    private Button eliminargastobutton;
    @FXML
    private PieChart piechart;
    @FXML
    private TextField unidadesText;
    @FXML
    private DatePicker fechaGasto;
    @FXML
    private Button borrarDatosButton;
    @FXML
    private Button modificarGastoButton;
    @FXML
    private Text topText;
    @FXML
    private StackedBarChart<String, Double> gastoMensual;
    @FXML
    private CategoryAxis yAxis;
    @FXML
    private NumberAxis xAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Inicializacion de la tabla principal
            inicializarTabla(Acount.getInstance().getUserCharges());
        } catch (AcountDAOException | IOException ex) {
            System.err.println("Error al cargar la tabla: " + ex);
        }
        //Inicializa el panel de añadir gasto
        topText.setText("Bienvendido, " + acount.getLoggedUser().getName());

        inicializarGastoPanel();

    }

    private void inicializarGastoPanel() {
        inicializarCategorias();

        //Filtrar solo los numeros decimales
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
        fechaGasto.setValue(LocalDate.now());

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
            errorGasto.setText("Error al actualizar la categoria");
            System.err.println(e);
        }
    }
    XYChart.Series series1;

    public void inicializarTabla(List<Charge> s) {
        try {
            acount = Acount.getInstance();
            user = Acount.getInstance().getLoggedUser();

            toolbarImage.setImage(user.getImage());
            toolbarUsername.setText(user.getNickName());

            categoria.setCellFactory(column -> {
                return new TableCell<Charge, Category>() {
                    @Override
                    protected void updateItem(Category category, boolean empty) {
                        super.updateItem(category, empty);

                        if (empty || category == null) {
                            setText(null);
                        } else {
                            setText(category.getName());
                        }
                    }
                };
            });

            categoria.setCellValueFactory(new PropertyValueFactory<>("category"));
            fecha.setCellValueFactory(new PropertyValueFactory<>("date"));
            precio.setCellValueFactory(new PropertyValueFactory<>("cost"));
            unidades.setCellValueFactory(new PropertyValueFactory<>("units"));
            nombre.setCellValueFactory(new PropertyValueFactory<>("name"));

            List<Charge> charges = s;
            tabla.setItems(FXCollections.observableList(charges));
            nuevoGastoPanel.setVisible(false);
            añadirCategoriaPanel.setVisible(false);
            List<Category> categories = acount.getUserCategories();
            ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
            series1 = new XYChart.Series<>();
            Map<Month, Double> monthlyExpenses = new HashMap<>();
            double totalMonthlyExpense = 0;

            for (Charge charge : charges) {
                double cost = charge.getCost();
                Month month = charge.getDate().getMonth();
                monthlyExpenses.put(month, monthlyExpenses.getOrDefault(month, 0.0) + cost);
                if (month.equals(LocalDate.now().getMonth())) {
                    totalMonthlyExpense += cost;
                }
                datos.add(new PieChart.Data(charge.getCategory().getName(), cost));
            }

            series1.getData().clear();
            for (Map.Entry<Month, Double> entry : monthlyExpenses.entrySet()) {
                series1.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey().toString()));
            }

            gastoMensual.setTitle("Gasto mensual: " + "\n" + totalMonthlyExpense + "$");

            if (!buscarText.isFocused()) {
                gastoMensual.getData().clear();
                gastoMensual.getData().addAll(series1);
                piechart.setData(datos);
            }
        } catch (AcountDAOException | IOException ex) {
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

    @FXML
    private void nuevoGasto(ActionEvent event) throws IOException {
        nuevoGastoPanel.setVisible(!nuevoGastoPanel.visibleProperty().getValue());
        añadirCategoriaPanel.setVisible(false);
    }

    @FXML
    private void filtro(ActionEvent event) {
        filtroPanel.setVisible(!filtroPanel.visibleProperty().getValue());
    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
        añadirCategoriaPanel.setVisible(!añadirCategoriaPanel.visibleProperty().getValue());
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
            factura.setFitWidth(20);
            factura.setFitHeight(20);
        } catch (FileNotFoundException ex) {
            errorGasto.setText("No se ha podido importar la factura");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void añadirGasto(ActionEvent event) throws IOException {
        try {
            if (nombreText.getText().isEmpty()) {
                errorGasto.setText("Debes introducir tu nombre");
            } else if (categoriaText.getValue() == null) {
                errorGasto.setText("Debes introducir una categoria");
            } else if (coste.getText().isEmpty()) {
                errorGasto.setText("Debes introducir el coste");
            } else if (unidadesText.getText().isEmpty()) {
                errorGasto.setText("Debes introducir las unidades");
            } else if (fechaGasto.getValue() == null) {
                errorGasto.setText("Debes introducir una fecha");
            } else if (acount.registerCharge(nombreText.getText(),
                    descripcion.getText(),
                    Double.parseDouble(coste.getText()),
                    Integer.parseInt(unidadesText.getText()),
                    factura.getImage(),
                    fechaGasto.getValue(),
                    categoriaText.getValue())) {
                inicializarTabla(Acount.getInstance().getUserCharges());
                nombreText.setText("");
                descripcion.setText("");
                categoriaText.setValue(null);
                coste.setText("");
                unidadesText.setText("");
                fechaGasto.setValue(null);
                if (factura.getImage() != null) {
                    factura.setImage(null);
                    factura.setFitHeight(0);
                    factura.setFitWidth(0);
                }
            }
        } catch (AcountDAOException e) {
            errorGasto.setText("Error al añadir el gasto");
            System.err.println(e);
        }
    }

    @FXML
    private void nextNombre(ActionEvent event) {
        if (nombreText.getText().isBlank()) {
            descripcion.requestFocus();
        }
    }

    @FXML
    private void nextDescripcion(ActionEvent event) {
        categoriaText.requestFocus();
    }

    @FXML
    private void nextCoste(ActionEvent event) {
    }

    @FXML
    private void nextUnidades(ActionEvent event) {
    }

    @FXML
    private void nextFecha(ActionEvent event) {
        añadirGastoButton.requestFocus();
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
    }

    @FXML
    private void eliminargasto(ActionEvent event) throws AcountDAOException, IOException {
        if (tabla.getSelectionModel().isEmpty()) {
            // If nothing is selected in the tabla, show an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un elemento de la tabla.");
            alert.showAndWait();
            return;
        }

        TablePosition pos = tabla.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Charge item = tabla.getItems().get(row);

        acount.removeCharge(item);
        inicializarTabla(Acount.getInstance().getUserCharges());
    }

    @FXML
    private void borrarDatos(ActionEvent event) {
        nombreText.setText("");
        descripcion.setText("");
        categoriaText.setValue(null);
        coste.setText("");
        unidadesText.setText("");
        fechaGasto.setValue(null);
        if (factura.getImage() != null) {
            factura.setImage(null);
            factura.setFitHeight(0);
            factura.setFitWidth(0);
        }
    }

    @FXML
    private void buscar(KeyEvent event) throws AcountDAOException {
        List<Charge> charges = acount.getUserCharges();
        List<Charge> s = new ArrayList();
        for (Charge c : charges) {
            if (c.getName().toLowerCase().startsWith(buscarText.getText().toLowerCase())) {
                s.add(c);
            }
        }
        inicializarTabla(s);
    }

    @FXML
    private void contextmenu(ContextMenuEvent event) {
        TablePosition pos = tabla.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Charge item = tabla.getItems().get(row);
        if (item != null) {
            ContextMenu cm = new ContextMenu();
            MenuItem mi1 = new MenuItem("Editar");
            cm.getItems().add(mi1);
            MenuItem mi2 = new MenuItem("Imprimir");
            cm.getItems().add(mi2);
            cm.show(tabla, event.getScreenX(), event.getScreenY());
            mi1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    openEditDialog(item, event);
                }
            });
            mi2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    imprimir(e);
                }
            });

            // Adding event filter to hide the context menu on mouse click outside of it
            tabla.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event1) -> {
                if (cm.isShowing()) {
                    cm.hide();
                }
            });
        }
    }

    private void openEditDialog(Charge item, Event event) {
        try {
            final Stage dialog = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/EditarGasto.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            dialog.initModality(Modality.APPLICATION_MODAL);
            EditarGastoController childController = fxmlLoader.getController();
            childController.setValues(item);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(item);
            dialog.initOwner(stage);
            Scene dialogScene = new Scene(root);
            dialog.setScene(dialogScene);
            dialog.show();
            dialog.setOnCloseRequest(closeEvent -> handleDialogClose());
            dialog.setOnHidden(hiddenEvent -> handleDialogClose());
        } catch (IOException | AcountDAOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleDialogClose() {
        try {
            inicializarTabla(Acount.getInstance().getUserCharges());
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modificarGasto(ActionEvent event) {
        if (tabla.getSelectionModel().isEmpty()) {
            // If nothing is selected in the tabla, show an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar un elemento de la tabla.");
            alert.showAndWait();
            return;
        }

        TablePosition pos = tabla.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Charge item = tabla.getItems().get(row);
        openEditDialog(item, event);
    }

    @FXML
    private void imprimir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(tabla.getScene().getWindow());

        if (file != null) {
            try {
                PdfWriter writer = new PdfWriter(file);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf, PageSize.A4);

                // Add title to the document
                document.add(new Paragraph("Expense Report").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));

                // Create a table with the same columns as the TableView
                float[] pointColumnWidths = {150F, 150F, 150F, 150F, 150F};
                Table table = new Table(pointColumnWidths);

                // Add table headers
                table.addHeaderCell(new Cell().add(new Paragraph("Nombre")));
                table.addHeaderCell(new Cell().add(new Paragraph("Categoria")));
                table.addHeaderCell(new Cell().add(new Paragraph("Unidades")));
                table.addHeaderCell(new Cell().add(new Paragraph("Precio")));
                table.addHeaderCell(new Cell().add(new Paragraph("Fecha")));

                // Add rows to the table
                for (Charge charge : tabla.getItems()) {
                    table.addCell(new Cell().add(new Paragraph(charge.getName())));
                    table.addCell(new Cell().add(new Paragraph(charge.getCategory().getName())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(charge.getUnits()))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(charge.getCost()))));
                    table.addCell(new Cell().add(new Paragraph(charge.getDate().toString())));
                }

                // Add table to the document
                document.add(table);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
