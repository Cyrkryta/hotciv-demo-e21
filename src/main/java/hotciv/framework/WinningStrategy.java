package hotciv.framework;

import hotciv.standard.GameImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public interface WinningStrategy {
    Player calculateWinner(GameImpl game);
}
