package hotciv.variants;

import hotciv.framework.AttackingStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;

public class AlphaAttackingStrategy implements AttackingStrategy {
    @Override
    public boolean calculateAttack(Position from, Position to, Game game) {
        return true;
    }
}