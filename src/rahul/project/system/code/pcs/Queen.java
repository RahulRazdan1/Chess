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

public final class Queen extends Pc {

    private final static int[] CAND_MOVE_COORDS = { -9, -8, -7, -1, 1, 7, 8, 9 };
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    public Queen(final Combine alli, final int pcPos) {
        super(PceType.QUEEN, alli, pcPos, true);
    }

    public Queen(final Combine alli,
                 final int pcPos,
                 final boolean isFstMove) {
        super(PceType.QUEEN, alli, pcPos, isFstMove);
    }

    public Collection<Mv> calAllMoves(final Brd brd) {
        final List<Mv> allwdMvs = new ArrayList<>();
        for (final int currCandOffset : CAND_MOVE_COORDS) {
            int candDestCoord = this.pcPos;
            while (true) {
                if (isFrstColExc(currCandOffset, candDestCoord) ||
                    isEightColumnExclusion(currCandOffset, candDestCoord)) {
                    break;
                }
                candDestCoord += currCandOffset;
                if (!BrdUtils.isValidTileCoord(candDestCoord)) {
                    break;
                } else {
                    final Pc pcAtDest = brd.getPc(candDestCoord);
                    if (pcAtDest == null) {
                        allwdMvs.add(new MajorMove(brd, this, candDestCoord));
                    } else {
                        final Combine pcAtDestAlle = pcAtDest.getPcAlle();
                        if (this.pcAlli != pcAtDestAlle) {
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

    public Queen mvPc(final Mv move) {
        return PcUtils.INST.getMovedQueen(move.getMvdPc().getPcAlle(), move.getDestCoord());
    }

    public String toString() {
        return this.pcType.toString();
    }

    private static boolean isFrstColExc(final int currPosn,
                                                  final int candPosn) {
        int col = candPosn / TLS_IN_A_ROW;
        int row = candPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.FIRST_COL[row][col]
            && ((currPosn == -9)
            || (currPosn == -1) || (currPosn == 7)));
    }

    private static boolean isEightColumnExclusion(final int currPosn,
                                                  final int candPosn) {
        int col = candPosn / TLS_IN_A_ROW;
        int row = candPosn % TLS_IN_A_ROW;
        return (BrdUtils.INST.EIGHTH_COL[row][col]
            && ((currPosn == -7)
            || (currPosn == 1) || (currPosn == 9)));
    }

}