package hotciv.brokerClient;

import hotciv.framework.City;
import hotciv.framework.Player;

public class stubCityBrokerClient implements City {
    @Override
    public Player getOwner() {
        return Player.YELLOW;
    }

    @Override
    public int getSize() {
        return 69;
    }

    @Override
    public int getTreasury() {
        return 420;
    }

    @Override
    public String getProduction() {
        return "FISK";
    }

    @Override
    public String getWorkforceFocus() {
        return "RØDE_PØLSER";
    }
}
