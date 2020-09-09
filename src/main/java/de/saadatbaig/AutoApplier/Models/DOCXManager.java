package de.saadatbaig.AutoApplier.Models;

import org.checkerframework.checker.nullness.qual.NonNull;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import java.io.File;
import java.util.Map;

public class DOCXManager {

    private WordprocessingMLPackage _processor;
    private String _fPath;

    /**
     * Standard ctor.
     */
    public DOCXManager() { /*code*/ }

    /**
     * Prep the document processor.
     * @param fPath Path to the .docx file.
     * @return 0 if successfully set, 1 if not.
     */
    public int setDocument(@NonNull String fPath) {
        try {
            File handle = new File(fPath);
            _processor = WordprocessingMLPackage.load(handle);
            _fPath = fPath.substring(0, fPath.lastIndexOf("/"));
            return 0;
        } catch (Docx4JException | NullPointerException excep) {
            excep.printStackTrace(); // debug
            System.out.println("ok??");
            return 1;
        }
    }


    /* End */
}
