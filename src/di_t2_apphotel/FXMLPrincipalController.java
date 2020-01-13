package di_t2_apphotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Alberto León
 */
public class FXMLPrincipalController implements Initializable {

    private EntityManager em;
    private EntityManagerFactory emf;

    private Label label;
    @FXML
    private MenuItem menuHabitaciones;
    @FXML
    private MenuItem menuSalonHabana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onActionHabitaciones(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Habitaciones.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Reserva de Habitaciones");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("recursos/iconrooms.png"));
        stage.show();

        HabitacionesController habitaciones = (HabitacionesController) fxmlLoader.getController();
        habitaciones.setEm(em);
    }

    @FXML
    private void onActionSalonHabana(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SalonHabana.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Reserva de Salón Habana");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("recursos/conference.png"));
        stage.show();
        
        SalonHabanaController habana = (SalonHabanaController) fxmlLoader.getController();
        habana.setEm(em);
        
        stage.setOnHidden(e->habana.terminar());
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
