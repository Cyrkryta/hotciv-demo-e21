package hotciv.variants;

import hotciv.framework.*;
import hotciv.standard.GameImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaCiv {
    private Game game;
    private AgeStrategy linearAgeStrategy = new LinearAgeStrategy();
    private WinningStrategy alphaCivWinningStrategy = new AlphaCivWinningStrategy();
    private UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();
    private WorldLayoutStrategy deltaWorldLayoutStrategy = new DeltaWorldLayoutStrategy();

    /************  FIXTURE FOR DELTACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, alphaCivWinningStrategy, alphaUnitActionStrategy, deltaWorldLayoutStrategy);
    }

    /************ TESTING FOR DELTACIV WORLD LAYOUT ************/
    // Testing for mountain on position 0,5
    @Test
    public void shouldBeMountainAt0_5() {
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    // Testing for hill on position 4,8
    @Test
    public void shouldBeHillAt4_8() {
        assertThat(game.getTileAt(new Position(4,8)).getTypeString(), is(GameConstants.HILLS));
    }

    // Testing for forest on position 5,2
    @Test
    public void shouldBeForestAt5_2() {
        assertThat(game.getTileAt(new Position(5,2)).getTypeString(), is(GameConstants.FOREST));
    }

    // Testing for ocean on position 15,0
    @Test
    public void shouldBeOceanAt15_0() {
        assertThat(game.getTileAt(new Position(15,0)).getTypeString(), is(GameConstants.OCEANS));
    }

    // Testing for plain on position 7,5
    @Test
    public void shouldBePlainAt7_5() {
        assertThat(game.getTileAt(new Position(7,5)).getTypeString(), is(GameConstants.PLAINS));
    }

    //Testing for forest on position 9,11
    @Test
    public void shouldBeForestAt9_11() {
        assertThat(game.getTileAt(new Position(9,11)).getTypeString(), is(GameConstants.FOREST));
    }

    // Testing for ocean on position 15,15
    @Test
    public void shouldBeOceanAt15_15() {
        assertThat(game.getTileAt(new Position(15,15)).getTypeString(), is(GameConstants.OCEANS));
    }

    /************ TESTING FOR DELTACIV CITIES ************/

    //Testing that Red has a city on (8,12)
    @Test
    public void shouldHaveRedCityOn8_12(){
        Position redCityPosition = new Position(8,12);
        assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    }

    //Testing that Blue has a city on (8,12)
    @Test
    public void shouldHaveBlueCityOn4_5(){
        Position blueCityPosition = new Position(4,5);
        assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
    }
}
