/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class HabitacionesController implements Initializable {

    @FXML
    private TextField textFieldDNI;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldLocalidad;
    @FXML
    private ComboBox<?> comboBoxProvincia;
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
    private Button btnBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionListenerLimpiar(ActionEvent event) {
        
        //Añadir Alerta
        
        textFieldDNI.setText("");
        textFieldNombre.setText("");
        textFieldDireccion.setText("");
        textFieldLocalidad.setText("");
        comboBoxProvincia.getSelectionModel().clearSelection();
    }

    @FXML
    private void onActionListenerAceptar(ActionEvent event) {
    }

    @FXML
    private void onActionListenerCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }
    
}
