package hotciv.variants;

import hotciv.framework.AttackingStrategy;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;

public class EpsilonAttackingStrategy implements AttackingStrategy {
    @Override
    public boolean calculateAttack(Position from, Position to, GameImpl game) {
        return false;
    }
}
