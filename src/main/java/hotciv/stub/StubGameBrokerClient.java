package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubServants.StubCityServant;
import hotciv.stub.StubServants.StubTileServant;
import hotciv.stub.StubServants.StubUnitServant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StubGameBrokerClient implements Game, Servant {
    private String id;

    private Map<Position, Unit> unitMap;
    private Map<Position, City> cityMap;
    private Map<Position,Tile> world;

    Player playerInTurn = Player.RED;

    public StubGameBrokerClient() {
        //Creating unique Game ID
        id = UUID.randomUUID().toString();

        defineWorld();
        // Put some units into play
        unitMap = new HashMap<>();
        unitMap.put(new Position(2,0), new StubUnitServant( GameConstants.ARCHER, Player.RED ));
        unitMap.put(new Position(2,1), new StubUnitServant( GameConstants.ARCHER, Player.RED ));
        unitMap.put(new Position(3,2), new StubUnitServant( GameConstants.LEGION, Player.BLUE ));
        unitMap.put(new Position(4,2), new StubUnitServant( GameConstants.SETTLER, Player.RED ));

        cityMap = new HashMap<>();
        cityMap.put((new Position(3,2)), new StubCityServant(Player.BLUE));
        cityMap.put((new Position(8,8)), new StubCityServant(Player.RED));
    }

    protected void defineWorld() {
        world = new HashMap<Position,Tile>();
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                Position p = new Position(r,c);
                world.put( p, new StubTileServant(GameConstants.PLAINS));
            }
        }
        // Create a little area around the theta unit of special terrain
        world.put(new Position(1,1), new StubTileServant(GameConstants.HILLS));
        world.put(new Position(1,2), new StubTileServant(GameConstants.FOREST));
        world.put(new Position(1,3), new StubTileServant(GameConstants.MOUNTAINS));
        world.put(new Position(1,4), new StubTileServant(GameConstants.OCEANS));
    }

    @Override
    public Tile getTileAt(Position p) {
        return world.get(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return unitMap.get(p);
    }

    @Override
    public City getCityAt(Position p) {
        return cityMap.get(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    @Override
    public Player getWinner() {
        return Player.YELLOW;
    }

    @Override
    public int getAge() {
        return 53;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    @Override
    public void endOfTurn() {
        if (playerInTurn == Player.RED) {
            playerInTurn = Player.BLUE;
        } else {
            playerInTurn = Player.RED;
        }
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        System.out.println("-- changeWorkForceFocusInCityAt was called changing production at: "+getPositionString(p)+" to "+balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println("-- changeProductionInCityAt was called changing production at: "+getPositionString(p)+" to "+unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println("-- performUnitActionAt was called performing unit action at: " + getPositionString(p));
    }

    @Override
    public void addObserver(GameObserver observer) {
        System.out.println("-- addObserver method has been called");
    }

    @Override
    public void setTileFocus(Position position) {
        System.out.println("-- setTileFocus method has been called setting the focus at: " +
                getPositionString(position));
    }

    private String getPositionString(Position p) {
        return "("+p.getColumn()+","+p.getRow()+")";
    }
}
