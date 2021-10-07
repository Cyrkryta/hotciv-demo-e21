package hotciv.variants.alphaStrategies;

import hotciv.framework.AttackingStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class AlphaAttackingStrategy implements AttackingStrategy {
    @Override
    public boolean calculateAttack(Position from, Position to, Game game) {
        return true;
    }
}
