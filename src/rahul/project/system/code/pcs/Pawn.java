package rahul.project.system.code.pcs;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.BrdUtils;
import rahul.project.system.code.brd.Mv;
import rahul.project.system.code.brd.Mv.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Pawn
        extends Pc {

    private final static int[] CAND_MOVE_COORDS = {8, 16, 7, 9};
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;

    public Pawn(final Combine allegiance,
                final int pcPos) {
        super(PceType.PAWN, allegiance, pcPos, true);
    }

    public Pawn(final Combine alli,
                final int pcPos,
                final boolean isFstMove) {
        super(PceType.PAWN, alli, pcPos, isFstMove);
    }

    public Collection<Mv> calAllMoves(final Brd brd) {
        final List<Mv> allwdMvs = new ArrayList<>();
        for (final int currCandOffset : CAND_MOVE_COORDS) {
            int candDestCoord = this.pcPos + (this.pcAlli.getDir() * currCandOffset);
            int col = candDestCoord / TLS_IN_A_ROW;
            int row = candDestCoord % TLS_IN_A_ROW;
            if (!BrdUtils.isValidTileCoord(candDestCoord)) {
                continue;
            }
            if (currCandOffset == 8 && brd.getPc(candDestCoord) == null) {
                allwdMvs.add(new PawnMove(brd, this, candDestCoord));
            }
            else if (currCandOffset == 16 && this.isFstMove() &&
                    ((
                        BrdUtils.INST.SCND_ROW[row][col]
                        && this.pcAlli.isBlk()) ||
                     (
                        BrdUtils.INST.SVNTH_ROW[row][col]
                        && this.pcAlli.isWht()))) {
                final int behindcandDestCoord =
                        this.pcPos + (this.pcAlli.getDir() * 8);
                if (brd.getPc(candDestCoord) == null &&
                    brd.getPc(behindcandDestCoord) == null) {
                    allwdMvs.add(new PawnJump(brd, this, candDestCoord));
                }
            }
            else if (currCandOffset == 7 &&
                    !((
                        BrdUtils.INST.EIGHTH_COL[row][col]
                        && this.pcAlli.isWht()) ||
                      (
                        BrdUtils.INST.FIRST_COL[row][col] 
                        && this.pcAlli.isBlk()))) {
                if(brd.getPc(candDestCoord) != null) {
                    final Pc pcOnCand = brd.getPc(candDestCoord);
                    if (this.pcAlli != pcOnCand.getPcAlle()) {
                        allwdMvs.add(new PawnAttackMove(brd, this, candDestCoord, pcOnCand));
                    }
                } 
            }
            else if (currCandOffset == 9 &&
                    !((BrdUtils.INST.FIRST_COL[row][col] && this.pcAlli.isWht()) ||
                      (BrdUtils.INST.EIGHTH_COL[row][col] && this.pcAlli.isBlk()))) {
                if(brd.getPc(candDestCoord) != null) {
                    if (this.pcAlli !=
                            brd.getPc(candDestCoord).getPcAlle()) {
                            allwdMvs.add(new PawnAttackMove(brd, this, candDestCoord, brd.getPc(candDestCoord)));
                    }
                }
            }
        }
        return Collections.unmodifiableList(allwdMvs);
    }

    public String toString() {
        return this.pcType.toString();
    }

    public Pawn mvPc(final Mv move) {
        return PcUtils.INST.getMovedPawn(move.getMvdPc().getPcAlle(), move.getDestCoord());
    }

}