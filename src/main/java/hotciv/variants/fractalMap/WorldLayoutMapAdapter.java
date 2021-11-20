package hotciv.variants.fractalMap;

import hotciv.framework.*;
import hotciv.standard.TileImpl;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;

public class WorldLayoutMapAdapter implements WorldLayoutStrategy {

    private ThirdPartyFractalGenerator fractalGenerator;
    public WorldLayoutMapAdapter() {
        fractalGenerator = new ThirdPartyFractalGenerator();
    }

    @Override
    public HashMap<Position, Tile> createWorld() {
        HashMap<Position,Tile> worldMap = new HashMap<>();
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = fractalGenerator.getLandscapeAt(r,c);
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
        return null;
    }

    @Override
    public HashMap<Position, Unit> placeUnits() {
        return null;
    }
}
