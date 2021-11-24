package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;

public class GameProxy implements Game, ClientProxy {

    public static final String GAME_OBJECTID = "singleton";

    private final Requestor requestor;

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Tile getTileAt(Position p) {
        return null;
    }

    @Override
    public Unit getUnitAt(Position p) {
        return null;
    }

    @Override
    public City getCityAt(Position p) {
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return null;
    }

    @Override
    public Player getWinner() {
        Player uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETWINNER_METHOD,
                Player.class);
        return uid;
    }

    @Override
    public int getAge() {
        int uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETAGE_METHOD,
                int.class);
        return uid;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    @Override
    public void endOfTurn() {

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {

    }

    @Override
    public void performUnitActionAt(Position p) {

    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
