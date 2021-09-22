package hotciv.variants;

import hotciv.framework.AgeStrategy;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import org.junit.jupiter.api.BeforeEach;

public class TestBetaCiv {
    private Game game;
    private City redCity;
    private AgeStrategy LinearAgeStrategy = new LinearAgeStrategy();

    /************  FIXTURE FOR ALPHACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(LinearAgeStrategy);
        redCity = new CityImpl(Player.RED); //Create test city
    }
}
