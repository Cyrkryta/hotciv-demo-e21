package hotciv.framework;

import hotciv.standard.GameImpl;

public interface ValidMoveStrategy {
    boolean moveIsPossible(Position from, Position to, GameImpl game);
    boolean movesToNeighbourTile(Position from, Position to);
    boolean isMovableTerrain(Position to, GameImpl game);
}
