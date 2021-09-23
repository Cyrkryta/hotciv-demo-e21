package hotciv.variants;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

import java.util.Collection;
import java.util.Set;

public class AlphaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(int currentAge, Collection<City> collectionOfCities) {
        if(currentAge == -3000) {
            return Player.RED;
        }
        return null;
    }
}
