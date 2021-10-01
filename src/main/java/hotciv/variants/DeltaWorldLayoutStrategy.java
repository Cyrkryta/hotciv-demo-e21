package hotciv.variants;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class DeltaWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public HashMap<Position, Tile> createWorld() {
        String[] layout =
                new String[] {
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Conversion...
        HashMap<Position,Tile> worldMap = new HashMap<Position,Tile>();
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                worldMap.put( p, new TileImpl(type));
            }
        }
        return worldMap;
    }

    @Override
    public HashMap<Position, City> placeCities() {
        HashMap<Position,City> cityMap = new HashMap<>();
        City redCity = new CityImpl(Player.RED);
        City blueCity = new CityImpl(Player.BLUE);
        cityMap.put(new Position(8,12), redCity);
        cityMap.put(new Position(4,5), blueCity);
        return cityMap;
    }

    @Override
    public HashMap<Position, Unit> placeUnits() {
        HashMap<Position, Unit> unitMap = new HashMap<>();
        unitMap.put(new Position(5,5), new UnitImpl(GameConstants.SETTLER, Player.RED));
        unitMap.put(new Position(3,8), new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(new Position(4,4), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        return unitMap;
    }
}
