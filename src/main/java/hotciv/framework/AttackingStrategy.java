package hotciv.framework;

import hotciv.standard.GameImpl;

public interface AttackingStrategy {
    boolean calculateAttack(Position from, Position to, GameImpl game);

}
