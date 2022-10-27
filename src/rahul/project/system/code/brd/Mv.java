package rahul.project.system.code.brd;

import rahul.project.system.code.brd.Brd.Bldr;
import rahul.project.system.code.pcs.Pawn;
import rahul.project.system.code.pcs.Pc;

public abstract class Mv {

    protected final Brd brd;
    protected final int destCoord;
    protected final Pc mvdPc;
    protected final boolean isFstMove;

    private Mv(final Brd brd,
                 final int destCoord) {
        this.brd = brd;
        this.destCoord = destCoord;
        this.mvdPc = null;
        this.isFstMove = false;
    }

    private Mv(final Brd brd,
                 final Pc pcMvd,
                 final int destCoord) {
        this.brd = brd;
        this.destCoord = destCoord;
        this.mvdPc = pcMvd;
        this.isFstMove = pcMvd.isFstMove();
    }

    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Mv)) {
            return false;
        }
        final Mv otherMove = (Mv) other;
        return getCurrCoord() == otherMove.getCurrCoord() &&
               getDestCoord() == otherMove.getDestCoord() &&
               getMvdPc().equals(otherMove.getMvdPc());
    }

    public Brd getBrd() {
        return this.brd;
    }

    public int getCurrCoord() {
        return this.mvdPc.getPcPos();
    }

    public int getDestCoord() {
        return this.destCoord;
    }

    public Pc getMvdPc() {
        return this.mvdPc;
    }

    public boolean isAttack() {
        return false;
    }

    public Pc getAttkdPc() {
        return null;
    }

    public Brd execute() {
        final Brd.Bldr bld = new Bldr();
        this.brd.currPlyr().getActPcs().stream().filter(pc -> !this.mvdPc.equals(pc)).forEach(bld::setPc);
        this.brd.currPlyr().getOpp().getActPcs().forEach(bld::setPc);
        bld.setPc(this.mvdPc.mvPc(this));
        bld.setMvMkr(this.brd.currPlyr().getOpp().getComb());
        bld.setMvTrnsn(this);
        return bld.build();
    }

    String disambiguationFile() {
        for(final Mv move : this.brd.currPlyr().getAllwdMvs()) {
            if(move.getDestCoord() == this.destCoord && !this.equals(move) &&
               this.mvdPc.getPcType().equals(move.getMvdPc().getPcType())) {
                return BrdUtils.INST.getPosnAtCoord(this.mvdPc.getPcPos()).substring(0, 1);
            }
        }
        return "";
    }

    public static class MajorAttackMove
            extends AttackMove {

        public MajorAttackMove(final Brd brd,
                               final Pc pcMvd,
                               final int destCoord,
                               final Pc pcAttkd) {
            super(brd, pcMvd, destCoord, pcAttkd);
        }

        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

    }

    public enum MoveStatus {

        DONE {
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL_MOVE {
            public boolean isDone() {
                return false;
            }
        },
        LEAVES_PLAYER_IN_CHECK {
            public boolean isDone() {
                return false;
            }
        };

        public abstract boolean isDone();

    }

    public static class MajorMove
            extends Mv {

        public MajorMove(final Brd brd,
                         final Pc pcMvd,
                         final int destCoord) {
            super(brd, pcMvd, destCoord);
        }

        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

    }

    public static class PawnMove
            extends Mv {

        public PawnMove(final Brd brd, final Pc pcMvd, final int destCoord) {
            super(brd, pcMvd, destCoord);
        }

        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

    }

    public static class mvFtry {

        private static final Mv NULL_MOVE = new NullMove();

        private mvFtry() {
            throw new RuntimeException("Not instantiatable!");
        }

        public static Mv getNullMove() {
            return NULL_MOVE;
        }

        public static Mv createMv(final Brd brd,
                                      final int currCoord,
                                      final int destCoord) {
            for (final Mv move : brd.getAllAllwdMvs()) {
                if (move.getCurrCoord() == currCoord &&
                    move.getDestCoord() == destCoord) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

    public static class PawnAttackMove
            extends AttackMove {

        public PawnAttackMove(final Brd brd,
                              final Pc pcMvd,
                              final int destCoord,
                              final Pc pcAttkd) {
            super(brd, pcMvd, destCoord, pcAttkd);
        }

        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

    }

    public static class PawnJump
            extends Mv {

        public PawnJump(final Brd brd,
                        final Pawn pcMvd,
                        final int destCoord) {
            super(brd, pcMvd, destCoord);
        }

        public boolean equals(final Object other) {
            return this == other || other instanceof PawnJump && super.equals(other);
        }

        public Brd execute() {
            final Brd.Bldr bld = new Bldr();
            this.brd.currPlyr().getActPcs().stream().filter(pc -> !this.mvdPc.equals(pc)).forEach(bld::setPc);
            this.brd.currPlyr().getOpp().getActPcs().forEach(bld::setPc);
            final Pawn movedPawn = (Pawn)this.mvdPc.mvPc(this);
            bld.setPc(movedPawn);
            bld.setMvMkr(this.brd.currPlyr().getOpp().getComb());
            bld.setMvTrnsn(this);
            return bld.build();
        }

    }

    private static class NullMove
            extends Mv {

        private NullMove() {
            super(null, -1);
        }

        public int getCurrCoord() {
            return -1;
        }

        public int getDestCoord() {
            return -1;
        }

        public Brd execute() {
            throw new RuntimeException("cannot execute null move!");
        }
    }

    static abstract class AttackMove
            extends Mv {

        private final Pc attkdPc;

        AttackMove(final Brd brd,
                   final Pc pcMvd,
                   final int destCoord,
                   final Pc pcAttkd) {
            super(brd, pcMvd, destCoord);
            this.attkdPc = pcAttkd;
        }

        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttkdPc().equals(otherAttackMove.getAttkdPc());
        }

        public Pc getAttkdPc() {
            return this.attkdPc;
        }

        public boolean isAttack() {
            return true;
        }

    }
}