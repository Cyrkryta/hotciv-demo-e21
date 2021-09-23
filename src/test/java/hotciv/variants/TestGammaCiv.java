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

    /************ TESTS FOR SETTLER ACTION ************/
    // Testing if unit is removed when city is created.
    @Test
    public void shouldRemoveUnitWhenCreatingCity() {
        Position unitPosition = GameConstants.RedSettler_Start_Position;
        assertThat(game.getUnitAt(unitPosition), is(notNullValue()));
        game.performUnitActionAt(unitPosition);
        assertThat(game.getUnitAt(unitPosition), is(nullValue()));
    }

    // Testing if city is created when settler performs action.
    @Test
    public void shouldCreateCityWhenUnitPerformAction() {
        Position cityPosition = GameConstants.RedSettler_Start_Position;
        assertThat(game.getCityAt(cityPosition), is(nullValue()));
        game.performUnitActionAt(cityPosition);
        assertThat(game.getCityAt(cityPosition), is(notNullValue()));
    }

    // Testing if new city owner is the same as the settlers.
    @Test
    public void shouldChangeOwnerInCityWhenUnitPerformsAction() {
        Position cityPosition = GameConstants.RedSettler_Start_Position;
        assertThat(game.getUnitAt(cityPosition).getOwner(), is(Player.RED));
        game.performUnitActionAt(cityPosition);
        assertThat(game.getCityAt(cityPosition).getOwner(), is(Player.RED));
    }
}
