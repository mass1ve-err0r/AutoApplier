package de.saadatbaig.AutoApplier.Models;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * Load arbitrary Configurations.
     * @param configName Alternate config name to load.
     * @return 0 if successful, 1 if not.
     */
    public int loadPrefs(@Nullable String configName) {
        String fn;
        try {
            fn = (configName == null) ? STANDARD_PREFS_NAME : configName;
            FileInputStream fs = new FileInputStream(LOCAL_DIR + "/" + fn);
            BufferedInputStream bs = new BufferedInputStream(fs);
            if (configName == null) { _defaults.load(bs); }
            else { _config.load(bs); }
            bs.close();
            fs.close();
            return 0;
        } catch(IOException | SecurityException exception) {
            exception.printStackTrace();
            return 1;
        }
    }

    /**
     * Save currently loaded prefs.
     * @return 0 if success, else 1
     */
    public int savePrefs(@Nullable String configName) {
        String fn;
        try {
            fn = (configName == null) ? STANDARD_PREFS_NAME : configName;
            FileOutputStream fs = new FileOutputStream(LOCAL_DIR + "/" + fn);
            if (configName == null) { _defaults.store(fs, null); }
            else { _config.store(fs, null); }
            fs.close();
            return 0;
        } catch (IOException | SecurityException exception) {
            return 1;
        }
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