package rahul.project.system.code.pcs;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.BrdUtils;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PcUtils {

    INST;

    private final Table<Combine, Integer, Queen> ALL_POSS_QUEENS = PcUtils.createAllPossMvdQueens();
    private final Table<Combine, Integer, Rook> ALL_POSS_ROOKS = PcUtils.createAllPossMvdRooks();
    private final Table<Combine, Integer, Knight> ALL_POSS_KNIGHTS = PcUtils.createAllPossMvdKnights();
    private final Table<Combine, Integer, Bishop> ALL_POSS_BISHOPS = PcUtils.createAllPossMvdBishops();
    private final Table<Combine, Integer, Pawn> ALL_POSS_PAWNS = PcUtils.createAllPossMvdPawns();

    Pawn getMovedPawn(final Combine alli,
                      final int destCoord) {
        return ALL_POSS_PAWNS.get(alli, destCoord);
    }

    Knight getMovedKnight(final Combine alli,
                          final int destCoord) {
        return ALL_POSS_KNIGHTS.get(alli, destCoord);
    }

    Bishop getMovedBishop(final Combine alli,
                          final int destCoord) {
        return ALL_POSS_BISHOPS.get(alli, destCoord);
    }

    Rook getMovedRook(final Combine alli,
                      final int destCoord) {
        return ALL_POSS_ROOKS.get(alli, destCoord);
    }

    Queen getMovedQueen(final Combine alli,
                        final int destCoord) {
        return ALL_POSS_QUEENS.get(alli, destCoord);
    }

    private static Table<Combine, Integer, Queen> createAllPossMvdQueens() {
        final ImmutableTable.Builder<Combine, Integer, Queen> pcs = ImmutableTable.builder();
        for(final Combine alli : Combine.values()) {
            for(int i = 0; i < BrdUtils.NUM_TILES; i++) {
                pcs.put(alli, i, new Queen(alli, i, false));
            }
        }
        return pcs.build();
    }

    private static Table<Combine, Integer, Knight> createAllPossMvdKnights() {
        final ImmutableTable.Builder<Combine, Integer, Knight> pcs = ImmutableTable.builder();
        for(final Combine alli : Combine.values()) {
            for(int i = 0; i < BrdUtils.NUM_TILES; i++) {
                pcs.put(alli, i, new Knight(alli, i, false));
            }
        }
        return pcs.build();
    }

    private static Table<Combine, Integer, Pawn> createAllPossMvdPawns() {
        final ImmutableTable.Builder<Combine, Integer, Pawn> pcs = ImmutableTable.builder();
        for(final Combine alli : Combine.values()) {
            for(int i = 0; i < BrdUtils.NUM_TILES; i++) {
                pcs.put(alli, i, new Pawn(alli, i, false));
            }
        }
        return pcs.build();
    }

    private static Table<Combine, Integer, Bishop> createAllPossMvdBishops() {
        final ImmutableTable.Builder<Combine, Integer, Bishop> pcs = ImmutableTable.builder();
        for(final Combine alli : Combine.values()) {
            for(int i = 0; i < BrdUtils.NUM_TILES; i++) {
                pcs.put(alli, i, new Bishop(alli, i, false));
            }
        }
        return pcs.build();
    }

    private static Table<Combine, Integer, Rook> createAllPossMvdRooks() {
        final ImmutableTable.Builder<Combine, Integer, Rook> pcs = ImmutableTable.builder();
        for(final Combine alli : Combine.values()) {
            for(int i = 0; i < BrdUtils.NUM_TILES; i++) {
                pcs.put(alli, i, new Rook(alli, i, false));
            }
        }
        return pcs.build();
    }

}
