package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

import java.util.UUID;

public class TileImpl implements Tile {
    private String id;
    private String tileName;

    public TileImpl (String tileName){
        this.tileName = tileName;
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getTypeString() {
        return tileName;
    }

    @Override
    public String getId() {
        return id;
    }
}
