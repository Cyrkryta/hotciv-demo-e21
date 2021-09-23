package hotciv.variants;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.framework.WorldLayoutStrategy;
import hotciv.standard.TileImpl;

import java.util.HashMap;

public class AlphaWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public HashMap<Position, Tile> createWorld() {
        HashMap<Position, Tile> worldMap = new HashMap<>();
        for (int i = 0; i <= GameConstants.WORLDSIZE - 1; i++) {
            for (int j = 0; j <= GameConstants.WORLDSIZE - 1; j++) {
                worldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }
        // Creation of tile with ocean.
        worldMap.put(GameConstants.Ocean_Tile_Position, new TileImpl(GameConstants.OCEANS));
        // Creation of tile with mountain.
        worldMap.put(GameConstants.Mountain_Tile_Position, new TileImpl(GameConstants.MOUNTAINS));
        // Creation of tile with hills.
        worldMap.put(GameConstants.Hill_Tile_Position, new TileImpl(GameConstants.HILLS));
        return worldMap;
    }
}
