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
    private WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, alphaCivWinningStrategy, gammaUnitActionStrategy, alphaWorldLayoutStrategy);
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

    /************ TESTS FOR ARCHER ACTION ************/
    //Testing that a fortified archer has 6 defense (Double of 3)
    @Test
    public void shouldHave6DefenseWhenFortified(){
        Position unitPosition = GameConstants.RedArcher_Start_Position;
        game.performUnitActionAt(unitPosition);
        assertThat(game.getUnitAt(unitPosition).getDefensiveStrength(), is(6));
    }

    @Test
    public void shouldNotBeAbleToMoveIfFortified(){
        //Testing that units cannot move after they are fortified
        Position unitPosition = GameConstants.RedArcher_Start_Position;
        //Fortify unit
        game.performUnitActionAt(unitPosition);
        //Attempt to move unit 1 tile (2,0) to (2,1)
        assertThat(game.moveUnit(unitPosition, new Position(2,1)), is(false));
    }

    @Test
    public void shouldBeAbleToMoveAfterUnFortifying(){
        //Testing that units can move after they are un-fortified
        Position unitPosition = GameConstants.RedArcher_Start_Position;
        //Fortify unit
        game.performUnitActionAt(unitPosition);
        //Un-Fortify unit
        game.performUnitActionAt(unitPosition);
        //Attempt to move unit 1 tile (2,0) to (2,1)
        assertThat(game.moveUnit(unitPosition, new Position(2,1)), is(true));
    }
}
