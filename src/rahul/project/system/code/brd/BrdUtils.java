package rahul.project.system.code.brd;

import java.util.*;

public enum  BrdUtils {

    INST;

    public final Boolean[][] FIRST_COL = initCol(0);
    public final Boolean[][] SECOND_COL = initCol(1);
    public final Boolean[][] THIRD_COL = initCol(2);
    public final Boolean[][] FOURTH_COL = initCol(3);
    public final Boolean[][] FIFTH_COL = initCol(4);
    public final Boolean[][] SIXTH_COL = initCol(5);
    public final Boolean[][] SEVENTH_COL = initCol(6);
    public final Boolean[][] EIGHTH_COL = initCol(7);
    public final Boolean[][] FRST_ROW = initRow(0);
    public final Boolean[][] SCND_ROW = initRow(1);
    public final Boolean[][] THRD_ROW = initRow(2);
    public final Boolean[][] FRTH_ROW = initRow(3);
    public final Boolean[][] FFTH_ROW = initRow(4);
    public final Boolean[][] SXTH_ROW = initRow(5);
    public final Boolean[][] SVNTH_ROW = initRow(6);
    public final Boolean[][] EITH_ROW = initRow(7);
    public final String[][] ALGEBRAIC_NOTATION = initAlgNotn();
    public final Map<String, Integer> POSN_TO_COORD = initPosnToCoordMap();
    public static final int START_TILE_INDEX = 0;
    public static final int TLS_IN_A_ROW = 8;
    public static final int TLS_IN_A_COL = 8;
    public static final int NUM_TILES = 64;

    private static Boolean[][] initCol(int columnNumber) {
        final Boolean[][] column = new Boolean[TLS_IN_A_ROW][TLS_IN_A_COL];
        for(int row = 0; row < TLS_IN_A_ROW; row++) {
            for(int col = 0; col < TLS_IN_A_COL; col++) {
                if(col == columnNumber) {
                    column[row][col] = true;
                } else {
                    column[row][col] = false;
                }
            }
        }
        return column;
    }

    private static Boolean[][] initRow(int rowNumber) {
        final Boolean[][] rowComp = new Boolean[TLS_IN_A_ROW][TLS_IN_A_COL];
        for(int row = 0; row < TLS_IN_A_ROW; row++) {
            for(int col = 0; col < TLS_IN_A_COL; col++) {
                if(row == rowNumber) {
                    rowComp[row][col] = true;
                } else {
                    rowComp[row][col] = false;
                }
            }
        }
        return rowComp;
    }

    private Map<String, Integer> initPosnToCoordMap() {
        final Map<String, Integer> posnToCoord = new HashMap<>();
        for(int row = 0; row < TLS_IN_A_ROW; row++) {
            for(int col = 0; col < TLS_IN_A_COL; col++) {
                int i = row * TLS_IN_A_ROW + col;
                posnToCoord.put(ALGEBRAIC_NOTATION[row][col], i);
            }
        }
        return Collections.unmodifiableMap(posnToCoord);
    }

    private static String[][] initAlgNotn() {
        String[][] spaces = new String[8][8];
        return spaces;
    }

    public static boolean isValidTileCoord(final int coord) {
        return coord >= START_TILE_INDEX && coord < NUM_TILES;
    }

    public int getCoordAtPosn(final String posn) {
        return POSN_TO_COORD.get(posn);
    }

    public String getPosnAtCoord(final int coord) {
        int col = coord / TLS_IN_A_ROW;
        int row = coord % TLS_IN_A_ROW;
        return ALGEBRAIC_NOTATION[row][col];
    }

    public static boolean isEndGame(final Brd brd) {
        return brd.currPlyr().isChkMate();
    }
}
