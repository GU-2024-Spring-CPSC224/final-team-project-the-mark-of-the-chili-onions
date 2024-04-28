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
    /** Maximum size for each bar or row in this view */
    private static final Dimension MAX_BAR_SIZE = new Dimension(300, 20);
    private final StateController stateController;
    private final PieceImages images;
    private final JPanel attackerPieces;
    private final JPanel defenderPieces;
    private int scaledImagePadding = 4;

    private final ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent event) {
            images.resize(getPieceImageSize());
            updatePieces();
        }

        // Unused
        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }
    };

    public CapturedPiecesView(StateController stateController) {
        super();
        this.stateController = stateController;
        images = new PieceImages(getPieceImageSize());
        stateController.gameManager.onTeamSwitch(event -> updatePieces());
        stateController.gameManager.onBoardChange(event -> updatePieces()); //Reset view whenever board is changed
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

    private void calcScaledImagePadding() {
        scaledImagePadding = (getHeight()) / (16 * 16);
    }

    private int getPieceImageSize() {
        int w = Math.max(0, (getWidth() / 2) - (Theme.PADDING_M * 2));
        int h = Math.max(0, (getHeight() - scaledImagePadding * 16 * 2 - 120) / 16); // 16 is max number of pieces team can have
        return Math.min(w, h);
    }

    private void updatePieces() {
        ArrayList<Turn> turns = stateController.gameManager.getTurnHistory();

        int capturedAttackers = 0;
        int capturedDefenders = 0;

        for (Turn turn : turns) {
            Team team = turn.player.team;

            if (team.equals(Team.DEFENDER)) {
                capturedAttackers += turn.capturedPieceCount;
            } else if (team.equals(Team.ATTACKER)) {
                capturedDefenders += turn.capturedPieceCount;
            }
        }

        updateColumn(attackerPieces, Team.DEFENDER, stateController.gameManager.getAttacker().name, capturedDefenders);
        updateColumn(defenderPieces, Team.ATTACKER, stateController.gameManager.getDefender().name, capturedAttackers);

        setPreferredSize(new Dimension(148, getHeight()));

        revalidate();
        repaint();
    }

    private void updateColumn(JPanel panel, Team team, String name, int count) {
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

        ThemeLabel2 aPieceCount = new ThemeLabel2(count + aPieces);
        aPieceCount.setMaximumSize(CapturedPiecesView.MAX_BAR_SIZE);
        panel.add(aPieceCount, gbc);

        calcScaledImagePadding();
        EmptyBorder padding = new EmptyBorder(scaledImagePadding, scaledImagePadding, scaledImagePadding, scaledImagePadding);

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
}
