package hotciv.variants.epsilonStrategies;

import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.standard.GameImpl;

import java.util.ArrayList;
import java.util.Collections;

public class EpsilonCivWinningStrategy implements WinningStrategy {
    ArrayList<Player> playerVictories = new ArrayList<>();
    @Override
    public Player calculateWinner(GameImpl game) {
        Player[] players = Player.values();
        for(Player player: players){
            if(Collections.frequency(playerVictories, player) >= 3){
                return player;
            }
        }
        return null;
    }

    public void incrementBattlesWonBy(Player player){
        playerVictories.add(player);
    }

    @Override
    public void incrementRoundsPlayed() {}
}
