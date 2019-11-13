package di_t2_apphotel;

import entidades.Cliente;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class SalonHabanaController implements Initializable {

    private EntityManager em;
    private boolean errorFormato = false;
    private ArrayList<String> lista = new ArrayList<String>();

    @FXML
    private TextField textFieldDNI;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private RadioButton roundBtnBanquete;
    @FXML
    private ToggleGroup grupoBtn1;
    @FXML
    private RadioButton roundBtnJornada;
    @FXML
    private RadioButton roundBtnCongreso;
    @FXML
    private TextField textFiedlPersonas;
    @FXML
    private Label labelTipoCocina;
    @FXML
    private ComboBox<String> comboBoxTipoCocina;
    @FXML
    private CheckBox checkBoxHabitaciones;
    @FXML
    private Label labelCuantas;
    @FXML
    private TextField textFieldHab;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private Label labelNumDias;
    @FXML
    private TextField textFieldDIas;
    @FXML
    private Label labelTipoElegido;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    @FXML
    private VBox grupo_rb;
    @FXML
    private Label labelPersona;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lista.add("No precisa");
        lista.add("Buffet vegetariano");
        lista.add("Buffet NO vegetariano");
        lista.add("Carta");

        comboBoxTipoCocina.setValue(lista.get(0));
        comboBoxTipoCocina.setItems(FXCollections.observableList(lista));

    }

    @FXML
    private void onActionBanquete(ActionEvent event) {
        //Habilitamos y desabilitamos lo que necesitamos
        deshabilitar();
        comboBoxTipoCocina.setValue(comboBoxTipoCocina.getItems().get(0));
        labelPersona.setDisable(false);
        textFiedlPersonas.setDisable(false);
        labelTipoCocina.setDisable(false);
        comboBoxTipoCocina.setDisable(false);
        datePickerFecha.setDisable(false);
        labelTipoElegido.setDisable(false);

        //Cambiamos el label del tipo de evento
        labelTipoElegido.setText("Tipo Elegido: Banquete");
        labelTipoElegido.setStyle("-fx-text-fill: #37b3e4");

    }

    @FXML
    private void onActionJornada(ActionEvent event) {
        //Habilitamos y desabilitamos lo que necesitamos
        deshabilitar();
        comboBoxTipoCocina.setValue(comboBoxTipoCocina.getItems().get(0));
        labelPersona.setDisable(false);
        textFiedlPersonas.setDisable(false);
        datePickerFecha.setDisable(false);
        labelTipoElegido.setDisable(false);

        labelTipoElegido.setStyle("-fx-text-fill: #ff5c5c");
        //Cambiamos el label del tipo de evento
        labelTipoElegido.setText("Tipo Elegido: Jornada");

    }

    @FXML
    private void onActionCongreso(ActionEvent event) {
        //Habilitamos y desabilitamos lo que necesitamos
        deshabilitar();
        comboBoxTipoCocina.setValue(comboBoxTipoCocina.getItems().get(0));
        labelPersona.setDisable(false);
        textFiedlPersonas.setDisable(false);
        labelCuantas.setDisable(false);
        checkBoxHabitaciones.setDisable(false);
        textFieldHab.setDisable(false);
        datePickerFecha.setDisable(false);
        labelTipoElegido.setDisable(false);
        labelNumDias.setDisable(false);
        textFieldDIas.setDisable(false);

        //Cambiamos el label del tipo de evento
        labelTipoElegido.setText("Tipo Elegido: Congreso");
        labelTipoElegido.setStyle("-fx-text-fill: #e6ba37");

    }

    @FXML
    private void onActionNecesitasHab(ActionEvent event) {
        if (checkBoxHabitaciones.isSelected()) {
            textFieldHab.setDisable(false);
            labelCuantas.setDisable(false);
        } else {
            textFieldHab.setDisable(true);
            labelCuantas.setDisable(true);
        }
    }

    @FXML
    private void onActionBtnBuscar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (textFieldDNI.getText().length() == 9 && (!textFieldDNI.getText().equals("") || (textFieldDNI.getText() != null))
                    && (textFieldDNI.getText().charAt(8) > 64 && textFieldDNI.getText().charAt(8) < 91)) {

                Query queryCliente = em.createQuery("select c from Cliente c where c.dni='" + textFieldDNI.getText() + "'");
                List<Cliente> listaCliente = queryCliente.getResultList();
                if (!listaCliente.isEmpty()) {

                    deshabilitar();

                    Cliente cliente = (Cliente) queryCliente.getResultList().get(0);

                    textFieldNombre.setText(cliente.getNombre());
                    textFieldDireccion.setText(cliente.getDireccion());
                    textFieldTelefono.setText(cliente.getTelefono());

                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);
                    textFieldTelefono.setDisable(true);

                } else {
                    textFieldNombre.setText("");
                    textFieldDireccion.setText("");
                    textFieldTelefono.setText("");
                    textFieldNombre.setDisable(false);
                    textFieldDireccion.setDisable(false);
                    textFieldTelefono.setDisable(false);
                }

                comboBoxTipoCocina.setValue(comboBoxTipoCocina.getItems().get(0));
                textFieldDNI.setDisable(true);
                grupo_rb.setDisable(false);
            }

        }
    }

    @FXML
    private void onActionLimpiar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer limpiar los datos (no se borrarán sus datos)?",
                ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Limpiar datos");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            deshabilitar();
            textFieldDNI.setDisable(false);
            textFieldDNI.setText("");
            textFieldNombre.setText("");
            textFieldDireccion.setText("");
            textFieldTelefono.setText("");
            grupoBtn1.selectToggle(null);
            textFiedlPersonas.setText("");
            comboBoxTipoCocina.setValue(lista.get(0));
            checkBoxHabitaciones.setSelected(false);
            textFieldHab.setText("");
            datePickerFecha.setValue(null);
            textFieldDIas.setText("");
            labelTipoElegido.setText("Tipo Elegido:");
            labelTipoElegido.setStyle("-fx-text-fill: black");
            grupo_rb.setDisable(true);
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer cerrar la ventana (Sus datos no se guardarán)?",
                ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Cerrar ventana");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onActionAceptar(ActionEvent event) {
    }

    public DatePicker getfecha() {
        return datePickerFecha;
    }

    public void deshabilitar() {

        labelPersona.setDisable(true);
        textFiedlPersonas.setDisable(true);
        comboBoxTipoCocina.setDisable(true);
        checkBoxHabitaciones.setDisable(true);
        textFieldDIas.setDisable(true);
        labelNumDias.setDisable(true);
        labelCuantas.setDisable(true);
        datePickerFecha.setDisable(true);
        labelTipoElegido.setDisable(true);
        textFieldHab.setDisable(true);
        labelTipoCocina.setDisable(true);
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
