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
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer limpiar los datos (no se borrarán sus datos)?",
                ButtonType.YES, ButtonType.NO);
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
        Reservahabitacion habitacion = new Reservahabitacion();
        errorFormato = false;
        if (cliente == null) {
            cliente = new Cliente();
        }

        if (!textFieldDNI.getText().equals("") && textFieldDNI.getText() != null) {
            cliente.setDni(textFieldDNI.getText());

            if (!textFieldNombre.getText().equals("") && textFieldNombre.getText() != null) {
                cliente.setNombre(textFieldNombre.getText());
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un nombre");
                alerta.showAndWait();
                errorFormato = true;
            }

            if (!textFieldDireccion.getText().equals("") && textFieldDireccion != null) {
                cliente.setDireccion(textFieldDireccion.getText());

            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una direccion");
                alerta.showAndWait();
                errorFormato = true;
            }

            if (!textFieldLocalidad.getText().equals("") && textFieldLocalidad != null) {
                cliente.setLocalidad(textFieldLocalidad.getText());
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce la localidad");
                alerta.showAndWait();
                errorFormato = true;
            }

            if (!comboBoxProvincia.getValue().toString().isEmpty() && comboBoxProvincia.getValue() != null) {
                cliente.setProvincia(comboBoxProvincia.getValue());
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce una provincia");
                alerta.showAndWait();
                errorFormato = true;
            }

            if (datePickerLlegada.getValue() != null && !datePickerLlegada.getValue().toString().isEmpty()) {
                LocalDate localDate = datePickerLlegada.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date llegada = Date.from(instant);
                habitacion.setLlegada(llegada);
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce la fecha de llegada");
                alerta.showAndWait();
                errorFormato = true;
            }

            if (datePickerSalida.getValue() != null && !datePickerSalida.getValue().toString().isEmpty()) {
                LocalDate localDate = datePickerSalida.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date salida = Date.from(instant);
                habitacion.setSalida(salida);
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce la fecha de salida");
                alerta.showAndWait();
                errorFormato = true;
            }

            habitacion.setNHabitaciones(spinnerHabitaciones.getValue());

            if (!comboBoxTipoHab.getValue().isEmpty() && comboBoxTipoHab.getValue() != null) {
                habitacion.setTipo(comboBoxTipoHab.getValue());
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un tipo de habitación");
                alerta.showAndWait();
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
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione un Régimen");
                alerta.showAndWait();
                errorFormato = true;
            }

            habitacion.setDni(cliente);

            if (!errorFormato) {
                try {
                    alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Son correctos los datos introducidos?",
                            ButtonType.YES, ButtonType.NO);
                    alerta.setHeaderText("Enviar Reserva");
                    Optional<ButtonType> result = alerta.showAndWait();

                    if (result.get() == ButtonType.YES) {
                        em.merge(cliente);
                        em.persist(habitacion);
                        em.getTransaction().begin();
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
    private void onActionBtnBuscar(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (textFieldDNI.getText().length() == 9 && (!textFieldDNI.getText().equals("") || (textFieldDNI.getText() != null))
                    && (textFieldDNI.getText().charAt(8) > 64 && textFieldDNI.getText().charAt(8) < 91)) {
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
        }
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
