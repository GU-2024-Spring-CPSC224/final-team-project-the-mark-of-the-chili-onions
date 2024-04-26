package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Soldier;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.backend.Turn;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewHelpers.ThemeComponent;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/** Displays the captured pieces */
public class CapturedPiecesView extends JPanel {
    private StateController stateController;
    private PieceImages images;
    private JPanel attackerPieces;
    private JPanel defenderPieces;

    /** Maximum size for each bar or row in this view */
    private static final Dimension MAX_BAR_SIZE = new Dimension(300, 20);

    public CapturedPiecesView(StateController stateController) {
        super();
        this.stateController = stateController;
        images = new PieceImages(getPieceImageSize());
        stateController.gameManager.onTeamSwitch(event -> updatePieces());
        addComponentListener(componentListener);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ThemeLabel title = new ThemeLabel("Captured Pieces");
        title.setMaximumSize(CapturedPiecesView.MAX_BAR_SIZE);
        add(title);

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(1, 2));
        attackerPieces = new JPanel();
        attackerPieces.setLayout(new GridBagLayout());
        Theme.setBackgroundFor(attackerPieces, ThemeComponent.background2);
        piecesPanel.add(attackerPieces);
        defenderPieces = new JPanel();
        defenderPieces.setLayout(new GridBagLayout());
        Theme.setBackgroundFor(defenderPieces, ThemeComponent.background2);
        piecesPanel.add(defenderPieces);
        add(piecesPanel);

        updatePieces();

        setBorder(new EmptyBorder(8, 8, 8, 8));
        revalidate();
        repaint();
    }

    private int getPieceImageSize() {
        int w = Math.max(0, (getWidth() / 2) - (Theme.PADDING_M * 2));
        int h = Math.max(0, getHeight() / 16); // 16 is max number of pieces team can have
        return Math.min(w, h);
    }

    private void updatePieces() {
        ArrayList<Turn> turns = stateController.gameManager.getTurnHistory();

        int capturedAttackers = 0;
        int capturedDefenders = 0;

        for (Turn turn : turns) {
            Team team = turn.player.team;

            if (team.equals(Team.ATTACKER)) {
                capturedAttackers += turn.capturedPieceCount;
            } else if (team.equals(Team.DEFENDER)) {
                capturedDefenders += turn.capturedPieceCount;
            }
        }

        updateCollumn(attackerPieces, Team.ATTACKER, stateController.gameManager.getAttacker().name, capturedAttackers);
        updateCollumn(defenderPieces, Team.DEFENDER, stateController.gameManager.getDefender().name, capturedDefenders);

        revalidate();
        repaint();
    }

    private void updateCollumn(JPanel panel, Team team, String name, int count) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        String aPieces = count == 1 ? " piece" : " pieces";

        panel.removeAll();
        ThemeLabel2 aName = new ThemeLabel2(name);
        aName.setMaximumSize(CapturedPiecesView.MAX_BAR_SIZE);
        panel.add(aName, gbc);

        gbc.gridy++;

        ThemeLabel2 aPieceCount = new ThemeLabel2("-" + count + aPieces);
        aPieceCount.setMaximumSize(CapturedPiecesView.MAX_BAR_SIZE);
        panel.add(aPieceCount, gbc);

        EmptyBorder padding = new EmptyBorder(Theme.PADDING_M, Theme.PADDING_M, Theme.PADDING_M, Theme.PADDING_M);

        for (int i = 0; i < count; i++) {
            gbc.gridy++;
            JLabel piece = new JLabel(images.imageFor(new Soldier(team)));
            piece.setOpaque(false);
            piece.setBackground(new Color(0, 0, 0, 0));
            piece.setBorder(padding);
            panel.add(piece, gbc);
        }

        gbc.gridy++;
        gbc.weighty = 1;

        JPanel bitchPanel = new JPanel();
        bitchPanel.setOpaque(false);

        panel.add(bitchPanel, gbc);
    }

    private final ComponentListener componentListener =  new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent event) {
            images.resize(getPieceImageSize());
            updatePieces();
        }

        // Unused
        @Override public void componentMoved(ComponentEvent e) {}
        @Override public void componentShown(ComponentEvent e) {}
        @Override public void componentHidden(ComponentEvent e) {}
    };
}
