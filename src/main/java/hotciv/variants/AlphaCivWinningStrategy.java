package hotciv.variants;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.standard.GameImpl;

import java.util.ArrayList;

public class AlphaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(GameImpl game) {
        if(game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }
}
