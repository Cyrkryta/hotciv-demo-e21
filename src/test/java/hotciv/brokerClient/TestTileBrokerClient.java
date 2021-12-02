package hotciv.brokerClient;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Tile;

public class TestTileBrokerClient implements Tile {


    @Override
    public String getTypeString() {
        return "GHETTO";
    }

    @Override
    public String getId() {
        return null;
    }
}
