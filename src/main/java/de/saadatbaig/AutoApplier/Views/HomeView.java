package de.saadatbaig.AutoApplier.Views;

import org.checkerframework.checker.nullness.qual.NonNull;
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

    ///////////////////////////////////////////////////////////////////////////
    // FXMLs + Vars
    ///////////////////////////////////////////////////////////////////////////
    @FXML public AnchorPane rootView;
    @FXML public Button btnPickBase;
    @FXML public Button btnClear;
    @FXML public Button btnLoad;
    @FXML public Button btnAddVar;
    @FXML public Button btnRemVar;
    @FXML public Button btnExec;
    @FXML public TextField tfFName;
    @FXML public TextField tfFPath;
    @FXML public ListView<String> lvKeys;
    @FXML public ListView<String> lvVals;
    private HomeViewController _controller;

    ///////////////////////////////////////////////////////////////////////////
    // MAGICS
    ///////////////////////////////////////////////////////////////////////////
    private final String VARS_TITLE = "Replacements are already present";
    private final String VARS_MSG = "Would you like to keep them?";
    private final String VARS_MSG2 = "Are you sure to clear the Pairs?";
    private final String F_TITLE = "Enter File suffix";
    private final String EDITOR_TITLE = "Edit Field";
    private final String CNF_TITLE = "Load Configuration";
    private final String F_CONTENT_TEXT = "Suffix:";
    private final String EDITOR_CONTENT_TEXT = "Value:";
    private final String CNF_CONTENT_TEXT = "File Name:";
    private final String F_HDR_TEXT = "Please add a file suffix to easier identify the output file.";
    private final String EDITOR_HDR_TEXT = "Enter a new value for the selected field.";
    private final String CNF_HDR_TEXT = "Enter the name of the configuration file to load.";
    private final int MOUSE_DOUBLECLICK = 2;

    /**
     * Reference to our controller.
     * @param c HomeViewController to reference.
     */
    public void assignController(HomeViewController c) { _controller = c; }

    /**
     * Late initializer for when JFX elements are available.
     * Intentionally created this instead of overriding initialize().
     */
    public void lateInit() {
        lvKeys.setItems(_controller._kvManager._keys);
        lvKeys.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == MOUSE_DOUBLECLICK && !lvKeys.getItems().isEmpty()) {
                int sel_idx = lvKeys.getSelectionModel().getSelectedIndex();
                if (sel_idx != -1) { editValuePrompt(true, sel_idx); }
            }
        });
        lvVals.setItems(_controller._kvManager._vals);
        lvVals.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == MOUSE_DOUBLECLICK && !lvVals.getItems().isEmpty()) {
                int sel_idx = lvVals.getSelectionModel().getSelectedIndex();
                if (sel_idx != -1) { editValuePrompt(false, sel_idx); }
            }
        });
    }


    /**
     * File selection Dialog. Self-Explanatory.
     * @param evt Event.
     */
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
                boolean b = areYouSurePrompt(VARS_TITLE, VARS_MSG);
                _controller.initDocx(b, rv.getAbsolutePath());
            }
        }
    }

    /**
     * Removes a KV-Pair from the list, regardless which side you click on.
     * Will always delete the associated key/ value as well.
     * @param evt Event.
     */
    @FXML
    public void removeReplPair(ActionEvent evt) {
        int k_idx = lvKeys.getSelectionModel().getSelectedIndex();
        int v_idx = lvVals.getSelectionModel().getSelectedIndex();
        int idx = (k_idx == -1) ? v_idx : k_idx;
        _controller.removeFromLists(idx);
    }


    /**
     * Following code is courtesy of Stack-Overflow and code.makery!
     * (Adapted for my own needs)
     * ref 1: https://code.makery.ch/blog/javafx-dialogs-official/
     * ref 2: https://stackoverflow.com/a/31556716
     * @param evt event.
     */
    @FXML
    public void addReplPair(ActionEvent evt) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Replacement Pair");
        ButtonType btnConfirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnConfirm, ButtonType.CANCEL);

        TextField tfKey = new TextField();
        tfKey.setPromptText("myVar");
        TextField tfVal = new TextField();
        tfVal.setPromptText("myValueForIt");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        gridPane.add(new Label("Variable:"), 0, 0);
        gridPane.add(tfKey, 1, 0);
        gridPane.add(new Label("Replacement:"), 0, 1);
        gridPane.add(tfVal, 1, 1);
        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> tfKey.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnConfirm) {
                return new Pair<>(tfKey.getText(), tfVal.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            _controller.addToLists(pair.getKey(), pair.getValue());
        });
    }

    /**
     * Clear the pairings.
     * @param evt Event.
     */
    @FXML
    public void clearPairs(ActionEvent evt) {
        boolean b = areYouSurePrompt(VARS_TITLE, VARS_MSG2);
        if (b) { _controller.clearLists(); }
    }

    /**
     * Start the replacing & saving.
     * @param evt Event.
     */
    @FXML
    public void startProcedure(ActionEvent evt) {
        TextInputDialog dialog = createTextInputDialog(F_TITLE, F_CONTENT_TEXT, F_HDR_TEXT);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> {
            _controller.startReplacingAndSave(str);
        });
    }

    /**
     * Load Keys/ Vars from prefs.
     * @param evt Event.
     */
    @FXML
    public void loadKeysFromPrefs(ActionEvent evt) {
        TextInputDialog dialog = createTextInputDialog(CNF_TITLE, CNF_CONTENT_TEXT, CNF_HDR_TEXT);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> {
            _controller.getKeysFromConfig(str);
        });
    }

    /**
     * Dialog to make sure the user agrees to the action.
     * @return true or false.
     */
    public boolean areYouSurePrompt(@NonNull String title, @NonNull String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Editing Dialog which optionally edits the field in question.
     * @param isKeys If it's the Key list.
     * @param idx Field index in the list.
     */
    public void editValuePrompt(boolean isKeys, int idx) {
        TextInputDialog dialog = createTextInputDialog(EDITOR_TITLE, EDITOR_CONTENT_TEXT, EDITOR_HDR_TEXT);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> {
            _controller.editInList(isKeys, idx, str);
        });
    }

    /**
     * Show a Dialog.
     * @param type Type of the Dialog
     * @param title Title of the Dialog.
     * @param msg Message to display.
     */
    public void showMessage(Alert.AlertType type, @NonNull String title, @NonNull String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * (Just in case) Force refresh lists.
     */
    public void refreshLists() {
        lvKeys.refresh();
        lvVals.refresh();
    }

    /**
     * Generic Dialog creator. Self-Explanatory.
     * @param title Title.
     * @param cText Content Text.
     * @param hText Header Text.
     * @return A pre-setup TextInputDialog.
     */
    private TextInputDialog createTextInputDialog(@NonNull String title, @NonNull String cText, @NonNull String hText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(cText);
        dialog.setHeaderText(hText);
        return dialog;
    }


    /* End */
}
