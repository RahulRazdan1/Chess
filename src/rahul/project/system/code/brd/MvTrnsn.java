package rahul.project.system.code.brd;

import rahul.project.system.code.brd.Mv.MoveStatus;

public final class MvTrnsn {

    private final Brd frmBrd;
    private final Brd toBrd;
    private final Mv trsnMove;
    private final MoveStatus mvStat;

    public MvTrnsn(final Brd frmBrd,
                        final Brd toBrd,
                        final Mv trsnMove,
                        final MoveStatus mvStat) {
        this.frmBrd = frmBrd;
        this.toBrd = toBrd;
        this.trsnMove = trsnMove;
        this.mvStat = mvStat;
    }

    public Brd getFrmBrd() {
        return this.frmBrd;
    }

    public Brd getToBrd() {
         return this.toBrd;
    }

    public Mv getTrsnMove() {
        return this.trsnMove;
    }

    public MoveStatus getMoveStat() {
        return this.mvStat;
    }
}
