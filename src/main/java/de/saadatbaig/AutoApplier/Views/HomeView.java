package de.saadatbaig.AutoApplier.Views;

import de.saadatbaig.AutoApplier.Controllers.HomeViewController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.Optional;

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
        lvKeys.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2 && !lvKeys.getItems().isEmpty()) {
                int sel_idx = lvKeys.getSelectionModel().getSelectedIndex();
                if (sel_idx != -1) { editValuePrompt(true, sel_idx); }
            }
        });
        lvVals.setItems(_controller._kvManager._vals);
        lvVals.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2 && !lvVals.getItems().isEmpty()) {
                int sel_idx = lvVals.getSelectionModel().getSelectedIndex();
                if (sel_idx != -1) { editValuePrompt(false, sel_idx); }
            }
        });
    }


    @FXML
    public void openFilePickerDialog(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Template Word Document");
        fc.getExtensionFilters().add(
                new ExtensionFilter("Word Document (2007+)", "*.docx")
        );
        Stage stage = (Stage) rootView.getScene().getWindow();
        File rv = fc.showOpenDialog(stage);
        if (rv != null) {
            tfFName.setText(rv.getName());
            tfFPath.setText(rv.getAbsolutePath());
            if (lvKeys.getItems().isEmpty()) {
                _controller.initDocx(false, rv.getAbsolutePath());
            } else {
                boolean b = saveVariablesPrompt();
                _controller.initDocx(b, rv.getAbsolutePath());
            }
        }
    }

    @FXML
    public void removeReplPair(ActionEvent evt) {
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

    public boolean saveVariablesPrompt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Replacements are already present");
        alert.setContentText("Would you like to keep them?");
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    public void editValuePrompt(boolean isKeys, int idx) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Field");
        dialog.setContentText("Value:");
        dialog.setHeaderText("Enter a new value for the selected field");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> {
            _controller.editInList(isKeys, idx, str);
        });
    }

    /**
     * (Just in case) Force refresh lists.
     */
    public void refreshLists() {
        lvKeys.refresh();
        lvVals.refresh();
    }


    /* End */
}
