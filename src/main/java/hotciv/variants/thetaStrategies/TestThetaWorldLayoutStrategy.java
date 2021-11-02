package hotciv.variants.thetaStrategies;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class TestThetaWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public HashMap<Position, Tile> createWorld() {
        String[] layout =
                new String[] {
                "...oododdoo.....",
                "..ohhdddddddddd.",
                ".oddddMddd...dd.",
                ".odMMddododdddoo",
                "...ofodddddddo..",
                ".ofddddoddddddo.",
                "...odd..dddddd..",
                ".oddddddddddoM..",
                ".oddddddddddddf.",
                "offddddoddddddoo",
                "oodddodo...oddoo",
                ".ooMMdddd...ddd.",
                "..oodddddfoddd..",
                "....oddddMMdo...",
                "..ooddddddMd....",
                ".....oddddddoo..",
        };
        // Conversion...
        HashMap<Position,Tile> worldMap = new HashMap<>();
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
                if ( tileChar == 'd' ) { type = GameConstants.DESSERT; }
                Position p = new Position(r,c);
                worldMap.put( p, new TileImpl(type));
            }
        }
        return worldMap;
    }

    @Override
    public HashMap<Position, City> placeCities() {
        HashMap<Position,City> cityMap = new HashMap<>();

        Position RedCityPosition = new Position(8,12);
        Position BlueCityPosition = new Position(4,5);
        City redCity = new CityImpl(Player.RED);
        City blueCity = new CityImpl(Player.BLUE);

        cityMap.put(RedCityPosition, redCity);
        cityMap.put(BlueCityPosition, blueCity);
        return cityMap;
    }

    @Override
    public HashMap<Position, Unit> placeUnits() {
        HashMap<Position, Unit> unitMap = new HashMap<>();
        //Place units around sandworm
        unitMap.put(new Position(8,5), new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(new Position(8,7), new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(new Position(10,6), new UnitImpl(GameConstants.ARCHER, Player.BLUE));
        unitMap.put(new Position(10,7), new UnitImpl(GameConstants.ARCHER, Player.BLUE));

        //Place sandworm in the middle
        unitMap.put(new Position(9,6), new UnitImpl(GameConstants.SANDWORM, Player.BLUE));

        return unitMap;
    }
}
