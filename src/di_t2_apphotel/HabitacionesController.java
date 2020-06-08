/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_t2_apphotel;

import entidades.Cliente;
import entidades.Provincia;
import entidades.Reservahabitacion;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class HabitacionesController implements Initializable {

    private EntityManager em;
    private boolean errorFormato = false;
    private ArrayList<String> lista = new ArrayList<String>();
    private Cliente cliente;

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
        spinnerHabitaciones.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        spinnerHabitaciones.setEditable(false);
        lista.add("Individual");
        lista.add("Doble Individual");
        lista.add("Doble");
        lista.add("Matrimonio");
        comboBoxTipoHab.setItems(FXCollections.observableList(lista));
        comboBoxTipoHab.setValue(lista.get(0));

    }

    @FXML
    private void onActionListenerLimpiar(ActionEvent event) {

        //Alerta que avisa al usuario antes de borrar los datos
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer limpiar los datos (no se borrarán sus datos)?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Limpiar datos");

        Optional<ButtonType> result = alerta.showAndWait();
        //Si el usuario pulsa que si, los campos se limpiarán y se deshabilitaran
        if (result.get() == ButtonType.YES) {
            deshabilitar();
            textFieldDNI.setText("");
            textFieldDNI.setDisable(false);
            textFieldNombre.setText("");
            textFieldDireccion.setText("");
            textFieldLocalidad.setText("");
            datePickerLlegada.setValue(null);
            datePickerSalida.setValue(null);
            checkBoxFumador.setSelected(false);
            comboBoxTipoHab.setValue(null);
            radioButtons.selectToggle(null);
            comboBoxProvincia.getSelectionModel().clearSelection();
            spinnerHabitaciones.getValueFactory().setValue(1);
            comboBoxTipoHab.setValue(lista.get(0));
        }
    }

    @FXML
    private void onActionListenerAceptar(ActionEvent event) {

        Alert alerta;
        String error = "Faltan los siguientes elementos por completar: ";
        Reservahabitacion habitacion = new Reservahabitacion();
        errorFormato = false;
        if (cliente == null) {
            cliente = new Cliente();
        }

        // Expresion regular para comprobar el dni.
        String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
        Pattern pat = Pattern.compile(dniRegexp);
        Matcher mat = pat.matcher(textFieldDNI.getText());

        if (textFieldDNI.getText() != null && !textFieldDNI.getText().equals("") && (mat.matches())) {
            cliente.setDni(textFieldDNI.getText());

            // Expresion regular para comprabar el nombre.
            String name = "[A-Za-z]*";
            pat = Pattern.compile(name);
            mat = pat.matcher(textFieldNombre.getText());
            if (textFieldNombre.getText() != null && !textFieldNombre.getText().equals("") && (mat.matches())) {
                cliente.setNombre(textFieldNombre.getText());
            } else if (textFieldNombre.getText() == null) {
                error += "\n- Nombre.";
                errorFormato = true;
            }

            if (textFieldDireccion != null && !textFieldDireccion.getText().equals("")) {
                cliente.setDireccion(textFieldDireccion.getText());
            } else if (textFieldDireccion.getText() == null) {
                error += "\n- Dirección.";
                errorFormato = true;
            }

            if (textFieldLocalidad != null && !textFieldLocalidad.getText().equals("")) {
                cliente.setLocalidad(textFieldLocalidad.getText());
            } else if (textFieldLocalidad.getText() == null) {
                error += "\n- Localidad.";
                errorFormato = true;
            }

            if (comboBoxProvincia.getValue() != null && !comboBoxProvincia.getValue().toString().isEmpty()) {
                cliente.setProvincia(comboBoxProvincia.getValue());
            } else if (comboBoxProvincia.getValue() == null) {
                error += "\n- Datos Personales.";
                errorFormato = true;
            }

            if (datePickerLlegada.getValue() != null && !datePickerLlegada.getValue().toString().isEmpty()) {
                LocalDate localDate = datePickerLlegada.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date llegada = Date.from(instant);
                habitacion.setLlegada(llegada);
            } else if (datePickerLlegada.getValue() == null) {
                error += "\n- Fecha de llegada.";
                errorFormato = true;
            }

            if (datePickerSalida.getValue() != null && !datePickerSalida.getValue().toString().isEmpty()) {
                LocalDate localDate = datePickerSalida.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date salida = Date.from(instant);
                habitacion.setSalida(salida);
            } else if (datePickerSalida.getValue() == null) {
                error += "\n- Fecha de salida.";
                errorFormato = true;
            }

            habitacion.setNHabitaciones(spinnerHabitaciones.getValue());

            if (comboBoxTipoHab.getValue() != null && !comboBoxTipoHab.getValue().isEmpty()) {
                habitacion.setTipo(comboBoxTipoHab.getValue());
            } else if (comboBoxTipoHab.getValue() == null) {
                error += "\n- Habitaciones.";
                errorFormato = true;
            }

            if (checkBoxFumador.isSelected()) {
                habitacion.setFumador("Fumador");
            } else {
                habitacion.setFumador("No Fumador");
            }

            if (radioButtons.getSelectedToggle() != null) {
                if (radBtnAlojamientoDesayuno.isSelected()) {
                    habitacion.setRegimen("Alojamiento y desayuno");
                } else if (radBtnMediaPension.isSelected()) {
                    habitacion.setRegimen("Media Pensión");
                } else if (radBtnPensionCompleta.isSelected()) {
                    habitacion.setRegimen("Pensión Completa");
                }
            } else {
                error += "\n- Pension.";
                errorFormato = true;
            }
            if (errorFormato) {
                alerta = new Alert(Alert.AlertType.INFORMATION, error);
                alerta.showAndWait();
            }

            habitacion.setDni(cliente);

            if (!errorFormato) {
                try {
                    String confirmacion = "¿Son correctos los datos introducidos?";
                    confirmacion += "\n DNI :" + textFieldDNI.getText();
                    confirmacion += "\n Nombre :" + textFieldNombre.getText();
                    confirmacion += "\n Dirección :" + textFieldDireccion.getText();
                    confirmacion += "\n Localidad :" + textFieldLocalidad.getText();
                    confirmacion += "\n Provincia :" + comboBoxProvincia.getSelectionModel().getSelectedItem().getNombre();
                    confirmacion += "\n Fecha de entrada :" + datePickerLlegada.getValue();
                    confirmacion += "\n Fecha de salida :" + datePickerSalida.getValue();
                    confirmacion += "\n Número de habitaciones :" + spinnerHabitaciones.getValue();
                    confirmacion += "\n Tipo de habitacion :" + comboBoxTipoHab.getSelectionModel().getSelectedItem();
                    
                    if (radBtnAlojamientoDesayuno.isSelected()) {
                        confirmacion += "\n Regimen :" + radBtnAlojamientoDesayuno.getText();
                    } else if (radBtnMediaPension.isSelected()) {
                        confirmacion += "\n Regimen :" + radBtnMediaPension.getText();
                    }else if(radBtnPensionCompleta.isSelected()){
                        confirmacion += "\n Regimen :" + radBtnPensionCompleta.getText();
                    }
                    if(checkBoxFumador.isSelected()){
                        confirmacion+="\n Fumador :SI";
                    }else{
                        confirmacion+="\n Fumador :NO";
                    }

                    alerta = new Alert(Alert.AlertType.CONFIRMATION, confirmacion, ButtonType.YES, ButtonType.NO);
                    alerta.setHeaderText("Enviar Reserva");
                    Optional<ButtonType> result = alerta.showAndWait();

                    if (result.get() == ButtonType.YES) {
                        em.getTransaction().begin();
                        em.merge(cliente);
                        em.persist(habitacion);
                        em.getTransaction().commit();

                        Stage stage = (Stage) btnAceptar.getScene().getWindow();
                        stage.close();
                    }

                } catch (RollbackException e) {
                    em.getTransaction().rollback();
                    alerta = new Alert(Alert.AlertType.INFORMATION, "Error al guardar los datos. Inténtelo de nuevo");
                    alerta.setContentText(e.getLocalizedMessage());
                    alerta.showAndWait();
                }
            }

        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un DNI");
            alerta.showAndWait();
            errorFormato = true;
        }

    }

    @FXML
    private void onActionListenerCancelar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer cerrar la ventana (Sus datos no se guardarán)?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Cerrar ventana");

        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.YES) {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onActionBtnBuscar(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            // Expresion regular para comprabar el dni.
            String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
            Pattern pat = Pattern.compile(dniRegexp);
            Matcher mat = pat.matcher(textFieldDNI.getText());

            if (textFieldDNI.getText().length() == 9 && (!textFieldDNI.getText().equals("") || (textFieldDNI.getText() != null) && (mat.matches())) && (textFieldDNI.getText().charAt(8) > 64 && textFieldDNI.getText().charAt(8) < 91)) {
                deshabilitar();
                Query queryCliente = em.createQuery("select c from Cliente c where c.dni='" + textFieldDNI.getText() + "'");
                List<Cliente> listaCliente = queryCliente.getResultList();
                if (!listaCliente.isEmpty()) {

                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);
                    textFieldLocalidad.setDisable(true);
                    comboBoxProvincia.setDisable(true);

                    cliente = (Cliente) queryCliente.getResultList().get(0);

                    textFieldNombre.setText(cliente.getNombre());
                    textFieldDireccion.setText(cliente.getDireccion());
                    textFieldLocalidad.setText(cliente.getLocalidad());

                    cargarProvincias();
                    if (cliente.getProvincia() != null) {
                        comboBoxProvincia.setValue(cliente.getProvincia());
                    } else {
                        comboBoxProvincia.setDisable(false);
                    }

                    if (cliente.getLocalidad() != null) {
                        textFieldLocalidad.setText(cliente.getLocalidad());
                    } else {
                        textFieldLocalidad.setDisable(false);
                    }

                    habilitar();

                } else {
                    cargarProvincias();
                    textFieldNombre.setDisable(false);
                    textFieldDireccion.setDisable(false);
                    textFieldLocalidad.setDisable(false);
                    comboBoxProvincia.setDisable(false);
                    habilitar();
                }
                textFieldDNI.setDisable(true);

                deshabilitarFecha();
            } else {
                textFieldDNI.setDisable(false);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "DNI incorrecto!");
                alerta.showAndWait();
            }
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
        datePickerLlegada.setDisable(false);
        spinnerHabitaciones.setDisable(false);
        comboBoxTipoHab.setDisable(false);
        checkBoxFumador.setDisable(false);
        vboxRadBtn.setDisable(false);
    }

    private void cargarProvincias() {
        Query queryProvinciaFindAll = em.createNamedQuery("Provincia.findAll");
        List listProvincia = queryProvinciaFindAll.getResultList();
        comboBoxProvincia.setItems(FXCollections.observableList(listProvincia));

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

    private void deshabilitarFecha() {
        datePickerLlegada.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        datePickerSalida.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (datePickerLlegada.getValue() != null) {
                    setDisable(empty || date.compareTo(datePickerLlegada.getValue()) < 1);
                }
            }
        });
    }

    @FXML
    private void onActionFechaLlegada(ActionEvent event) {
        if (datePickerLlegada.getValue() != null) {
            datePickerSalida.setDisable(false);
            datePickerSalida.setValue(null);
        }
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
