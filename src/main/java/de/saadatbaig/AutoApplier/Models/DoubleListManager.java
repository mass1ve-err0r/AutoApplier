package de.saadatbaig.AutoApplier.Models;

import org.checkerframework.checker.nullness.qual.NonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;

public class DoubleListManager {

    public ObservableList<String> _keys;
    public ObservableList<String> _vals;

    /**
     * Standard ctor.
     *
     * Initialize the two observables here.
     */
    public DoubleListManager() {
        _keys = FXCollections.observableArrayList();
        _vals = FXCollections.observableArrayList();
    }


    /**
     * Delete a KV-Pair by deleting from both lists.
     * @param target_idx Index to delete at.
     * @return 0 if successfull, 1 if not.
     */
    public int deleteElementAtIndex(int target_idx){
        try {
            System.out.printf("[Removing] key: %s | val: %s\n",
                    _keys.get(target_idx),
                    _vals.get(target_idx)
            ); // debug
            _keys.remove(target_idx);
            _vals.remove(target_idx);
            return 0;
        } catch(IndexOutOfBoundsException oobe) {
            oobe.printStackTrace(); //debug
            return 1;
        }
    }

    /**
     * Add a KV-Pair by adding the elements to their according list.
     * @param key Key.
     * @param val Value associated with Key.
     * @return 0 if successfull, 1 if not.
     */
    public int addElement(@NonNull String key, @NonNull String val) {
        if (!_keys.contains(key)) {
            _keys.add(key);
            _vals.add(val);
            return 0;
        }
        return 1;
    }

    /**
     * Zip both lists up a (Hash-)Map.
     * Since both indices are in parallel, this is a legal zip.
     * @return HashMap of both lists.
     */
    public HashMap<String, String> zip() {
        HashMap<String, String> rv = new HashMap<>();
        for (int i = 0; i < _keys.size(); i++) {
            rv.put(_keys.get(i), _vals.get(i));
        }
        return rv;
    }

    /**
     * Flush the current KV-Pairs.
     */
    public void flush() {
        _keys.clear();
        _vals.clear();
    }


    /* End */
}
