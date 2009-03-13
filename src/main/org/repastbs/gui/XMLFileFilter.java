/**
 * File: XMLFileFilter.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.repastbs.Utils;

/**
 * XML file filter used by file choosers
 * @author Ľudovít Hajzer
 *
 */
public class XMLFileFilter extends FileFilter {

    /**
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.xml)) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() {
        return "RepastBS model [.xml]";
    }
}