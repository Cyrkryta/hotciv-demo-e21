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
        endTurns(78);
        endTurns(2);
        assertThat(game.getAge(),is(0));
    }

    @Test
    public void shouldAge50After50AD(){
        //Age the game 4050 years
        endTurns(86);
    }

    /************ ASSISTING METHODS FOR TESTS ************/
    //Helps increment turns by x amount
    private void endTurns(int x) {
        for (int i = 0; i < x; i++) {
            game.endOfTurn();
        }
    }
}
