package rahul.project.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import rahul.project.system.code.brd.*;
import rahul.project.system.code.brd.Mv.mvFtry;
import rahul.project.system.code.pcs.Pc;


import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.invokeLater;

public final class Tbl {

    private Color[] lightClrs = {Color.decode("#e3c16f"), Color.decode("#e3c267"), Color.decode("#e3c56f"), Color.decode("#e3c96f")};
    private Color[] darkClrs = {Color.decode("#b88b4a"), Color.decode("#b8884a"), Color.decode("#b8844a"), Color.decode("#b8804a")};
    
    private int colorRandInt = getRandInt(4);

    private Color lightClr = lightClrs[colorRandInt];
    private Color darkClr = darkClrs[colorRandInt];

    private final JFrame gf;
    private final BrdPnl brdPnl;

    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    private static Dimension OUTER_FRAME_DIMSN = new Dimension(650, 650);
    private static Dimension BRD_PNL_DIMSN = new Dimension(420, 360);
    private static Dimension TILE_PANEL_DIMSN = new Dimension(12, 12);
    
    private Brd cBrd;
    private Pc srcTl;
    private Pc hMvdPc;

    private static Tbl INST = new Tbl();

    private Tbl() {
        this.cBrd = Brd.createStandardBrd();
        this.brdPnl = new BrdPnl();
        this.gf = new JFrame("Chess By Rahul");
        this.gf.setLayout(new BorderLayout());
        this.gf.setSize(OUTER_FRAME_DIMSN);
        this.gf.add(this.brdPnl, BorderLayout.CENTER);
        this.gf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        center(this.gf);
        this.gf.setVisible(true);
    }
    
    private static int getRandInt(double max) {
        return (int)(Math.floor(Math.random() * max));
    }

    public static Tbl get() {
        return INST;
    }

    private Brd getGameBrd() {
        return this.cBrd;
    }

    private class TilPnl extends JPanel {

        private int tId;

        TilPnl( BrdPnl brdPnl,
                   int tId) {
            super(new GridBagLayout());
            this.tId = tId;
            setPreferredSize(TILE_PANEL_DIMSN);
            assignClr();
            assignTilePcIcon(cBrd);
            shadeTlBrdr(cBrd);
            addMouseListener(new MouseListener() {
                public void mouseClicked(final MouseEvent event) {

                    if (isLeftMouseButton(event)) {
                        if (srcTl == null) {
                            srcTl = cBrd.getPc(tId);
                            hMvdPc = srcTl;
                            if (hMvdPc == null) {
                                srcTl = null;
                            }
                        } else {
                            Mv move = mvFtry.createMv(cBrd, srcTl.getPcPos(),
                                    tId);
                            MvTrnsn trnsn = cBrd.currPlyr().makeMove(move);
                            if (trnsn.getMoveStat().isDone()) {
                                cBrd = trnsn.getToBrd();
                            }
                            srcTl = null;
                            hMvdPc = null;
                        }
                    }
                    invokeLater(() -> {
                        brdPnl.drawBrd(cBrd);
                    });
                }

                public void mouseExited(final MouseEvent e) {
                }

                public void mouseEntered(final MouseEvent e) {
                }

                public void mouseReleased(final MouseEvent e) {
                }

                public void mousePressed(final MouseEvent e) {
                }
            });
            validate();
        }

        private void shadeTlBrdr(final Brd brd) {
            if(hMvdPc != null &&
               hMvdPc.getPcAlle() == brd.currPlyr().getComb() &&
               hMvdPc.getPcPos() == this.tId) {
                setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
        }

        private void assignClr() {
            int col = this.tId / TLS_IN_A_ROW;
            int row = this.tId % TLS_IN_A_ROW;
            if (BrdUtils.INST.FRST_ROW[row][col] ||
                BrdUtils.INST.THRD_ROW[row][col] ||
                BrdUtils.INST.FFTH_ROW[row][col] ||
                BrdUtils.INST.SVNTH_ROW[row][col]) {
                setBackground(col % 2 == 0 ? lightClr : darkClr);
            } else if(BrdUtils.INST.SCND_ROW[row][col] ||
                      BrdUtils.INST.FRTH_ROW[row][col] ||
                      BrdUtils.INST.SXTH_ROW[row][col]  ||
                      BrdUtils.INST.EITH_ROW[row][col]) {
                setBackground(col % 2 != 0 ? lightClr : darkClr);
            }
        }

        private void assignTilePcIcon(final Brd brd) {
            this.removeAll();
            if(brd.getPc(this.tId) != null) {
                try{
                    add(new JLabel(brd.getPc(this.tId).getPcAlle().toString().substring(0, 1) + "" +
                    brd.getPc(this.tId).toString()));
                } catch(final Exception e) {
                    e.printStackTrace();
                }
            }
        }

        void drawTile(final Brd brd) {
            assignClr();
            assignTilePcIcon(brd);
            shadeTlBrdr(brd);
            validate();
            repaint();
        }
    }

    private static void center(final JFrame frm) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frm.getSize().width;
        final int h = frm.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frm.setLocation(x, y);
    }

    private BrdPnl getBrdPnl() {
        return this.brdPnl;
    }

    private class BrdPnl extends JPanel {

        final TilPnl[][] brdTls;

        BrdPnl() {
            super(new GridLayout(8,8));
            this.brdTls = new TilPnl[8][8];
            for(int row = 0; row < TLS_IN_A_ROW; row++) {
                for(int col = 0; col < TLS_IN_A_COL; col++) {
                    int i = row * TLS_IN_A_ROW + col;
                    final TilPnl tilPnl = new TilPnl(this, i);
                    this.brdTls[row][col] = tilPnl;
                }
            }
            setPreferredSize(BRD_PNL_DIMSN);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B1726"));
            validate();
        }

        void drawBrd(final Brd brd) {
            removeAll();
            for(int row = 0; row < TLS_IN_A_ROW; row++) {
                for(int col = 0; col < TLS_IN_A_COL; col++) {
                    brdTls[row][col].drawTile(brd);
                    add(brdTls[row][col]);
                }
            }
            validate();
            repaint();
        }

    }

    public void show() {
        Tbl.get().getBrdPnl().drawBrd(Tbl.get().getGameBrd());
    }
}

