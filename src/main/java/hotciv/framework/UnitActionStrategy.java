package hotciv.framework;
import hotciv.standard.GameImpl;

public interface UnitActionStrategy {
    void performAction(Position p, GameImpl game);
}
