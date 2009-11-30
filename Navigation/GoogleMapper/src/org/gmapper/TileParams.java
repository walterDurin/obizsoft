package org.gmapper;

import org.gmapper.types.IntPoint;

/**
 * User: Vova
 * Time: 23:46:44
 *
 * @author ognivo777@mail.ru
 */
public class TileParams {
    private IntPoint pos;
    private int level;
    private int type;

    public TileParams(IntPoint pos, int level, int type) {
        this.pos = pos;
        this.level = level;
        this.type = type;
    }

    public IntPoint getPos() {
        return pos;
    }

    public void setPos(IntPoint pos) {
        this.pos = pos;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
