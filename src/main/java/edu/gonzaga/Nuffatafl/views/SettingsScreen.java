/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.viewNavigation.ScreenChange;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that contains the UI for the Settings screen
 */
public class SettingsScreen extends JPanel {
    public SettingsScreen() {
        super(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel label = new JLabel("Settings Screen");
        topPanel.add(label);


        JButton goToPreviousViewButton = new JButton("Hide Settings Screen");
        goToPreviousViewButton.addActionListener(ScreenChange.toPreviousScreen);
        topPanel.add(goToPreviousViewButton);

        this.add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(500, 500);
        textArea.setText("Movement of pieces\n" +
                "\n" +
                "Each player can move with one piece per move. The king, his guards and mercenaries move in the same way " +
                "- any spaces horizontally or vertically (as chess rook does) with these restrictions:\n" +
                "\n" +
                "    The pieces cannot jump over other pieces.\n" +
                "    No more than one piece can stand on each square.\n" +
                "    After the king leaves his throne, no piece can move to the throne (nor the king). However, a piece " +
                "can go \"over\" the throne and finish its move behind it (e.g. the piece standing one space left from" +
                " the throne can move to the square two spaces right from thr throne if no other piece blocks its way).\n" +
                "\n" +
                "\n" +
                "\n" +
                "How to capture opponent's pieces\n" +
                "\n" +
                "Each player can capture enemy soldiers by surrounding an enemy from both sides vertically or " +
                "horizontally. If a player moves his/her piece between two opponent's ones, the piece is not captured. " +
                "The king cannot capture enemies, this feature is available for guards and mercenaries only. The " +
                "captured soldier will be removed from the board and appears at \"Captured pieces\" section. The " +
                "following picture shows how the white player captures three black mercenaries at once (before and " +
                "after capturing).\n" +
                "\n" +
                "\n" +
                "\n" +
                "How to finish the game\n" +
                "\n" +
                "The game is finished if one of the following conditions in fulfilled:\n" +
                "\n" +
                "  *  The king reaches any border square on the board. The white player wins the game.\n" +
                "\n" +
                "  *  Mercenaries surround the king from all four sides and capture him.\n" +
                "\n" +
                "  *  Mercenaries surround the king from three sides and the throne stands on the fourth side (since " +
                "nobody, including the king, can move to the throne). The black player wins the game.\n" +
                "\n" +
                "  *  A player cannot make a legal move. This player loses the game.");
        middlePanel.add(textArea);

        this.add(middlePanel, BorderLayout.CENTER);
    }
}
