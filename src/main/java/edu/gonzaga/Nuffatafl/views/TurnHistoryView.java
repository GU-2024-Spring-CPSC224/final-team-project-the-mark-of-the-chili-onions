/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v1.0.0 04/28/2024
 */

package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Turn;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;

/** Displays the turn history for the game :) */
public class TurnHistoryView extends JPanel {
    private final StateController stateController;
    private final JPanel turnsView;

    public TurnHistoryView(StateController stateController) {
        this.stateController = stateController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        ThemeLabel title = new ThemeLabel("Turn History");
        title.setMaximumSize(new Dimension(300, 20));
        add(title);

        // Panel for all the TurnViews
        turnsView = new JPanel();
        turnsView.setLayout(new BoxLayout(turnsView, BoxLayout.Y_AXIS));
        turnsView.setBorder(new EmptyBorder(Theme.PADDING_M, Theme.PADDING_M, Theme.PADDING_M, Theme.PADDING_M));
        setBorder(new EmptyBorder(0, Theme.PADDING_S, 0, 8));
        Theme.setBackgroundFor(this, ThemeComponent.background2);

        // Scroll pane so we can see all the TurnViews even when they fall off the screen
        JScrollPane scrollPane = new JScrollPane(turnsView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(scrollPane);

        // Make sure all of all our colors are up-to-date for players and themes
        scrollPane.setOpaque(false);
        Theme.setBackgroundFor(scrollPane, ThemeComponent.background2);
        updateScrollbar(scrollPane);
        updateTurns();

        Theme.onChange(newTheme -> updateScrollbar(scrollPane));
        stateController.gameManager.onTeamSwitch(event -> updateTurns());
        stateController.gameManager.onAttackerChange(event -> updateTurns());
        stateController.gameManager.onDefenderChange(event -> updateTurns());

        revalidate();
        repaint();
    }

    /** Displays up-to-date list of turns */
    private void updateTurns() {
        turnsView.removeAll();
        Theme.setBackgroundFor(turnsView, ThemeComponent.background2);
        ArrayList<Turn> turns = stateController.gameManager.getTurnHistory();
        int turnCount = turns.size();

        for (int i = 0; i < turnCount; i++) {
            turnsView.add(new TurnSummaryView(turns.get(i), turnCount - i));
            turnsView.add(Box.createVerticalStrut(8));
        }

        setPreferredSize(new Dimension(201, getHeight()));

        revalidate();
        repaint();
    }

    /** Sets the scrollbar color and background to current theme colors */
    private void updateScrollbar(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.scrollBarWidth = 8;
                this.thumbRect.width = 4;
                this.trackColor = Theme.getCurrentTheme().colorForKey(ThemeComponent.background2);
                this.thumbColor = Theme.getCurrentTheme().colorForKey(ThemeComponent.text2);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return nothingButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return nothingButton();
            }

            private JButton nothingButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }
}
