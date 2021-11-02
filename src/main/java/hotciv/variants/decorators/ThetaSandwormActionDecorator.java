package hotciv.variants.decorators;

import hotciv.Utility.Utility;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitActionStrategy;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.Iterator;

public class ThetaSandwormActionDecorator implements UnitActionStrategy {

    private UnitActionStrategy decoratee;
    public ThetaSandwormActionDecorator(UnitActionStrategy unitActionStrategy) {
        decoratee = unitActionStrategy;
    }

    @Override
    public void performAction(Position p, GameImpl game) {
        UnitImpl unit = (UnitImpl) game.getUnitAt(p);
        String unitType = game.getUnitAt(p).getTypeString();
        if (unitType.equals(GameConstants.SANDWORM)) {
            Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(p);
            while (listOfNeighbours.hasNext()) {
                Position position = listOfNeighbours.next();
                if (game.getUnitAt(position) != null) {
                    UnitImpl unitAtPosition = (UnitImpl) game.getUnitAt(position);
                    if (unitAtPosition.getOwner() != unit.getOwner()) {
                        game.unitMap.remove(position);
                    }
                }
            }
        } else {
            decoratee.performAction(p, game);
        }
    }
}
