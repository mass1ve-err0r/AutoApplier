package de.saadatbaig.AutoApplier.Controllers;

import org.checkerframework.checker.nullness.qual.NonNull;

import de.saadatbaig.AutoApplier.Models.DOCXManager;
import de.saadatbaig.AutoApplier.Models.DoubleListManager;
import de.saadatbaig.AutoApplier.Models.PreferenceLoader;
import de.saadatbaig.AutoApplier.Views.HomeView;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static javafx.scene.control.Alert.AlertType;

public class HomeViewController {

    ///////////////////////////////////////////////////////////////////////////
    // VARS
    ///////////////////////////////////////////////////////////////////////////
    public DoubleListManager _kvManager = new DoubleListManager();
    private PreferenceLoader _prefs = new PreferenceLoader();
    private DOCXManager _docManager = new DOCXManager();
    private boolean isDocSet = false;
    private HomeView _hv;

    ///////////////////////////////////////////////////////////////////////////
    // MAGICS
    ///////////////////////////////////////////////////////////////////////////
    private final String ERR_TITLE_NODOC = "Document Error";
    private final String ERR_MSG_NODOC = "Document is not set!";
    private final String ERR_TITLE_NOVARS = "Replacement Error";
    private final String ERR_MSG_NOVARS = "You have no Replacements set!";
    private final String ERR_TITLE_TASK = "Fatal Error during Processing";
    private final String ERR_MSG_TASK = "An error during the processing occurred, please retry or post an issue with details";
    private final String DEFAULT_VALUE = "__empty__";
    private final String SUCCESS_TITLE = "Done!";
    private final String SUCCESS_MSG = "Finished the templating task. Generated a PDF as well.";

    /**
     * Standard ctor, takes View as param to reference.
     * @param homeView View to ref.
     */
    public HomeViewController(HomeView homeView) {
        _prefs.loadPrefs(null);
        _hv = homeView;
        _hv.assignController(this);
        _hv.lateInit();
    }


    /**
     * Initialize the document for further processing.
     * @param shouldKeepVars Whether to keep the existing replacements or not.
     * @param fPath Path to the docx.
     */
    public void initDocx(boolean shouldKeepVars, @NonNull String fPath) {
        _docManager.setDocument(fPath);
        isDocSet = true;
        if (!shouldKeepVars) { clearLists(); }
    }

    /**
     * Start the processing.
     * @param fNameExt File Suffix for better identification.
     */
    public void startReplacingAndSave(@NonNull String fNameExt) {
        if (isDocSet) {
            if (!_kvManager.isEmpty()) {
                HashMap<String, String> mappings = _kvManager.zip();
                int rv = _docManager.replaceVariables(fNameExt, mappings);
                if (rv == 0) {
                    _hv.showMessage(AlertType.INFORMATION, SUCCESS_TITLE, SUCCESS_MSG);
                } else {
                    _hv.showMessage(AlertType.ERROR, ERR_TITLE_TASK, ERR_MSG_TASK);
                }
            } else { _hv.showMessage(AlertType.ERROR, ERR_TITLE_NOVARS, ERR_MSG_NOVARS); }
        } else { _hv.showMessage(AlertType.ERROR, ERR_TITLE_NODOC, ERR_MSG_NODOC); }
    }

    /**
     * (Wrapper) Add KV-Pair to their appropriate lists.
     * @param k Key.
     * @param v Associated Value.
     */
    public void addToLists(@NonNull String k, @NonNull String v) {
        _kvManager.addElement(k, v);
    }

    /**
     * (Wrapper) Remove KV-Pair from the doubel lists by the index.
     * @param idx Index of the element to remove.
     */
    public void removeFromLists(int idx) {
        if (idx != -1) { _kvManager.deleteElementAtIndex(idx); }
    }

    /**
     * (Wrapper) Edit value in the according list.
     * @param editingKeys Deduce if the keys list is meant or vals.
     * @param t_idx Target item index.
     * @param _s Replacement String.
     */
    public void editInList(boolean editingKeys, int t_idx, @NonNull String _s) {
        _kvManager.editValueAtIndex(editingKeys, t_idx, _s);
    }

    /**
     * (Wrapper) Self-Explanatory.
     */
    public void clearLists() {
        _kvManager.flush();
    }

    /**
     * (Wrapper) Load a custom preset for easier replacement.
     * @param configName File name without suffix.
     */
    public void getKeysFromConfig(@NonNull String configName) {
        _prefs.loadPrefs(configName + ".aaconf");
        Set<Map.Entry<Object, Object>> set = _prefs.loadConfigVals();
        for (Map.Entry<Object, Object> x: set) {
            String v = ((String) x.getValue()).isEmpty() ? DEFAULT_VALUE : (String)x.getValue();
            _kvManager.addElement((String)x.getKey(), v);
        }
    }


    /* End */
}
