package hotciv.variants.alphaStrategies;

import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.standard.GameImpl;

public class AlphaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(GameImpl game) {
        if(game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }

    @Override
    public void incrementBattlesWonBy(Player player) {}

    @Override
    public void incrementRoundsPlayed() {


    }
}
