/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_t2_apphotel;

import entidades.Cliente;
import entidades.Provincia;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class HabitacionesController implements Initializable {

    private EntityManager em;
    private EntityManagerFactory emf;

    @FXML
    private TextField textFieldDNI;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldLocalidad;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private DatePicker datePickerLlegada;
    @FXML
    private DatePicker datePickerSalida;
    @FXML
    private Spinner<Integer> spinnerHabitaciones;
    @FXML
    private ComboBox<String> comboBoxTipoHab;
    @FXML
    private RadioButton radBtnAlojamientoDesayuno;
    @FXML
    private RadioButton radBtnMediaPension;
    @FXML
    private RadioButton radBtnPensionCompleta;
    @FXML
    private CheckBox checkBoxFumador;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ToggleGroup radioButtons;
    @FXML
    private VBox vboxRadBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onActionListenerLimpiar(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer limpiar los datos (no se borrarán sus datos)?",
                ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Limpiar datos");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            deshabilitar();
            textFieldDNI.setText("");
            textFieldDNI.setDisable(false);
            textFieldNombre.setText("");
            textFieldDireccion.setText("");
            textFieldLocalidad.setText("");
            datePickerLlegada.setValue(null);
            datePickerSalida.setValue(null);
            spinnerHabitaciones.setValueFactory(null);
            checkBoxFumador.setSelected(false);
            comboBoxTipoHab.setValue(null);
            radioButtons.selectToggle(null);
            comboBoxProvincia.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void onActionListenerAceptar(ActionEvent event) {
        pararConexion();
    }

    @FXML
    private void onActionListenerCancelar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer cerrar la ventana (Sus datos no se guardarán)?",
                ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Cerrar ventana");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            if (em != null) {
                pararConexion();
            }
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onActionBtnBuscar(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            if (textFieldDNI.getText().length() == 9 && (!textFieldDNI.getText().equals("") || (textFieldDNI.getText() != null))) {
                iniciarConexion();
                deshabilitar();
                Query queryCliente = em.createQuery("select c from Cliente c where c.dni='" + textFieldDNI.getText() + "'");
                List<Cliente> listaCliente = queryCliente.getResultList();
                if (!listaCliente.isEmpty()) {

                    textFieldDNI.setDisable(true);
                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);
                    textFieldLocalidad.setDisable(true);
                    comboBoxProvincia.setDisable(true);

                    Cliente cliente = (Cliente) queryCliente.getResultList().get(0);

                    textFieldNombre.setText(cliente.getNombre() + " " + cliente.getApellidos());
                    textFieldDireccion.setText(cliente.getDireccion());
                    textFieldLocalidad.setText(cliente.getLocalidad());

                    cargarProvincias();

                    comboBoxProvincia.setValue(cliente.getProvincia());
                } else {
                    cargarProvincias();
                    habilitar();
                }
            }
        }
    }

    private void iniciarConexion() {
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("javax.persistence.jdbc.user", "APP");
        emfProperties.put("javax.persistence.jdbc.password", "App");
        emfProperties.put("javax.persistence.schema-generation.database.action", "create");
        emf = Persistence.createEntityManagerFactory("DI_T2_AppHotelPU", emfProperties);
        em = emf.createEntityManager();
    }

    private void pararConexion() {
        em.close();
        emf.close();
        try {
            DriverManager.getConnection("jdbc:derby:C:\\DBHotel;shutdown=true");
        } catch (SQLException ex) {
        }
    }

    private void deshabilitar() {
        textFieldNombre.setDisable(true);
        textFieldDireccion.setDisable(true);
        textFieldLocalidad.setDisable(true);
        comboBoxProvincia.setDisable(true);
        datePickerLlegada.setDisable(true);
        datePickerSalida.setDisable(true);
        spinnerHabitaciones.setDisable(true);
        comboBoxTipoHab.setDisable(true);
        checkBoxFumador.setDisable(true);
        vboxRadBtn.setDisable(true);
    }

    private void habilitar() {
        textFieldNombre.setDisable(false);
        textFieldDireccion.setDisable(false);
        textFieldLocalidad.setDisable(false);
        comboBoxProvincia.setDisable(false);
        datePickerLlegada.setDisable(false);
        datePickerSalida.setDisable(false);
        spinnerHabitaciones.setDisable(false);
        comboBoxTipoHab.setDisable(false);
        checkBoxFumador.setDisable(false);
        vboxRadBtn.setDisable(false);
    }

    private void cargarProvincias() {
        Query queryProvinciaFindAll = em.createNamedQuery("Provincia.findAll");
        List listProvincia = queryProvinciaFindAll.getResultList();
        comboBoxProvincia.setItems(FXCollections.observableList(listProvincia));

        //Cambiar lo que se muestra en el combobox de provincia
        comboBoxProvincia.setCellFactory((ListView<Provincia> l) -> new ListCell<Provincia>() {
            @Override
            protected void updateItem(Provincia provincia, boolean empty) {
                super.updateItem(provincia, empty);
                if (provincia == null || empty) {
                    setText("");
                } else {
                    setText(provincia.getCodigo() + "-" + provincia.getNombre());
                }
            }
        });

        comboBoxProvincia.setConverter(new StringConverter<Provincia>() {
            @Override
            public String toString(Provincia provincia) {
                if (provincia == null) {
                    return null;
                } else {
                    return provincia.getCodigo() + "-" + provincia.getNombre();
                }
            }

            @Override
            public Provincia fromString(String userId) {
                return null;
            }
        });
    }
}
