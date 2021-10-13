package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.variants.factories.GammaCivFactory;
import hotciv.variants.factories.ThetaCivFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestThetaCiv {
    private Game game;

    //Unit Start positions
    Position RedSettlerPosition = new Position(5,5);
    Position RedArcherPosition = new Position(3,8);
    Position BlueLegionPosition = new Position(4,4);
    Position BlueSandwormPosition = new Position(9,6);

    /************  FIXTURE FOR THETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
    }

    /************ TESTS FOR THETACIV CITIES ************/
    //region
    //endregion

    /************ TESTS FOR THETACIV UNITS************/
    //region
    //Testing that sandworms can only move in desserts
    @Test
    public void sandwormsShouldNotMoveOnNonDessertTiles(){
        //Go to blue's turn
        game.endOfTurn();
        //Assert that unit doesn't move to plains at 9,7
        assertThat(game.moveUnit(BlueSandwormPosition, new Position(9,7)), is(true));
    }
    //endregion

    /************ TESTS FOR THETACIV CITIES ************/
    //region
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
    //endregion

    /************ TESTS FOR THETACIV UNIT PLACEMENT ************/
    //region
    // Testing that Red starts with settler on 5,5.
    @Test
    public void shouldBeSettlerOn5_5OwnedByRed() {
        // Checking that the Settler is at position.
        assertThat(game.getUnitAt(RedSettlerPosition).getTypeString(), is(GameConstants.SETTLER));
        // Checking that the Settler is owned by Red.
        assertThat(game.getUnitAt(RedSettlerPosition).getOwner(), is(Player.RED));
    }

    // Testing that Red starts with archer on 3,8
    @Test
    public void shouldBeArcherOn3_8OwnedByRed() {
        // Checking that the archer is at position.
        assertThat(game.getUnitAt(RedArcherPosition).getTypeString(), is(GameConstants.ARCHER));
        // Checking that the archer is owned by Red.
        assertThat(game.getUnitAt(RedArcherPosition).getOwner(), is(Player.RED));
    }

    // Testing that Blue starts with legion 4,4.
    @Test
    public void shouldBeLegionOn4_4OwnedByBlue() {

        // Checking that the legion is at the position.
        assertThat(game.getUnitAt(BlueLegionPosition).getTypeString(), is(GameConstants.LEGION));
        // Checking that the legion is owned by Blue.
        assertThat(game.getUnitAt(BlueLegionPosition).getOwner(), is(Player.BLUE));
    }

    //Testing that Blue starts with a sandworm on (9,6)
    @Test
    public void shouldBeSandwormOn9_6OwnedByBlue(){
        // Checking that the legion is at the position.
        assertThat(game.getUnitAt(BlueSandwormPosition).getTypeString(), is(GameConstants.SANDWORM));
        // Checking that the legion is owned by Blue.
        assertThat(game.getUnitAt(BlueSandwormPosition).getOwner(), is(Player.BLUE));
    }
    //endregion

    /************ TESTING FOR THETACIV WORLD LAYOUT ************/
    //region
    // Testing for dessert on position 0,5
    @Test
    public void shouldBeDessertAt0_5() {
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(GameConstants.DESSERT));
    }

    // Testing for dessert on position 4,8
    @Test
    public void shouldBeDessertAt4_8() {
        assertThat(game.getTileAt(new Position(4,8)).getTypeString(), is(GameConstants.DESSERT));
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

    // Testing for ocean on position 15,15
    @Test
    public void shouldBeOceanAt15_15() {
        assertThat(game.getTileAt(new Position(15,15)).getTypeString(), is(GameConstants.OCEANS));
    }
    //endregion

}