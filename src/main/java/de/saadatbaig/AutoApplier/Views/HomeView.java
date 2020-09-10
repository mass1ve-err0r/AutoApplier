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


    @FXML
    public void removeReplPair(ActionEvent evt) {
        //TODO: Catch no-selection case.
        int k_idx = lvKeys.getSelectionModel().getSelectedIndex();
        int v_idx = lvVals.getSelectionModel().getSelectedIndex();
        int idx = (k_idx == -1) ? v_idx : k_idx;
        _controller.removeFromLists(idx);
        System.out.println("removeReplPair");
    }


    @FXML
    public void addReplPair(ActionEvent evt) {
        System.out.println("addReplPair");
    }

    @FXML
    public void clearPairs(ActionEvent evt) {
        System.out.println("clearPairs");
    }

    @FXML
    public void startProcedure(ActionEvent evt) {
        System.out.println("startProcedure");
    }


    /* End */
}
