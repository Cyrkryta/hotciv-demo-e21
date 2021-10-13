
package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.variants.factories.AlphaCivFactory;
import hotciv.variants.factories.SemiCivFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSemiCiv {
    private Game game;

    /************  FIXTURE FOR SEMICIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new SemiCivFactory());
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
    public void shouldIncrementBy1From1970AndAfterwards() {
        performRound(127);
        assertThat(game.getAge(), is(2000));
    }

    /************ TESTING FOR SEMICIV WORLD LAYOUT ************/
    // Testing for mountain on position 0,5
    @Test
    public void shouldBeMountainAt0_5() {
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(GameConstants.MOUNTAINS));
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

    // Testing that Red starts with settler on 5,5.
    @Test
    public void shouldBeSettlerOn5_5OwnedByRed() {
        // Creating the position.
        Position p = new Position(5,5);
        // Checking that the Settler is at position.
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.SETTLER));
        // Checking that the Settler is owned by Red.
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
    }

    // Testing that Red starts with archer on 3,8
    @Test
    public void shouldBeArcherOn3_8OwnedByRed() {
        // Creating the position.
        Position p = new Position(3,8);
        // Checking that the archer is at position.
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.ARCHER));
        // Checking that the archer is owned by Red.
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
    }

    /************ TESTS FOR SETTLER ACTION ************/
    // Testing if unit is removed when city is created.
    @Test
    public void shouldRemoveUnitWhenCreatingCity() {
        Position unitPosition = new Position(5,5);
        assertThat(game.getUnitAt(unitPosition), is(notNullValue()));
        game.performUnitActionAt(unitPosition);
        assertThat(game.getUnitAt(unitPosition), is(nullValue()));
    }

    //City should have 1 population after being made
    @Test
    public void shouldHave1PopulationAfterBeingCreated(){
        Position cityPosition = new Position(5,5);
        game.performUnitActionAt(cityPosition);
        assertThat(game.getCityAt(cityPosition).getSize(), is(1));
    }

    /************ TESTS FOR ARCHER ACTION ************/
    //Testing that a fortified archer has 6 defense (Double of 3)
    @Test
    public void shouldHave6DefenseWhenFortified(){
        Position unitPosition = new Position(3,8);
        game.performUnitActionAt(unitPosition);
        assertThat(game.getUnitAt(unitPosition).getDefensiveStrength(), is(6));
    }

    /************  TESTS FOR WINNING STRATEGY ************/
    // Test that checks if player wins with all cities before 20 rounds.
    @Test
    public void shouldWinWithAllCitiesBeforeRound20() {
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4,5));
        assertThat(game.getWinner(), is(nullValue()));
        performRound(10);
        blueCity.changeOwner(Player.RED);
        assertThat(game.getWinner(), is(Player.RED));
    }

    // Test that checks if player doesn't win if all cities after 20 rounds.
    @Test
    public void shouldNotWinWithAllCitiesAfterRound20() {
        CityImpl blueCity = (CityImpl) game.getCityAt(new Position(4,5));
        assertThat(game.getWinner(), is(nullValue()));
        performRound(23);
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