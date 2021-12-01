package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.OperationNames;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

    public static String TILE_OBJECTID;
    private final Requestor requestor;
    public TileProxy(String id, Requestor requestor) {
        TILE_OBJECTID = id;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String uid = requestor.sendRequestAndAwaitReply(TILE_OBJECTID, OperationNames.TILE_GETTYPESTRING_METHOD,
                String.class);
        return uid;
    }

    public String getId() {
        return TILE_OBJECTID;
    }
}
