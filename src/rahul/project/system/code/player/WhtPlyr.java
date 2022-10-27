package rahul.project.system.code.player;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.Mv;
import rahul.project.system.code.pcs.Pc;

import java.util.Collection;

public final class WhtPlyr extends Plyr {

    public WhtPlyr(final Brd brd,
                       final Collection<Mv> whtStdLls,
                       final Collection<Mv> blkStdLgls) {
        super(brd, whtStdLls, blkStdLgls);
    }

    public BlkPlyr getOpp() {
        return this.brd.blkPlyr();
    }

    public Collection<Pc> getActPcs() {
        return this.brd.getWhtPcs();
    }

    public Combine getComb() {
        return Combine.WHT;
    }

    public String toString() {
        return Combine.WHT.toString();
    }

}
