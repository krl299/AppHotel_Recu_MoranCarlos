package di_t2_apphotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import netscape.javascript.JSObject;

/**
 *
 * @author Alberto León
 */
public class FXMLPrincipalController implements Initializable
{

    private EntityManager em;
    private EntityManagerFactory emf;

    private Label label;
    @FXML
    private MenuItem menuHabitaciones;
    @FXML
    private MenuItem menuSalonHabana;
    private Scene scene;
    @FXML
    private MenuItem helpid;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void onActionHabitaciones(ActionEvent event) throws IOException
    {
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
    private void onActionSalonHabana(ActionEvent event) throws IOException
    {
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

        stage.setOnHidden(e -> habana.terminar());
    }

    public void setEm(EntityManager em)
    {
        this.em = em;
    }

    public void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @FXML
    private void onActionHelp(ActionEvent event)
    {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Webview Hotel");
        stage.setScene(new Scene(new Browser(), 750, 500, Color.web("#666970")));
        stage.show();

    }

    @FXML
    private void onActionAyuda(ActionEvent event)
    {
    }

    public class Browser extends Region
    {

        private HBox toolBar;
        private String[] imageFiles = new String[]
        {
            "resources/menu.png",
            "resources/reservaHab.png",
            "resources/reservaSalon.png",
            "resources/BaseDatos.png",
            "resources/Github.png"
        };
        private String[] captions = new String[]
        {
            "Menu Principal",
            "Reservas Habitaciones",
            "Reserservas Salón Habana",
            "Base de Datos",
            "Github"
        };
        private String[] urls = new String[]
        {
            FXMLPrincipalController.class.getResource("paginas/menu.html").toExternalForm(),
            FXMLPrincipalController.class.getResource("paginas/Habitacion.html").toExternalForm(),
            FXMLPrincipalController.class.getResource("paginas/salonhabana.html").toExternalForm(),
            FXMLPrincipalController.class.getResource("paginas/BD.html").toExternalForm(),
            "https://github.com/ieslosmontecillos-di/di-t2-apphotel-bionic"
        };
        final ImageView selectedImage = new ImageView();
        final Hyperlink[] hpls = new Hyperlink[captions.length];
        final Image[] images = new Image[imageFiles.length];
        private boolean needDocumentationButton = false;
        final WebView smallView = new WebView();
        final ComboBox comboBox = new ComboBox();

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        public Browser()
        {
            //apply the styles
            getStyleClass().add("browser");
            //Para tratar lo tres enlaces
            for (int i = 0; i < captions.length; i++)
            {
                Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
                Image image = images[i] = new Image(getClass().getResourceAsStream(imageFiles[i]));
                hpl.setGraphic(new ImageView(image));
                final String url = urls[i];

                //proccess event
                hpl.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent e)
                    {
                        webEngine.load(url);
                    }
                });
            }
            // load the web page

            // create the toolbar
            toolBar = new HBox();
            toolBar.setAlignment(Pos.CENTER);
            toolBar.getStyleClass().add("browser-toolbar");
            comboBox.setPrefWidth(60);
            toolBar.getChildren().add(comboBox);
            toolBar.getChildren().addAll(hpls);
            toolBar.getChildren().add(createSpacer());

            final WebHistory history = webEngine.getHistory();

            history.getEntries().addListener(new ListChangeListener<WebHistory.Entry>()
            {
                @Override
                public void onChanged(ListChangeListener.Change<? extends WebHistory.Entry> c)
                {
                    c.next();
                    for (WebHistory.Entry e : c.getRemoved())
                    {
                        comboBox.getItems().remove(e.getUrl());
                    }
                    for (WebHistory.Entry e : c.getAddedSubList())
                    {
                        comboBox.getItems().add(e.getUrl());
                    }
                }
            });
            //Se define el comportamiento del combobox
            comboBox.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent ev)
                {
                    int offset = comboBox.getSelectionModel().getSelectedIndex() - history.getCurrentIndex();
                    history.go(offset);
                }
            });

            smallView.setPrefSize(120, 80);
            //handle popup windows
            webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>()
            {
                @Override
                public WebEngine call(PopupFeatures config)
                {
                    smallView.setFontScale(0.8);
                    if (!toolBar.getChildren().contains(smallView))
                    {
                        toolBar.getChildren().add(smallView);
                    }
                    return smallView.getEngine();
                }
            }
            );

            // load the web page
            webEngine.load("https://www.palladiumhotelgroup.com/es/hoteles");

            //add components
            getChildren().add(toolBar);
            getChildren().add(browser);
        }

        private Node createSpacer()
        {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            return spacer;
        }

        @Override
        protected void layoutChildren()
        {
            double w = getWidth();
            double h = getHeight();
            double tbHeight = toolBar.prefHeight(w);
            layoutInArea(browser, 0, 0, w, h - tbHeight, 0, HPos.CENTER, VPos.CENTER);
            layoutInArea(toolBar, 0, h
                    - tbHeight, w, tbHeight, 0, HPos.CENTER, VPos.CENTER);
        }

        @Override
        protected double computePrefWidth(double height)
        {
            return 750;
        }

        @Override
        protected double computePrefHeight(double width)
        {
            return 500;
        }
    }

}
