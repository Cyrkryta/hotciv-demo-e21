package hotciv.variants;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

import java.util.ArrayList;

public class AlphaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(int currentAge, ArrayList<City> listOfCities) {
        if(currentAge == -3000) {
            return Player.RED;
        }
        return null;
    }
}
