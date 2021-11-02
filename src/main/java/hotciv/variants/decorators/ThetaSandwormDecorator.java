package hotciv.variants.decorators;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.ValidMoveStrategy;
import hotciv.standard.GameImpl;

public class ThetaSandwormDecorator implements ValidMoveStrategy {
    private ValidMoveStrategy decoratee;
    public ThetaSandwormDecorator(ValidMoveStrategy validMoveStrategy) {
        decoratee = validMoveStrategy;
    }

    @Override
    public boolean moveIsPossible(Position from, Position to, GameImpl game) {return decoratee.moveIsPossible(from, to, game);}

    @Override
    public boolean movesToNeighbourTile(Position from, Position to) {
        return decoratee.movesToNeighbourTile(from, to);
    }

    @Override
    public boolean isMovableTerrain(Position from, Position to, GameImpl game) {
        String unitType = game.getUnitAt(from).getTypeString();
        String tileType = game.getTileAt(to).getTypeString();
        if (unitType.equals(GameConstants.SANDWORM) && !tileType.equals(GameConstants.DESSERT)) {
            return false;
        } else {
            return decoratee.isMovableTerrain(from, to, game);
        }

    }
}
