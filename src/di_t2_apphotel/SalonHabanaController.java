package di_t2_apphotel;

import entidades.Cliente;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class SalonHabanaController implements Initializable {

    private EntityManager em;
    private EntityManagerFactory emf;

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
    private void onActionBtnBuscar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if ((!textFieldDNI.equals("")) && (textFieldNombre.getText() != null)) {
                iniciarConexion();

                Query queryCliente = em.createQuery("select c from Cliente c where c.dni='" + textFieldDNI.getText() + "'");
                List<Cliente> listaCliente = queryCliente.getResultList();
                if (!listaCliente.isEmpty()) {
                    Cliente cliente = (Cliente) queryCliente.getResultList().get(0);

                    textFieldNombre.setText(cliente.getNombre() + " " + cliente.getApellidos());
                    textFieldDireccion.setText(cliente.getDireccion());
                    textFieldTelefono.setText(cliente.getTelefono());
                }
                pararConexion();
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

    @FXML
    private void onActionLimpiar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer limpiar los datos (no se borrarán sus datos)?",
                ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Limpiar datos");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            textFieldDNI.setText("");
            textFieldNombre.setText("");
            textFieldDireccion.setText("");
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionAceptar(ActionEvent event) {
    }

}
