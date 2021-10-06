package hotciv.variants;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.standard.GameImpl;

import java.util.*;

public class BetaCivWinningStrategy implements WinningStrategy {
    @Override
    public Player calculateWinner(GameImpl game) {
        ArrayList<City> listOfCities = game.getCityList();
        for (City city : listOfCities) {
            if (city.getOwner() != listOfCities.get(0).getOwner())
                return null;
        }
        return listOfCities.get(0).getOwner();
    }
}
