package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;

public class CityProxy implements City, ClientProxy {

    public static String CITY_OBJECTID;
    private final Requestor requestor;

    public CityProxy(String id, Requestor requestor) {
        CITY_OBJECTID = id;
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player uid = requestor.sendRequestAndAwaitReply(CITY_OBJECTID, OperationNames.CITY_GETOWNER_METHOD,
                Player.class);
        return uid;
    }

    @Override
    public int getSize() {
        int uid = requestor.sendRequestAndAwaitReply(CITY_OBJECTID, OperationNames.CITY_GETSIZE_METHOD,
                int.class);
        return uid;
    }

    @Override
    public int getTreasury() {
        int uid = requestor.sendRequestAndAwaitReply(CITY_OBJECTID, OperationNames.CITY_GETTREASURY_METHOD,
                int.class);
        return uid;
    }

    @Override
    public String getProduction() {
        String uid = requestor.sendRequestAndAwaitReply(CITY_OBJECTID, OperationNames.CITY_GETPRODUCTION_METHOD,
                String.class);
        return uid;
    }

    @Override
    public String getWorkforceFocus() {
        String uid = requestor.sendRequestAndAwaitReply(CITY_OBJECTID, OperationNames.CITY_GETWORKFORCEFOCUS_METHOD,
                String.class);
        return uid;
    }

    public String getId() {
        return CITY_OBJECTID;
    }
}
