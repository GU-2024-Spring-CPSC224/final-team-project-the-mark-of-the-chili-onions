package edu.gonzaga.Nuffatafl.views;

import javax.swing.*;
import java.awt.*;

/** Class for loading and scaling images */
public class ImageLoading {
    /**
     * Function for loading and scaling a function from a given path
     * @param imagePath path to load image from, relative to working directory
     * @param scaleX x scale, in pixels
     * @param scaleY y scale, in pixels
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
}
