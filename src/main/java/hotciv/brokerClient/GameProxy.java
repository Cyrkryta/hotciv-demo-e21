package hotciv.brokerClient;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.stub.StubServants.StubCityServant;

import java.util.ArrayList;

public class GameProxy implements Game, ClientProxy {

    public static final String GAME_OBJECTID = "singleton";
    private final Requestor requestor;
    protected ArrayList<GameObserver> gameObservers = new ArrayList<>();

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Tile getTileAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETTILEAT_METHOD,
                String.class, p);
        if (id == null) {
            return null;
        } else {
            Tile proxy = new TileProxy(id, requestor);
            return proxy;
        }
    }

    @Override
    public Unit getUnitAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETUNITAT_METHOD,
                String.class, p);
        if (id == null) {
            return null;
        } else {
            Unit proxy = new UnitProxy(id, requestor);
            return proxy;
        }
    }

    @Override
    public City getCityAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETCITYAT_METHOD,
                String.class, p);
        if(id == null){
            return null;
        } else {
            City proxy = new CityProxy(id, requestor);
            return proxy;
        }
    }

    @Override
    public Player getPlayerInTurn() {
        Player uid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_GETINTURN_METHOD,
                Player.class);
        return uid;
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
        Boolean valid = requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_MOVEUNIT_METHOD,
                boolean.class, from, to);
        if (valid) {
            for (GameObserver gameObserver: gameObservers) {
                gameObserver.worldChangedAt(from);
                gameObserver.worldChangedAt(to);
            }
        }
        return valid;
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_ENDOFTURN_METHOD,
                void.class);
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.turnEnds(getPlayerInTurn(), getAge());
        }
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_CHANGEWORKFORCE_METHOD,
                void.class, p, balance);
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.cityWorkFocusChanges(balance);
        }
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_CHANGEPRODUCTION_METHOD,
                void.class, p, unitType);
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.cityProductionChanged(unitType);
        }
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply(GAME_OBJECTID, OperationNames.GAME_PERFORMUNITACTION_METHOD,
                void.class, p);
    }

    @Override
    public void addObserver(GameObserver observer) {
        gameObservers.add(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        for(GameObserver gameObserver: gameObservers) {
            gameObserver.tileFocusChangedAt(position);
        }
    }
}
