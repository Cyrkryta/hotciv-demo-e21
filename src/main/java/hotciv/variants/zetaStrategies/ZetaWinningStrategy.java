package hotciv.variants.zetaStrategies;

import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.standard.GameImpl;

public class ZetaWinningStrategy implements WinningStrategy {
    WinningStrategy betaWinningStrategy;
    WinningStrategy epsilonWinningStrategy;
    WinningStrategy currentState = null;
    int roundCount = 0;

    public ZetaWinningStrategy(WinningStrategy betaWinningStrategy, WinningStrategy epsilonWinningStrategy) {
        this.betaWinningStrategy = betaWinningStrategy;
        this.epsilonWinningStrategy = epsilonWinningStrategy;
    }

    @Override
    public Player calculateWinner(GameImpl game) {
        if (roundCount <= 20) {
            currentState = betaWinningStrategy;
        } else {
            currentState = epsilonWinningStrategy;
        }
        return currentState.calculateWinner(game);
    }

    @Override
    public void incrementBattlesWonBy(Player player) {
        currentState.incrementBattlesWonBy(player);
    }

    @Override
    public void incrementRoundsPlayed() {
        roundCount++;
    }
}
