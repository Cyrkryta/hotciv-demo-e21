package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;

public class StubGameBrokerClient implements Game, Servant {
    StubCity stubCity = new StubCity(Player.GREEN);
    //Position positionOfGreenCity = new Position(3, 8);
    Player playerInTurn = Player.RED;

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
        return stubCity;
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
