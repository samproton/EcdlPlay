/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecdlplay.utils;

import ecdlplay.gui.*;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

/**
 *
 * @author Morpheo
 */
public class ImageLoader {

    public static Image loadImageJAR(String filename) {
        
        EcdlPlayWindow instance = EcdlPlayWindow.getInstance();
        // Create ToolKit
        Toolkit toolkit = instance.getToolkit();
        // Create Base Path
        String path = GameCanvasConstants.RES_PATH + filename;
        
        // Force to load now
        MediaTracker mtracker = new MediaTracker(instance);
        Image image = toolkit.createImage(path);
        mtracker.addImage(image, 1);
        try {
            mtracker.waitForID(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mtracker.isErrorID(1)) {
            System.out.println("Could not load from byte data");
            return null;
        }

        return image;
    }

    public static Image loadImage(byte[] imagen) {
        EcdlPlayWindow instance = EcdlPlayWindow.getInstance();
        Toolkit toolkit = instance.getToolkit();

        MediaTracker mtracker = new MediaTracker(instance);
        Image image = toolkit.createImage(imagen);

        mtracker.addImage(image, 1);
        try {
            mtracker.waitForID(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mtracker.isErrorID(1)) {
            System.out.println("Could not load from byte data");
            return null;
        }

        return image;
    }
}
