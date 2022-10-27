package rahul.project.system.code.pcs;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.Mv;

import java.util.Collection;

public abstract class Pc {

    final PceType pcType;
    final Combine pcAlli;
    final int pcPos;
    private final boolean isFstMove;

    Pc(final PceType pcType,
          final Combine alli,
          final int pcPos,
          final boolean isFstMove) {
        this.pcType = pcType;
        this.pcPos = pcPos;
        this.pcAlli = alli;
        this.isFstMove = isFstMove;
    }

    public PceType getPcType() {
        return this.pcType;
    }

    public Combine getPcAlle() {
        return this.pcAlli;
    }

    public int getPcPos() {
        return this.pcPos;
    }

    public boolean isFstMove() {
        return this.isFstMove;
    }

    public abstract Pc mvPc(Mv move);

    public abstract Collection<Mv> calAllMoves(final Brd brd);


    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Pc)) {
            return false;
        }
        final Pc othrPc = (Pc) other;
        return this.pcPos == othrPc.pcPos && this.pcType == othrPc.pcType &&
               this.pcAlli == othrPc.pcAlli && this.isFstMove == othrPc.isFstMove;
    }

    public enum PceType {

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");
        
        private final String pcName;

        public String toString() {
            return this.pcName;
        }

        PceType(final String pcName) {
            this.pcName = pcName;
        }

    }

}