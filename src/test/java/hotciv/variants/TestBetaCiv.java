package hotciv.variants;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.LinearAgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestBetaCiv {
    private Game game;
    private AgeStrategy ageStrategy = new SlowingAgeStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(ageStrategy);
    }

    /************  TESTS FOR GAME AGING ************/
    @Test
    public void shouldAge100PerRoundIn4000BC(){
        assertThat(game.getAge(), is(-4000));
        endTurns(2);
        assertThat(game.getAge(), is(-3900));
    }

    @Test
    public void shouldAge100In100BC(){
        //Increment game 3900 year
        endTurns(80);
        assertThat(game.getAge(),is(0));
    }

    @Test
    public void shouldAge1InFirst0Year() {
        endTurns(82);
        assertThat(game.getAge(), is(1));
    }

    @Test
    public void shouldAgeMinus1When1() {
        endTurns(84);
        assertThat(game.getAge(),is(0));
    }

    @Test
    public void shouldAge50InSecond0Year() {
        endTurns(86);
        assertThat(game.getAge(),is(50));
    }

    @Test
    public void shouldIncrementBy50From50AdTo1750AD(){
        endTurns(154);
        assertThat(game.getAge(),is(1750));
    }

    @Test
    public void shouldIncrementBy25From1750AdTo1900AD(){
        endTurns(166);
        assertThat(game.getAge(),is(1900));
    }

    /************ ASSISTING METHODS FOR TESTS ************/
    //Helps increment turns by x amount
    private void endTurns(int x) {
        for (int i = 0; i < x; i++) {
            game.endOfTurn();
        }
    }
}
