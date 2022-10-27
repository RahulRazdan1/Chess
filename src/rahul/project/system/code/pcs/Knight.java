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

public final class Knight extends Pc {

    private final static int[] CAND_MOVE_COORDS = { -17, -15, -10, -6, 6, 10, 15, 17 };
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    public Knight(final Combine alli,
                  final int pcPos) {
        super(PceType.KNIGHT, alli, pcPos, true);
    }

    public Knight(final Combine alli,
                  final int pcPos,
                  final boolean isFstMove) {
        super(PceType.KNIGHT, alli, pcPos, isFstMove);
    }

    public Collection<Mv> calAllMoves(final Brd brd) {
        final List<Mv> allwdMvs = new ArrayList<>();
        for (final int currCandOffset : CAND_MOVE_COORDS) {
            if(isFrstColExc(this.pcPos, currCandOffset) ||
               isScndColExc(this.pcPos, currCandOffset) ||
               isSvnthColExc(this.pcPos, currCandOffset) ||
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

    public Knight mvPc(final Mv move) {
        return PcUtils.INST.getMovedKnight(move.getMvdPc().getPcAlle(), move.getDestCoord());
    }

    public String toString() {
        return this.pcType.toString();
    }

    private static boolean isFrstColExc(final int currPosn, final int candOffset) {
        int col = currPosn / TLS_IN_A_ROW;
        int row = currPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.FIRST_COL[col][row]
        && ((candOffset == -17) ||
                (candOffset == -10) || (candOffset == 6) || (candOffset == 15)));
    }

    private static boolean isScndColExc(final int currPosn, final int candOffset) {
        int col = currPosn / TLS_IN_A_ROW;
        int row = currPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.SECOND_COL[col][row]
        && ((candOffset == -10) || (candOffset == 6)));
    }

    private static boolean isSvnthColExc(final int currPosn, final int candOffset) {
        int col = currPosn / TLS_IN_A_ROW;
        int row = currPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.SEVENTH_COL[col][row]
        && ((candOffset == -6) || (candOffset == 10)));
    }

    private static boolean isEithColExc(final int currPosn, final int candOffset) {
        int col = currPosn / TLS_IN_A_ROW;
        int row = currPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.EIGHTH_COL[col][row]
            && ((candOffset == -15) || (candOffset == -6) ||
                (candOffset == 10) || (candOffset == 17)));
    }

}