package de.saadatbaig.AutoApplier.Views;

import de.saadatbaig.AutoApplier.Controllers.HomeViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;

public class HomeView {

    @FXML public AnchorPane rootView;
    @FXML public Button btnPickBase;
    @FXML public Button btnClear;
    @FXML public Button btnAddVar;
    @FXML public Button btnRemVar;
    @FXML public Button btnExec;
    @FXML public TextField tfFName;
    @FXML public TextField tfFPath;
    @FXML public ListView<String> lvKeys;
    @FXML public ListView<String> lvVals;
    private HomeViewController _controller;

    public void assignController(HomeViewController c) { _controller = c; }

    public void lateInit() {
        lvKeys.setItems(_controller._kvManager._keys);
        lvVals.setItems(_controller._kvManager._vals);
    }




    /* End */
}
