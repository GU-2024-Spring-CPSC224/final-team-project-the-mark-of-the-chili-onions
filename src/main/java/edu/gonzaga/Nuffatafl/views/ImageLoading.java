/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Orion Hess, Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import javax.swing.*;
import java.awt.*;

/** Class for loading and scaling images */
public class ImageLoading {
    /** Keep track of the resource path */
    public static String resourcesPath = "./src/main/resources/";

    /**
     * Function for loading and scaling a function from a given path
     *
     * @param imagePath path to load image from, relative to working directory
     * @param scaleX    x scale, in pixels
     * @param scaleY    y scale, in pixels
     * @return ImageIcon: the loaded image, scaled as given
     */
    public static ImageIcon getImage(String imagePath, int scaleX, int scaleY) {
        Image image;
        try {
            image = new ImageIcon(imagePath).getImage();
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
        image = image.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static ImageIcon settingsIcon(int size) {
        return getImage(resourcesPath + "settings.png", size, size);
    }

    public static ImageIcon rulesIcon(int size) {
        return getImage(resourcesPath + "rules.png", size, size);
    }

    public static ImageIcon darkPiece(int size) {
        return getImage(resourcesPath + "taflPieceDark.png", size, size);
    }

    public static ImageIcon lightPiece(int size) {
        return getImage(resourcesPath + "taflPieceLight.png", size, size);
    }

    public static ImageIcon kingPiece(int size) {
        return getImage(resourcesPath + "taflPieceKing.png", size, size);
    }

    public static ImageIcon xTile(int size) {
        return getImage(resourcesPath + "xTile.png", size, size);
    }

    public static ImageIcon clear(int size) {
        return getImage(resourcesPath + "clear.png", size, size);
    }
}
