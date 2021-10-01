package hotciv.variants;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

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

    @Override
    public HashMap<Position, City> placeCities() {
        HashMap<Position,City> cityMap = new HashMap<>();
        City redCity = new CityImpl(Player.RED);
        City blueCity = new CityImpl(Player.BLUE);
        cityMap.put(GameConstants.Red_City_Pos, redCity);
        cityMap.put(GameConstants.Blue_City_Pos, blueCity);
        return cityMap;
    }

    @Override
    public HashMap<Position, Unit> placeUnits() {
        HashMap<Position, Unit> unitMap = new HashMap<>();
        unitMap.put(GameConstants.RedSettler_Start_Position, new UnitImpl(GameConstants.SETTLER, Player.RED));
        unitMap.put(GameConstants.RedArcher_Start_Position, new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(GameConstants.BlueLegion_Start_Position, new UnitImpl(GameConstants.LEGION, Player.BLUE));
        return unitMap;
    }
}
