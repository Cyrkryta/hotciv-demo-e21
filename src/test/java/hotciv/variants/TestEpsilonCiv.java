package hotciv.variants;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.variants.LinearAgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestEpsilonCiv {
    private Game game;
    private AgeStrategy linearAgeStrategy = new LinearAgeStrategy();
    private WinningStrategy alphaCivWinningStrategy = new EpsilonCivWinningStrategy();
    private UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();
    private WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
    private AttackingStrategy alphaAttackingStrategy = new EpsilonAttackingStrategy();

    /************  FIXTURE FOR ALPHACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, alphaCivWinningStrategy, alphaUnitActionStrategy, alphaWorldLayoutStrategy, alphaAttackingStrategy);
    }
}
