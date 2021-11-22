package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;

public class StubGameBrokerClient implements Game, Servant {

    Position positionOfGreenCity = new Position(3, 8);

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
        if (p == positionOfGreenCity) {
            return new StubCity(Player.GREEN);
        }
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return Player.RED;
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
