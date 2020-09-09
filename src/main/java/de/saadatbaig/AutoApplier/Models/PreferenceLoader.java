package de.saadatbaig.AutoApplier.Models;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Properties;

public class PreferenceLoader {

    private Properties _defaults;
    private Properties _config;
    private final String LOCAL_DIR = System.getProperty("user.dir");
    private final String STANDARD_PREFS_NAME = "autoapplier.prefs";
    private final String EMPTY_KEY_DEFAULT = "__empty__";


    /**
     * Standard ctor.
     */
    public PreferenceLoader() {
        _defaults = new Properties();
        _config = new Properties();
    }


    /**
     * Fetch values from the loaded prefs.
     * @param key Key to scan prefs for.
     * @return Value associated with key or error string.
     */
    public String valueForKey(boolean fromMainPrefs, @NonNull String key) {
        return (fromMainPrefs) ? _defaults.getProperty(key, EMPTY_KEY_DEFAULT) : _config.getProperty(key, EMPTY_KEY_DEFAULT);
    }

    /**
     * Set value in the loaded prefs.
     * @param key Key to set value for.
     * @param val Value to set for the given Key.
     */
    public void setValueForKey(boolean fromMainPrefs, @NonNull String key, @NonNull String val) {
        if (fromMainPrefs) {
            _defaults.setProperty(key, val);
        } else {
            _config.setProperty(key, val);
        }
    }


    /* End */
}