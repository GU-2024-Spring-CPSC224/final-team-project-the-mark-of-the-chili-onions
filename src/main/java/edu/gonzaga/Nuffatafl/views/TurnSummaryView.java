package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.backend.Turn;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Shows information for a single turn */
public class TurnSummaryView extends JPanel {
    private Turn turn;
    private int number;
    private boolean isAttacker;
    private JPanel centerPanel;
    private int alignment;

    public TurnSummaryView(Turn turn, int number) {
        this.turn = turn;
        this.number = number;
        this.isAttacker = turn.player.team.equals(Team.ATTACKER);
        this.alignment = isAttacker ? FlowLayout.LEFT : FlowLayout.RIGHT;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        /** Put color bar on left for attacker and right for defender */
        if (isAttacker) {
            setupColorBar();
            add(Box.createHorizontalGlue());
            setupCenterPanel();
            centerPanel.setBorder(new EmptyBorder(0, 8, 0, 0));
        } else {
            setupCenterPanel();
            add(Box.createHorizontalGlue());
            setupColorBar();
            centerPanel.setBorder(new EmptyBorder(0, 0, 0, 8));
        }

        Theme.setBackgroundFor(this, ThemeComponent.background);

        setMaximumSize(new Dimension(400, 100));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        revalidate();
        repaint();
    }

    private void setupColorBar() {
        JPanel colorBar = new JPanel();
        colorBar.setBackground(turn.player.color);
        add(colorBar);
    }

    private void setupCenterPanel() {
        centerPanel = new JPanel();
        Theme.setBackgroundFor(centerPanel, ThemeComponent.background);
        GridLayout centerLayout = new GridLayout(3, 1);
        centerLayout.setHgap(0);
        centerLayout.setVgap(0);
        centerPanel.setLayout(centerLayout);
        add(centerPanel);

        setupTopBar();
        setupMiddleBar();
        setupBottomBar();
    }

    /** Player name, emoji, and turn number */
    private void setupTopBar() {
        JPanel topBar = new JPanel();
        Theme.setBackgroundFor(topBar, ThemeComponent.background);
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        centerPanel.add(topBar);

        JLabel turnNumber = new JLabel("Turn " + number);
        Theme.setBackgroundFor(turnNumber, ThemeComponent.background);
        Theme.setForegroundFor(turnNumber, ThemeComponent.text2);

        PlayerLabel player = new PlayerLabel(turn.player, false);
        player.setOpaque(false);


        if (isAttacker) {
            topBar.add(player);
            topBar.add(Box.createHorizontalGlue());
            topBar.add(turnNumber);
        } else {
            topBar.add(turnNumber);
            topBar.add(Box.createHorizontalGlue());
            topBar.add(player);
        }
    }

    /** From and to position information */
    private void setupMiddleBar() {
        JPanel middleBar = new JPanel(new FlowLayout(alignment));
        Theme.setBackgroundFor(middleBar, ThemeComponent.background);
        centerPanel.add(middleBar);
        middleBar.add(new ThemeLabel("Moved " + turn.from + " to " + turn.to));
    }

    /** Captured piece information */
    private void setupBottomBar() {
        JPanel bottomBar = new JPanel(new FlowLayout(alignment));
        Theme.setBackgroundFor(bottomBar, ThemeComponent.background);
        centerPanel.add(bottomBar);
        String pieces = turn.capturedPieceCount == 1 ? " piece" : " pieces";
        bottomBar.add(new ThemeLabel("Captured " + turn.capturedPieceCount + pieces));
    }
}
