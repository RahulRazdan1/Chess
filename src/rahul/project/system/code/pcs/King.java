package rahul.project.system.code.pcs;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.BrdUtils;
import rahul.project.system.code.brd.Mv;
import rahul.project.system.code.brd.Mv.MajorAttackMove;
import rahul.project.system.code.brd.Mv.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class King extends Pc {

    private final static int[] CAND_MOVE_COORDS = { -9, -8, -7, -1, 1, 7, 8, 9 };
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    public King(final Combine alli,
                final int pcPos) {
        super(PceType.KING, alli, pcPos, true);
    }

    public King(final Combine alli,
                final int pcPos,
                final boolean isFstMove) {
        super(PceType.KING, alli, pcPos, isFstMove);
    }

    public Collection<Mv> calAllMoves(final Brd brd) {
        final List<Mv> allwdMvs = new ArrayList<>();
        for (final int currCandOffset : CAND_MOVE_COORDS) {
            if (isFrstColExc(this.pcPos, currCandOffset) ||
                isEithColExc(this.pcPos, currCandOffset)) {
                continue;
            }
            final int candDestCoord = this.pcPos + currCandOffset;
            if (BrdUtils.isValidTileCoord(candDestCoord)) {
                final Pc pcAtDest = brd.getPc(candDestCoord);
                if (pcAtDest == null) {
                    allwdMvs.add(new MajorMove(brd, this, candDestCoord));
                } else {
                    final Combine pcAtDestAlle = pcAtDest.getPcAlle();
                    if (this.pcAlli != pcAtDestAlle) {
                        allwdMvs.add(new MajorAttackMove(brd, this, candDestCoord,
                                pcAtDest));
                    }
                }
            }
        }
        return Collections.unmodifiableList(allwdMvs);
    }

    public String toString() {
        return this.pcType.toString();
    }

    public King mvPc(final Mv move) {
        return new King(this.pcAlli, move.getDestCoord(), false);
    }

    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        return false;
    }

    private static boolean isFrstColExc(final int currCand,
                                                  final int candDestCoord) {
        int col = currCand / TLS_IN_A_ROW;
        int row = currCand % TLS_IN_A_ROW;
        return (BrdUtils.INST.FIRST_COL[col][row] &&
            ((candDestCoord == -9) || (candDestCoord == -1) ||
            (candDestCoord == 7)));
    }

    private static boolean isEithColExc(final int currCand, final int candDestCoord) {
        int col = currCand / TLS_IN_A_ROW;
        int row = currCand % TLS_IN_A_ROW;
        return (BrdUtils.INST.EIGHTH_COL[col][row] &&
            ((candDestCoord == -7) || (candDestCoord == 1) ||
            (candDestCoord == 9)));
    }
}