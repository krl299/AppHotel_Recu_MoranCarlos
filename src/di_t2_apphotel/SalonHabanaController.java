package di_t2_apphotel;

import componentes_abrilcarlos.TemporizadorController;
import entidades.Cliente;
import entidades.Reservasalon;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * FXML Controller class
 *
 * @author Alberto León
 */
public class SalonHabanaController implements Initializable {

    private EntityManager em;
    private boolean errorFormato = false;
    private ArrayList<String> lista = new ArrayList<String>();
    private Cliente cliente;
    private boolean alert=true;

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
    @FXML
    private TemporizadorController temporizador;

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

        datePickerFecha.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        temporizador.getSegundos().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Expiración del tiempo, vuelva a hacer la reserva.");
                    alerta.show();
                    btnAceptar.setDisable(true);
                    btnLimpiar.setDisable(true);
                }
            }

        });
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

        labelTipoElegido.setStyle("-fx-text-fill: #3BB33B");
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
        checkBoxHabitaciones.setDisable(false);
        datePickerFecha.setDisable(false);
        labelTipoElegido.setDisable(false);
        labelNumDias.setDisable(false);
        textFieldDIas.setDisable(false);

        //Cambiamos el label del tipo de evento
        labelTipoElegido.setText("Tipo Elegido: Congreso");
        labelTipoElegido.setStyle("-fx-text-fill: #7923A4");

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

            // Expresion regular para comprabar el dni.
            String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
            Pattern pat = Pattern.compile(dniRegexp);
            Matcher mat = pat.matcher(textFieldDNI.getText());

            if (textFieldDNI.getText().length() == 9 && (!textFieldDNI.getText().equals("") || (textFieldDNI.getText() != null) && (mat.matches())) && (textFieldDNI.getText().charAt(8) > 64 && textFieldDNI.getText().charAt(8) < 91)) {

                Query queryCliente = em.createQuery("select c from Cliente c where c.dni='" + textFieldDNI.getText() + "'");
                List<Cliente> listaCliente = queryCliente.getResultList();
                if (!listaCliente.isEmpty()) {

                    deshabilitar();

                    cliente = (Cliente) queryCliente.getResultList().get(0);

                    textFieldNombre.setText(cliente.getNombre());
                    textFieldDireccion.setText(cliente.getDireccion());
                    textFieldTelefono.setText(cliente.getTelefono());

                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);

                    if (cliente.getTelefono() == null) {
                        textFieldTelefono.setDisable(false);
                    } else {
                        textFieldTelefono.setDisable(true);
                    }

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
            } else {
                textFieldDNI.setDisable(false);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "DNI incorrecto!");
                alerta.showAndWait();
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
            textFieldNombre.setDisable(true);
            textFieldDireccion.setText("");
            textFieldDireccion.setDisable(true);
            textFieldTelefono.setText("");
            textFieldTelefono.setDisable(true);
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
        errorFormato = false;
        Alert alerta;
        Reservasalon salon = new Reservasalon();


        // Expresion regular para comprabar el dni.
        String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
        Pattern pat = Pattern.compile(dniRegexp);
        Matcher mat = pat.matcher(textFieldDNI.getText());

        if (textFieldDNI.getText() != null && !textFieldDNI.getText().equals("") && (mat.matches())) {
            if (cliente == null) {
                cliente = new Cliente();
            }          
            if (textFieldDNI.getText() != null && !textFieldDNI.getText().isEmpty()) {
                cliente.setDni(textFieldDNI.getText());
                salon.setDni(cliente);
            }

            // Expresion regular para comprabar el nombre
            String name = "[A-Za-z]*";
            pat = Pattern.compile(name);
            mat = pat.matcher(textFieldTelefono.getText());

            if (textFieldNombre.getText() != null && !textFieldNombre.getText().isEmpty()) {
             cliente.setNombre(textFieldNombre.getText());
            }else if (alert){
                   errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un nombre correcto");
                alerta.showAndWait();
                   alert=false;
            }

            if (textFieldDireccion.getText() != null && !textFieldDireccion.getText().isEmpty()) {
                 cliente.setDireccion(textFieldDireccion.getText());
            } else  if(alert){
               errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca una dirección");
                alerta.showAndWait();
                alert=false;
            }


            if (textFieldTelefono.getText() != null && !textFieldTelefono.getText().isEmpty() && textFieldTelefono.getText().matches("[6|7|9][0-9]{8}$")) {
                cliente.setTelefono(textFieldTelefono.getText());

            } else if (!textFieldTelefono.getText().matches("[6|7|9][0-9]{8}$") && alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número de teléfono valido");
                alerta.showAndWait();
                alert=false;
            } else if(alert){
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número de teléfono");
                alerta.showAndWait();
                alert=false;
            }

            if (grupoBtn1.getSelectedToggle() == null && !alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Seleccione un tipo de evento");
                alerta.showAndWait();
                alert=false;
            } else {
                if (roundBtnBanquete.isSelected()) {
                    salon.setEvento("Banquete");
                    /*Comprobación del radio button Banquete*/
                    comprobarBanquete(salon);

                } else if (roundBtnJornada.isSelected()) {
                    salon.setEvento("Jornada");
                    /*Comprobación del radio button Jornada*/
                    comprobarJornada(salon);

                } else {
                    salon.setEvento("Congreso");
                    /*Comprobación del radio button Congreso*/
                    comprobarCongreso(salon);
                }
            }
            if (!errorFormato) {
                try {

                    alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Son correctos los datos introducidos?", ButtonType.YES, ButtonType.NO);
                    alerta.setHeaderText("Enviar Reserva");

                    Optional<ButtonType> result = alerta.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        em.getTransaction().begin();
                        em.merge(cliente);
                        em.persist(salon);
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
            textFieldDNI.setDisable(false);
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduce un DNI correctamente!");
            alerta.showAndWait();
            errorFormato = true;
            alert=false;
        }
        alert=true;
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

    public void comprobarBanquete(Reservasalon salon) {
        Alert alerta;

        if ((textFiedlPersonas.getText() == null || textFiedlPersonas.getText().isEmpty() && alert)) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
           alert=false;
        } else if (textFiedlPersonas.getText().matches("[a-zA-Z]*") && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
            alert=false;
        } else if (Integer.parseInt(textFiedlPersonas.getText()) >= 50 || Integer.parseInt(textFiedlPersonas.getText()) < 0 && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
            alert=false;
        } else {
            salon.setNPersonas(Integer.parseInt(textFiedlPersonas.getText()));
            alert=true;
        }

        salon.setComida(comboBoxTipoCocina.getValue());

        if (datePickerFecha.getValue() == null || datePickerFecha.getValue().toString().isEmpty()&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca una fecha");
            alerta.showAndWait();
            alert=false;
        } else {
            LocalDate localDate = datePickerFecha.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            salon.setFecha(date);
        }
    }

    public void comprobarJornada(Reservasalon salon) {
        Alert alerta;

        if ((textFiedlPersonas.getText() == null || textFiedlPersonas.getText().isEmpty())&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
            alert=false;

        } else if (textFiedlPersonas.getText().matches("[a-zA-Z]*")&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
            alert=false;

        } else if (Integer.parseInt(textFiedlPersonas.getText()) >= 50 && Integer.parseInt(textFiedlPersonas.getText()) < 0 && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
            alert=false;

        } else {
            salon.setNPersonas(Integer.parseInt(textFiedlPersonas.getText()));
            alert=true;
        }

        if (datePickerFecha.getValue() == null && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca una fecha");
            alerta.showAndWait();
            alert=false;

        } else {
            LocalDate localDate = datePickerFecha.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            salon.setFecha(date);
            alert=true;
        }
    }

    public void comprobarCongreso(Reservasalon salon) {
        Alert alerta;

        if ((textFiedlPersonas.getText() == null || textFiedlPersonas.getText().isEmpty() && alert)) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
             alert=false;

        } else if (textFiedlPersonas.getText().matches("[a-zA-Z]*")&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
             alert=false;

        } else if (Integer.parseInt(textFiedlPersonas.getText()) >= 50 || Integer.parseInt(textFiedlPersonas.getText()) < 0 && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
            alerta.showAndWait();
             alert=false;

        } else {
            salon.setNPersonas(Integer.parseInt(textFiedlPersonas.getText()));
            alert=true;
        }

        if (checkBoxHabitaciones.isSelected()) {
            if (textFieldHab.getText() == null || textFieldHab.getText().equals("")&& alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca el número de habitaciones");
                alerta.showAndWait();
                 alert=false;

            } else if (textFieldHab.getText().matches("[a-zA-Z]*")&& alert) {
                errorFormato = true;
                alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca un número correcto de personas (max:50)");
                alerta.showAndWait();
                 alert=false;

            } else {
                salon.setHabitaciones(Integer.parseInt(textFieldHab.getText()));
                alert=true;
            }
        }

        salon.setComida(comboBoxTipoCocina.getValue());

        if (datePickerFecha.getValue() == null && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca una fecha");
            alerta.showAndWait();
             alert=false;
        } else {
            LocalDate localDate = datePickerFecha.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            salon.setFecha(date);
             alert=true;
        }

        if ((textFieldDIas.getText() == null || textFieldDIas.getText().isEmpty())&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca el número correcto de días");
            alerta.showAndWait();
             alert=false;
        } else if (textFieldDIas.getText().matches("[a-zA-Z]*")&& alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca el correcto de días");
            alerta.showAndWait();
             alert=false;
        } else if (Integer.parseInt(textFieldDIas.getText()) >= 50 || Integer.parseInt(textFieldDIas.getText()) < 0 && alert) {
            errorFormato = true;
            alerta = new Alert(Alert.AlertType.INFORMATION, "Introduzca el correcto de días");
            alerta.showAndWait();
             alert=false;
        } else {
            salon.setNDias(Integer.parseInt(textFieldDIas.getText()));
             alert=true;
        }
    }
    
     public void terminar() {
        temporizador.getTimeline().stop();
    }
}
