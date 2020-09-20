package de.saadatbaig.AutoApplier;

import de.saadatbaig.AutoApplier.Controllers.HomeViewController;
import de.saadatbaig.AutoApplier.Views.HomeView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private FXMLLoader _fxmlLoader;
    private HomeView _homeView;
    private HomeViewController _homeViewController;

    public void start(Stage stage) throws Exception {
        // Setup Scene
        _fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLs/HomeView.fxml"));
        Pane pane = _fxmlLoader.load();
        _homeView = _fxmlLoader.getController();
        _homeViewController = new HomeViewController(_homeView);

        // scene settings
        Scene scene = new Scene(pane);
        stage.setTitle("AutoApplier");
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void stop() throws Exception {
        Platform.exit();
        System.exit(0);
    }


    /* End */
}
