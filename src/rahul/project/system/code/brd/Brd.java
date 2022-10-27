package rahul.project.system.code.brd;

import rahul.project.system.code.Combine;
import rahul.project.system.code.brd.Mv.mvFtry;
import rahul.project.system.code.pcs.*;
import rahul.project.system.code.player.BlkPlyr;
import rahul.project.system.code.player.Plyr;
import rahul.project.system.code.player.WhtPlyr;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Brd {

    private final Map<Integer, Pc> brdConf;
    private final Collection<Pc> whtPcs;
    private final Collection<Pc> blkPcs;
    private final WhtPlyr whtPlyr;
    private final BlkPlyr blkPlyr;
    private final Plyr currPlyr;
    private final Mv trsnMove;

    private static final Brd STD_BRD = genStdBrdImpl();

    private Brd(final Bldr bld) {
        this.brdConf = Collections.unmodifiableMap(bld.brdConf);
        this.whtPcs = calActivePcs(bld, Combine.WHT);
        this.blkPcs = calActivePcs(bld, Combine.BLK);
        final Collection<Mv> whiteStandardMoves = calAllMoves(this.whtPcs);
        final Collection<Mv> blackStandardMoves = calAllMoves(this.blkPcs);
        this.whtPlyr = new WhtPlyr(this, whiteStandardMoves, blackStandardMoves);
        this.blkPlyr = new BlkPlyr(this, whiteStandardMoves, blackStandardMoves);
        this.currPlyr = bld.nextMoveMaker.choosePlyrByAlli(this.whtPlyr, this.blkPlyr);
        this.trsnMove = bld.trsnMove != null ? bld.trsnMove : mvFtry.getNullMove();
    }

    public Collection<Pc> getBlkPcs() {
        return this.blkPcs;
    }

    public Collection<Pc> getWhtPcs() {
        return this.whtPcs;
    }

    public Collection<Pc> getAllPcs() {
        return Stream.concat(this.whtPcs.stream(),
                             this.blkPcs.stream()).collect(Collectors.toList());
    }

    public Collection<Mv> getAllAllwdMvs() {
        return Stream.concat(this.whtPlyr.getAllwdMvs().stream(),
                             this.blkPlyr.getAllwdMvs().stream()).collect(Collectors.toList());
    }

    public WhtPlyr whtPlyr() {
        return this.whtPlyr;
    }

    public BlkPlyr blkPlyr() {
        return this.blkPlyr;
    }

    public Plyr currPlyr() {
        return this.currPlyr;
    }

    public Pc getPc(final int coord) {
        return this.brdConf.get(coord);
    }

    public Mv getTrsnMove() {
        return this.trsnMove;
    }

    private static Collection<Pc> calActivePcs(final Bldr bld,
                                                           final Combine alliance) {
        return bld.brdConf.values().stream()
               .filter(pc -> pc.getPcAlle() == alliance)
               .collect(Collectors.toList());
    }

    public static Brd createStandardBrd() {
        return STD_BRD;
    }

    private static Brd genStdBrdImpl() {
        final Bldr bld = new Bldr();
        
        bld.setPc(new Rook(Combine.BLK, 0));
        bld.setPc(new Knight(Combine.BLK, 1));
        bld.setPc(new Bishop(Combine.BLK, 2));
        bld.setPc(new Queen(Combine.BLK, 3));
        bld.setPc(new King(Combine.BLK, 4));
        bld.setPc(new Bishop(Combine.BLK, 5));
        bld.setPc(new Knight(Combine.BLK, 6));
        bld.setPc(new Rook(Combine.BLK, 7));
        bld.setPc(new Pawn(Combine.BLK, 8));
        bld.setPc(new Pawn(Combine.BLK, 9));
        bld.setPc(new Pawn(Combine.BLK, 10));
        bld.setPc(new Pawn(Combine.BLK, 11));
        bld.setPc(new Pawn(Combine.BLK, 12));
        bld.setPc(new Pawn(Combine.BLK, 13));
        bld.setPc(new Pawn(Combine.BLK, 14));
        bld.setPc(new Pawn(Combine.BLK, 15));
        
        bld.setPc(new Pawn(Combine.WHT, 48));
        bld.setPc(new Pawn(Combine.WHT, 49));
        bld.setPc(new Pawn(Combine.WHT, 50));
        bld.setPc(new Pawn(Combine.WHT, 51));
        bld.setPc(new Pawn(Combine.WHT, 52));
        bld.setPc(new Pawn(Combine.WHT, 53));
        bld.setPc(new Pawn(Combine.WHT, 54));
        bld.setPc(new Pawn(Combine.WHT, 55));
        bld.setPc(new Rook(Combine.WHT, 56));
        bld.setPc(new Knight(Combine.WHT, 57));
        bld.setPc(new Bishop(Combine.WHT, 58));
        bld.setPc(new Queen(Combine.WHT, 59));
        bld.setPc(new King(Combine.WHT, 60));
        bld.setPc(new Bishop(Combine.WHT, 61));
        bld.setPc(new Knight(Combine.WHT, 62));
        bld.setPc(new Rook(Combine.WHT, 63));
        
        bld.setMvMkr(Combine.WHT);
        
        return bld.build();
    }

    private Collection<Mv> calAllMoves(final Collection<Pc> pcs) {
        return pcs.stream().flatMap(pc -> pc.calAllMoves(this).stream())
                      .collect(Collectors.toList());
    }

    public static class Bldr {

        Map<Integer, Pc> brdConf;
        Combine nextMoveMaker;
        Mv trsnMove;

        public Bldr() {
            this.brdConf = new HashMap<>(32, 1.0f);
        }

        public Bldr setMvTrnsn(final Mv trsnMove) {
            this.trsnMove = trsnMove;
            return this;
        }

        public Brd build() {
            return new Brd(this);
        }

        public Bldr setMvMkr(final Combine nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Bldr setPc(final Pc pc) {
            this.brdConf.put(pc.getPcPos(), pc);
            return this;
        }

    }

}
