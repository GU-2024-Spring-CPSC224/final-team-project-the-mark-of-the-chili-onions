package edu.gonzaga.Nuffatafl.views;

import edu.gonzaga.Nuffatafl.backend.Soldier;
import edu.gonzaga.Nuffatafl.backend.Team;
import edu.gonzaga.Nuffatafl.backend.Turn;
import edu.gonzaga.Nuffatafl.viewHelpers.Theme;
import edu.gonzaga.Nuffatafl.viewNavigation.StateController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class CapturedPiecesView extends JPanel {
    private StateController stateController;
    private PieceImages images;
    private JPanel attackerPieces;
    private JPanel defenderPieces;

    public CapturedPiecesView(StateController stateController) {
        super();
        this.stateController = stateController;
        images = new PieceImages(getPieceImageSize());
        stateController.gameManager.onTeamSwitch(event -> updatePieces());
        addComponentListener(componentListener);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ThemeLabel title = new ThemeLabel("Captured Pieces");
        title.setMaximumSize(new Dimension(300, 20));
        add(title);

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(1, 2));
        attackerPieces = new JPanel();
        attackerPieces.setLayout(new GridBagLayout());
        piecesPanel.add(attackerPieces);
        defenderPieces = new JPanel();
        defenderPieces.setLayout(new GridBagLayout());
        piecesPanel.add(defenderPieces);
        add(piecesPanel);

        updatePieces();

        setBorder(new EmptyBorder(10, 10, 10, 10));
        revalidate();
        repaint();
    }

    private int getPieceImageSize() {
        int w = Math.max(0, (getWidth() / 2) - 20);
        int h = Math.max(0, getHeight() / 16);
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        String aPieces = capturedAttackers == 1 ? " piece" : " pieces";

        attackerPieces.removeAll();
        ThemeLabel2 aName = new ThemeLabel2(stateController.gameManager.getAttacker().name);
        aName.setMaximumSize(new Dimension(300, 20));
        attackerPieces.add(aName, gbc);

        gbc.gridy++;

        ThemeLabel2 aPieceCount = new ThemeLabel2("-" + capturedAttackers + aPieces);
        aPieceCount.setMaximumSize(new Dimension(300, 20));
        attackerPieces.add(aPieceCount, gbc);

        for (int i = 0; i < capturedAttackers; i++) {
            gbc.gridy++;
            JLabel piece = new JLabel(images.imageFor(new Soldier(Team.ATTACKER)));
            piece.setOpaque(false);
            piece.setBackground(new Color(0, 0, 0, 0));
            piece.setBorder(new EmptyBorder(10, 10, 10, 10));
            attackerPieces.add(piece, gbc);
        }

        gbc.gridy++;
        gbc.weighty = 1;

        attackerPieces.add(new JPanel(), gbc);

        gbc.gridy = 0;
        gbc.weighty = 0;

        String dPieces = capturedDefenders == 1 ? " piece" : " pieces";

        defenderPieces.removeAll();
        ThemeLabel2 dName = new ThemeLabel2(stateController.gameManager.getDefender().name);
        aName.setMaximumSize(new Dimension(300, 20));
        defenderPieces.add(dName, gbc);

        gbc.gridy++;

        ThemeLabel2 dPieceCount = new ThemeLabel2("-" + capturedDefenders + dPieces);
        dPieceCount.setMaximumSize(new Dimension(300, 20));
        defenderPieces.add(dPieceCount, gbc);

        for (int i = 0; i < capturedDefenders; i++) {
            gbc.gridy++;
            JLabel piece = new JLabel(images.imageFor(new Soldier(Team.DEFENDER)));
            piece.setOpaque(false);
            piece.setBackground(new Color(0, 0, 0, 0));
            piece.setBorder(new EmptyBorder(10, 10, 10, 10));
            defenderPieces.add(piece, gbc);
        }

        gbc.gridy++;
        gbc.weighty = 1;

        defenderPieces.add(new JPanel(), gbc);

        revalidate();
        repaint();
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
