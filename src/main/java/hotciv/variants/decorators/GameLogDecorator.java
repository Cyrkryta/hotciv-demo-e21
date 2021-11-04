package hotciv.variants.decorators;

import hotciv.framework.*;
import hotciv.standard.GameImpl;

import java.util.ArrayList;

public class GameLogDecorator implements Game {
    private Game decoratee;
    private ArrayList<String> gameLog = new ArrayList<>();

    public GameLogDecorator(GameImpl game) {
        this.decoratee = game;
    }

    @Override
    public Tile getTileAt(Position p) {
        return decoratee.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return decoratee.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return decoratee.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return decoratee.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return decoratee.getWinner();
    }

    @Override
    public int getAge() {
        return decoratee.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        String fromCoordinates = "("+from.getRow()+","+from.getColumn()+")";
        String toCoordinates = "("+to.getRow()+","+to.getColumn()+")";
        if(decoratee.moveUnit(from, to)) {
            Player unitOwner = getUnitAt(from).getOwner();
            String unitType = getUnitAt(from).getTypeString();
            System.out.println(unitOwner+" moves "+unitType+" from "+fromCoordinates+" to "+toCoordinates);
        } else {
            System.out.println("Attempt to move unit from "+fromCoordinates+" to "+toCoordinates+" failed");
        }
        return decoratee.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        //RED ends turn
        System.out.println(getPlayerInTurn()+" ends turn");
        decoratee.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        if(getCityAt(p) == null) return;
        Player cityOwner = getCityAt(p).getOwner();
        if(cityOwner != getPlayerInTurn()) return;

        String cityCoordinates = "("+p.getRow()+","+p.getColumn()+")";
        String cityFocus = "food";
        if (balance.equals("hammer")) {cityFocus = "production";}
        System.out.println(cityOwner+" changes work force focus in city at "+cityCoordinates+" to "+cityFocus+" focus");

        decoratee.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        if(getCityAt(p) == null) return;
        Player cityOwner = getCityAt(p).getOwner();
        if(cityOwner != getPlayerInTurn()) return;

        String cityCoordinates = "("+p.getRow()+","+p.getColumn()+")";
        System.out.println(cityOwner+" changes production in city at "+cityCoordinates+" to "+unitType);

        decoratee.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        if(getUnitAt(p) == null) return;
        Player unitOwner = getUnitAt(p).getOwner();
        if(unitOwner != getPlayerInTurn()) return;

        String unitCoordinates = "("+p.getRow()+","+p.getColumn()+")";
        String unitType = getUnitAt(p).getTypeString();
        System.out.println(unitOwner+" performs "+unitType+" action at "+unitCoordinates);

        decoratee.performUnitActionAt(p);
    }
}
