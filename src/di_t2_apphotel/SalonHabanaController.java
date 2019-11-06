package di_t2_apphotel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class SalonHabanaController implements Initializable {

    private EntityManager entityManager;

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
    private Button btnBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onActionBanquete(ActionEvent event) {
    }

    @FXML
    private void onActionJornada(ActionEvent event) {
    }

    @FXML
    private void onActionCongreso(ActionEvent event) {
    }

    @FXML
    private void onActionNecesitasHab(ActionEvent event) {
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

}
