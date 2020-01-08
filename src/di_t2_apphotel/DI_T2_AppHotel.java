package di_t2_apphotel;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alberto Leon
 */
public class DI_T2_AppHotel extends Application {

    private EntityManager em;
    private EntityManagerFactory emf;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("FXMLPrincipal.fxml"));
        Parent root = fxmloader.load();
        FXMLPrincipalController principal = (FXMLPrincipalController) fxmloader.getController();

        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setTitle("Paraiso Resort");
        stage.getIcons().add(new Image("recursos/hotel_icon.png"));
        stage.setScene(scene);
        stage.show();
        iniciarConexion();
        
        principal.setEm(em);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void iniciarConexion() throws IOException {
        emf = Persistence.createEntityManagerFactory("DI_T2_AppHotelPU");
        em = emf.createEntityManager();
    }

    @Override
    public void stop() throws Exception {
        pararConexion();
    }

    private void pararConexion() {
        em.close();
        emf.close();
        try {
            DriverManager.getConnection("jdbc:derby:C:\\DBHotel;shutdown=true");
        } catch (SQLException ex) {
        }
    }

}
