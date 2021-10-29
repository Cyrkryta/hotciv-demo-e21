package hotciv.variants.alphaStrategies;

import hotciv.Utility.Utility;
import hotciv.framework.GameConstants;
import hotciv.framework.ValidMoveStrategy;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlphaValidMoveStrategy implements ValidMoveStrategy {

    @Override
    public boolean moveIsPossible(Position from, Position to, GameImpl game) {
        //Checks tile conditions
        if (game.getUnitAt(from) == null) return false;
        if (from == to) return false;
        if (!movesToNeighbourTile(from, to)) return false;

        //Checks Unit conditions
        UnitImpl unit = (UnitImpl) game.getUnitAt(from);
        if (unit.getOwner() != game.playerInTurn) return false;
        if (!isMovableTerrain(to, game)) return false;
        if (!(unit.getMoveCount() > 0)) return false;

        boolean ownUnitAtTo = game.getUnitAt(to) != null && game.getUnitAt(to).getOwner() == game.playerInTurn;
        if (ownUnitAtTo) return false;

        //Handles combat if moving onto tile occupied by enemy unit
        boolean attackingEnemyUnit = game.getUnitAt(to) != null && game.getUnitAt(to).getOwner() != game.getUnitAt(from).getOwner();
        if (attackingEnemyUnit) {
            return game.handleAttack(from, to);
        }
        return true;
    }

    @Override
    public boolean movesToNeighbourTile(Position from, Position to) {
        List<Position> neighbourList = new ArrayList<>();
        Iterator<Position> neighbourIterator = Utility.get8neighborhoodIterator(from);
        while (neighbourIterator.hasNext()) {
            neighbourList.add(neighbourIterator.next());
        }
        return neighbourList.contains(to);
    }

    @Override
    public boolean isMovableTerrain(Position to, GameImpl game) {
        String tileType = game.getTileAt(to).getTypeString();
        return GameConstants.regularMovableTerrain.contains(tileType);
    }
}
