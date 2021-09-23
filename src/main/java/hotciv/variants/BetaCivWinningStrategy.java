package hotciv.variants;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BetaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(int currentAge, Collection<City> collectionOfCities) {
        collectionOfCities = new ArrayList<City>();
        Set<Player> setOfCityOwners = new HashSet<>();
        for (int i = 0; i<collectionOfCities.size(); i++){
        }
        return null;
    }
}
