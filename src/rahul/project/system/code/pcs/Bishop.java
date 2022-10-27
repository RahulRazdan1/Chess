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

public final class Bishop extends Pc {

    private final static int[] CAND_MOVE_COORDS = {-9, -7, 7, 9};
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    public Bishop(final Combine alli,
                  final int pcPos) {
         super(PceType.BISHOP, alli, pcPos, true);
    }

    public Bishop(final Combine alli,
                  final int pcPos,
                   final boolean isFstMove) {
        super(PceType.BISHOP, alli, pcPos, isFstMove);
    }

    public Collection<Mv> calAllMoves(final Brd brd) {
        final List<Mv> allwdMvs = new ArrayList<>();
        for (final int currCandOffset : CAND_MOVE_COORDS) {
            int candDestCoord = this.pcPos;
            while (BrdUtils.isValidTileCoord(candDestCoord)) {
                if (isFrstColExc(currCandOffset, candDestCoord) ||
                    isEithColExc(currCandOffset, candDestCoord)) {
                    break;
                }
                candDestCoord += currCandOffset;
                if (BrdUtils.isValidTileCoord(candDestCoord)) {
                    final Pc pcAtDest = brd.getPc(candDestCoord);
                    if (pcAtDest == null) {
                        allwdMvs.add(new MajorMove(brd, this, candDestCoord));
                    }
                    else {
                        final Combine pcAlli = pcAtDest.getPcAlle();
                        if (this.pcAlli != pcAlli) {
                            allwdMvs.add(new MajorAttackMove(brd, this, candDestCoord,
                                    pcAtDest));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(allwdMvs);
    }

    public Bishop mvPc(final Mv move) {
        return PcUtils.INST.getMovedBishop(move.getMvdPc().getPcAlle(), move.getDestCoord());
    }

    public String toString() {
        return this.pcType.toString();
    }

    private static boolean isFrstColExc(final int currCand, final int candDestCoord) {
        int col = candDestCoord / TLS_IN_A_ROW;
        int row = candDestCoord % TLS_IN_A_ROW;
        return (BrdUtils.INST.FIRST_COL[col][row] &&
                ((currCand == -9) || (currCand == 7)));
    }

    private static boolean isEithColExc(final int currCand, final int candDestCoord) {
        int col = candDestCoord / TLS_IN_A_ROW;
        int row = candDestCoord % TLS_IN_A_ROW;
        return (BrdUtils.INST.EIGHTH_COL[col][row] && ((currCand == -7) || (currCand == 9)));
    }

}