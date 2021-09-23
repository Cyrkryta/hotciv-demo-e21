package hotciv.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public interface WinningStrategy {
    Player calculateWinner(int currentAge, ArrayList<City> listOfCities);
}
