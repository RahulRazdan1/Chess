package rahul.project.system.code.player;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Brd;
import rahul.project.system.code.brd.Mv;
import rahul.project.system.code.pcs.Pc;

import java.util.Collection;

public final class BlkPlyr extends Plyr {

    public BlkPlyr(final Brd brd,
                       final Collection<Mv> whtStdLls,
                       final Collection<Mv> blkStdLgls) {
        super(brd, blkStdLgls, whtStdLls);
    }

    public WhtPlyr getOpp() {
        return this.brd.whtPlyr();
    }

    public Collection<Pc> getActPcs() {
        return this.brd.getBlkPcs();
    }

    public Combine getComb() {
        return Combine.BLK;
    }

    public String toString() {
        return Combine.BLK.toString();
    }

}
