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
    public boolean moveIsPossible(Position from, Position to, GameImpl game) {
        if (game.getUnitAt(from).getTypeString() == GameConstants.SANDWORM && game.getTileAt(to).getTypeString() != GameConstants.DESSERT) {
            return false;
        }
        return decoratee.moveIsPossible(from, to, game);
    }

    @Override
    public boolean movesToNeighbourTile(Position from, Position to) {
        return decoratee.movesToNeighbourTile(from, to);
    }

    @Override
    public boolean isMovableTerrain(Position to, GameImpl game) {
        return decoratee.isMovableTerrain(to, game);}
}
