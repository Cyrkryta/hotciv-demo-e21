package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.variants.AlphaCivWinningStrategy;
import hotciv.variants.GammaUnitActionStrategy;
import hotciv.variants.LinearAgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestGammaCiv {
    private Game game;
    private AgeStrategy linearAgeStrategy = new LinearAgeStrategy();
    private WinningStrategy alphaCivWinningStrategy = new AlphaCivWinningStrategy();
    private UnitActionStrategy gammaUnitActionStrategy = new GammaUnitActionStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, alphaCivWinningStrategy, gammaUnitActionStrategy);
    }
}
