package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.OperationNames;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

    public static final String GAME_OBJECTID = "singleton";
    private final Requestor requestor;
    public TileProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.TILE_GETTYPESTRING_METHOD,
                String.class);
        return uid;
    }
}
