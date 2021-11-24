package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;

public class CityProxy implements City, ClientProxy {

    public static final String GAME_OBJECTID = "singleton";
    private final Requestor requestor;

    public CityProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.CITY_GETOWNER_METHOD,
                Player.class);
        return uid;
    }

    @Override
    public int getSize() {
        int uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.CITY_GETSIZE_METHOD,
                int.class);
        return uid;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
