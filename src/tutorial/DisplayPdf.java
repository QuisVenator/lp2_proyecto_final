/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author guiga
 */
public class DisplayPdf {
    
    public static void display (String filePath) {  //  se coloca la direccion del archivo (ya sin incluir src)
        
        if (Desktop.isDesktopSupported()) {
            try {
                    File myFile = new File("./src/" + filePath);
                    Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }
}
