package rahul.project.system.code.player;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.Mv;
import rahul.project.system.code.brd.MvTrnsn;
import rahul.project.system.code.brd.Mv.MoveStatus;
import rahul.project.system.code.pcs.King;
import rahul.project.system.code.pcs.Pc;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static rahul.project.system.code.pcs.Pc.PceType.KING;

public abstract class Plyr {

    protected final Brd brd;
    protected final King plyrKng;
    protected final Collection<Mv> allwdMvs;
    protected final boolean isChk;

    Plyr(final Brd brd,
           final Collection<Mv> plyrLgls,
           final Collection<Mv> oppLgls) {
        this.brd = brd;
        this.plyrKng = setKing();
        this.isChk = !calAtkOnTl(this.plyrKng.getPcPos(), oppLgls).isEmpty();
        this.allwdMvs = Collections.unmodifiableCollection(plyrLgls);
    }

    public King getPlyrKng() {
        return this.plyrKng;
    }

    private boolean hasEscapeMoves() {
        return this.allwdMvs.stream()
                              .anyMatch(move -> makeMove(move)
                              .getMoveStat().isDone());
    }

    public boolean isChk() {
        return this.isChk;
    }

    public boolean isChkMate() {
       return this.isChk && !hasEscapeMoves();
    }

    private King setKing() {
        return (King) getActPcs().stream().filter(pc -> pc.getPcType() == KING)
                                       .findAny()
                                       .orElseThrow(RuntimeException::new);
    }

    public Collection<Mv> getAllwdMvs() {
        return this.allwdMvs;
    }

    static Collection<Mv> calAtkOnTl(final int tile,
                                                   final Collection<Mv> moves) {
        return moves.stream()
                    .filter(move -> move.getDestCoord() == tile)
                    .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public MvTrnsn makeMove(final Mv move) {
        if (!this.allwdMvs.contains(move)) {
            return new MvTrnsn(this.brd, this.brd, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Brd trnsnedBrd = move.execute();
        return trnsnedBrd.currPlyr().getOpp().isChk() ?
                new MvTrnsn(this.brd, this.brd, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
                new MvTrnsn(this.brd, trnsnedBrd, move, MoveStatus.DONE);
    }

    public abstract Collection<Pc> getActPcs();
    public abstract Combine getComb();
    public abstract Plyr getOpp();

}
