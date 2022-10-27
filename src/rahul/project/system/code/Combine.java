package rahul.project.system.code;

import rahul.project.system.code.player.BlkPlyr;
import rahul.project.system.code.player.Plyr;
import rahul.project.system.code.player.WhtPlyr;

public enum Combine {

    WHT() {

        public boolean isWht() {
            return true;
        }

        public boolean isBlk() {
            return false;
        }

        public int getDir() {
            return UP_DIR;
        }

        public int getOppDir() {
            return DOWN_DIR;
        }

        public Plyr choosePlyrByAlli(final WhtPlyr whtPlyr, final BlkPlyr blkPlyr) {
            return whtPlyr;
        }

        public String toString() {
            return "White";
        }

    },
    BLK() {

        public boolean isWht() {
            return false;
        }

        public boolean isBlk() {
            return true;
        }

        public int getDir() {
            return DOWN_DIR;
        }

        public int getOppDir() {
            return UP_DIR;
        }

        public Plyr choosePlyrByAlli(final WhtPlyr whtPlyr, final BlkPlyr blkPlyr) {
            return blkPlyr;
        }

        public String toString() {
            return "Black";
        }
    };

    public abstract int getDir();

    public abstract int getOppDir();

    public abstract boolean isWht();

    public abstract boolean isBlk();

    public abstract Plyr choosePlyrByAlli(final WhtPlyr whtPlyr, final BlkPlyr blkPlyr);

    private static final int UP_DIR = -1;

    private static final int DOWN_DIR = 1;

}