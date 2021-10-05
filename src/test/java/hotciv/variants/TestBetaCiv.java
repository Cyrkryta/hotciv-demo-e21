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
    private WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
    private AttackingStrategy alphaAttackingStrategy = new AlphaAttackingStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(slowingAgeStrategy, betaCivWinningStrategy, alphaUnitActionStrategy, alphaWorldLayoutStrategy, alphaAttackingStrategy);
    }

    /************  TESTS FOR GAME AGING ************/
    //region
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
    //endregion

    /************  UNITTESTS FOR GAME AGING ************/
    @Test
    public void shouldAge100YearsIfGivenYear4000BC(){
        assertThat(slowingAgeStrategy.calculateAge(-4000), is(-3900));
    }

    @Test
    public void shouldAge50YearsIfGivenYear1300AD(){
        assertThat(slowingAgeStrategy.calculateAge(1300), is(1350));
    }

    @Test
    public void shouldAge25YearsIfGivenYear1800AD(){
        assertThat(slowingAgeStrategy.calculateAge(1800), is(1825));
    }

    @Test
    public void shouldAge5YearsIfGivenYear1960AD(){
        assertThat(slowingAgeStrategy.calculateAge(1960), is(1965));
    }

    @Test
    public void shouldAge1YearIfGivenYear1980AD(){
        assertThat(slowingAgeStrategy.calculateAge(1980), is(1981));
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

    @Test
    public void shouldHaveNoWinnerIfNoPlayerOwnsAllCities(){
        CityImpl redCity = (CityImpl) game.getCityAt(GameConstants.Red_City_Pos);
        CityImpl blueCity = (CityImpl) game.getCityAt(GameConstants.Blue_City_Pos);
        redCity.changeOwner(Player.BLUE);
        blueCity.changeOwner(Player.RED);

        assertThat(game.getWinner(), is(nullValue()));
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
