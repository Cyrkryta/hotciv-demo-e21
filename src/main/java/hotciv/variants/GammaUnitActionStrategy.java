package hotciv.variants;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitActionStrategy;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

public class GammaUnitActionStrategy implements UnitActionStrategy {

    @Override
    public void performAction(Position p, GameImpl game) {
        if (GameConstants.SETTLER.equals(game.getUnitAt(p).getTypeString())) {
            game.gammaCivCreateCity(p);
        } else if (GameConstants.ARCHER.equals(game.getUnitAt(p).getTypeString())) {
            UnitImpl archerUnit = (UnitImpl) game.getUnitAt(p);
            archerUnit.fortifyUnit();
        }
    }
}