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

public class TestBetaCiv {
    private Game game;
    private AgeStrategy slowingAgeStrategy = new SlowingAgeStrategy();
    private WinningStrategy betaCivWinningStrategy = new BetaCivWinningStrategy();
    private UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(slowingAgeStrategy, betaCivWinningStrategy, alphaUnitActionStrategy);
    }

    /************  TESTS FOR GAME AGING ************/

    @Test
    public void shouldAge100PerRoundIn4000BC(){
        assertThat(game.getAge(), is(-4000));
        performRound(39);
        assertThat(game.getAge(), is(-100));
    }

    @Test
    public void shouldAge100YearsIfTheYearIs100BC(){
        //Increment game 3900 year
        performRound(40);
        assertThat(game.getAge(), is(0));
    }

    @Test
    public void shouldAge1InFirst0Year() {
        performRound(41);
        assertThat(game.getAge(), is(1));
    }

    @Test
    public void shouldAgeMinus1When1() {
        performRound(42);
        assertThat(game.getAge(), is(0));
    }

    @Test
    public void shouldAge50InSecond0Year() {
        performRound(43);
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void shouldIncrementBy50From50AdTo1750AD(){
        performRound(77);
        assertThat(game.getAge(), is(1750));
    }

    @Test
    public void shouldIncrementBy25From1750ADTo1900AD(){
        performRound(83);
        assertThat(game.getAge(), is(1900));
    }

    @Test
    public void shouldIncrementBy5From1900ADTo1970AD() {
        performRound(97);
        assertThat(game.getAge(), is(1970));
    }

    @Test
    public void shouldIncrementBy1From1970AndAfterwards() {
        performRound(127);
        assertThat(game.getAge(), is(2000));
    }

    /************ TESTS FOR GAME WINNING ************/
    @Test
    public void redShouldWinIfItOwnsAllCities(){
       CityImpl blueCity = (CityImpl) game.getCityAt(GameConstants.Blue_City_Pos);
       blueCity.changeOwner(Player.RED);
       assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueShouldWinIfItOwnsAllCities(){
        CityImpl redCity = (CityImpl) game.getCityAt(GameConstants.Red_City_Pos);
        redCity.changeOwner(Player.BLUE);
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    /************ ASSISTING METHODS FOR TESTS ************/
    // Helps increment a round.
    private void performRound(int number) {
        for (int i=0; i<number; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }
}
