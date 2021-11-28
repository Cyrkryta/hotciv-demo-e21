package hotciv.brokerClient;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Tile;

public class stubTileBrokerClient implements Tile {


    @Override
    public String getTypeString() {
        return "GHETTO";
    }
}
