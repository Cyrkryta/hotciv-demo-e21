package hotciv.variants.thetaStrategies;

import hotciv.framework.ValidMoveStrategy;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;

public class ThetaValidMoveSandwormDecorator implements ValidMoveStrategy {
    private ValidMoveStrategy decoratee;

    public ThetaValidMoveSandwormDecorator(ValidMoveStrategy validMoveStrategy) {
        validMoveStrategy = new AlphaValidMoveStrategy();
    }

    @Override
    public boolean moveIsPossible(Position from, Position to, GameImpl game) {
        return false;
    }

    @Override
    public boolean movesToNeighbourTile(Position from, Position to) {
        return false;
    }

    @Override
    public boolean isMovableTerrain(Position to, GameImpl game) {
        return false;
    }
}
