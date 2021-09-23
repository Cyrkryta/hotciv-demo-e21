package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitActionStrategy;
import hotciv.standard.GameImpl;

public class GammaUnitActionStrategy implements UnitActionStrategy {

    @Override
    public void performAction(Position p, GameImpl game) {
        if (GameConstants.SETTLER == game.getUnitAt(p).getTypeString()) {
            game.gammaCivCreateCity(p);
        } else if (GameConstants.ARCHER == game.getUnitAt(p).getTypeString()) {

        }
    }
}
