package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.factories.GammaCivFactory;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGammaCiv {
    private Game game;

    /************  FIXTURE FOR GAMMACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new GammaCivFactory());
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

    //City should have 1 population after being made
    @Test
    public void shouldHave1PopulationAfterBeingCreated(){
        Position cityPosition = GameConstants.RedSettler_Start_Position;
        game.performUnitActionAt(cityPosition);
        assertThat(game.getCityAt(cityPosition).getSize(), is(1));
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
