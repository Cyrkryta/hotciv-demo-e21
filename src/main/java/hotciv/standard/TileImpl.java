package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

public class TileImpl implements Tile {
    private String tileName;
    public TileImpl (String tileName){
        this.tileName = tileName;
    }

    @Override
    public String getTypeString() {
        return tileName;
    }

    @Override
    public String getId() {
        return null;
    }
}
