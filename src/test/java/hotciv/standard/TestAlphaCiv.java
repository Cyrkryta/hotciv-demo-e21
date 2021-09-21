package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Aug 2020 for JUnit 5 includes
 * Updated Oct 2015 for using Hamcrest matchers
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestAlphaCiv {
    private Game game;
    private City redCity;

    /************  FIXTURE FOR ALPHACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl();
        redCity = new CityImpl(Player.RED); //Create test city
    }

    // FRS p. 455 states that 'Red is the first player to take a turn'.

    /************ TESTS FOR PLAYERS ************/
    // Testing that Red is the first player to take turn.
    @Test
    public void shouldBeRedAsStartingPlayer() {
    // Player in turn should be equal to red.
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    // Testing that the player is blue after end turn.
    @Test
    public void shouldBeBlueAfterRed() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    //Testing that the game has only 2 players
    @Test
    public void shouldOnlyHave2Players() {
        endTurns(2);
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    /************ TESTS FOR CITIES ************/
    // Testing that the population of the city is always 1.
    @Test
    public void shouldAlwaysContainPopulationOf_1_InCities() {
        assertThat(redCity.getSize(), is(1));
    }

    // Testing that the city always has an owner.
    @Test
    public void shouldHaveOwnerInCity() {
        assertThat(redCity.getOwner(), is(Player.RED));
    }

    // Testing that there is a red city at 1,1
    @Test
    public void shouldHaveRedCityAt1_1() {
        City redCity = game.getCityAt(GameConstants.Red_City_Pos);
        assertThat(redCity, is(notNullValue()));
        assertThat(redCity.getOwner(), is(Player.RED));

    }

    // Testing that there is a blue city at 4,2
    @Test
    public void shouldHaveBlueCityAt4_1() {
        City blueCity = game.getCityAt(GameConstants.Blue_City_Pos);
        assertThat(blueCity, is(notNullValue()));
        assertThat(blueCity.getOwner(), is(Player.BLUE));
    }

    // Testing that 6 productions is produced after end of each round.
    @Test
    public void shouldProduce6ProductionAtEndOfRound() {
        City redCity = game.getCityAt(GameConstants.Red_City_Pos);
        assertThat(redCity.getTreasury(), is(0));
        endTurns(2);
        assertThat(redCity.getTreasury(), is(6));
    }

    //Testing that cities can change work force focus
    @Test
    public void shouldBeAbleToChangeWorkForceFocusInCity(){
        CityImpl redCity = (CityImpl) game.getCityAt(GameConstants.Red_City_Pos);
        redCity.changeWorkForceFocus(GameConstants.foodFocus);
        assertThat(redCity.getWorkforceFocus(), is(GameConstants.foodFocus));
    }

    /************ TESTS FOR TIME ************/
    // Testing that game starts in year 4000 BC
    @Test
    public void shouldAlwaysStartIn4000BC() {
        assertThat(game.getAge(), is(-4000));
    }

    // Testing that the time is incremented with 100 years after every round.
    @Test
    public void shouldIncrementTimeWith100AfterEveryRound() {
    // Changing to Player blue.
        game.endOfTurn();
    // Checking that the time stays at year 4000 BC.
        assertThat(game.getAge(), is(-4000));
    // Changing to player Red.
        game.endOfTurn();
    // Checking if the time has been incremented.
        assertThat(game.getAge(), is(-3900));
    }

    /************ TESTS FOR WINNER ************/
    // Testing that red is winning in year 3000.
    @Test
    public void shouldRedWinInYear3000BC() {
        assertThat(game.getWinner(), is(nullValue()));
    // Incrementing world age to year 3000.
        endTurns(20);
    // Checking that the winner is RED.
        assertThat(game.getWinner(), is(Player.RED));
    }

    /************ TESTS FOR WORLD ************/
    // Testing that most of the world is plains.
    @Test
    public void shouldMostlyBePlainsInTheWorld() {
        Position p = new Position(0, 0);
    // Iteration 1.
        assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
    // Iteration 2.
        p = new Position(7, 12);
        assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
    // Reliability.
        p = new Position(GameConstants.WORLDSIZE - 1, GameConstants.WORLDSIZE - 1);
        assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
    }

    // Testing that there are ocean at 1,0.
    @Test
    public void shouldBeOceanAtPosition1_0() {
    // Asserting ocean tile is of type Ocean
        assertThat(game.getTileAt(GameConstants.Ocean_Tile_Position).getTypeString(), is(GameConstants.OCEANS));
    }

    // Testing that there are mountain at 2,2
    @Test
    public void shouldBeMountainAtPosition2_2() {
    // Asserting mountain tile is of type Mountain
        assertThat(game.getTileAt(GameConstants.Mountain_Tile_Position).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    // Testing that there are hills at 0,1
    @Test
    public void shouldBeHillsAtPosition0_1() {
    // Asserting hill tile is of type Hill
        assertThat(game.getTileAt(GameConstants.Hill_Tile_Position).getTypeString(), is(GameConstants.HILLS));
    }

    /************ TESTS FOR UNITS ************/
    // Testing that Red starts with settler on 4,3.
    @Test
    public void shouldBeSettlerOn4_3OwnedByRed() {
    // Creating the position.
        Position p = GameConstants.RedSettler_Start_Position;
    // Checking that the Settler is at position.
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.SETTLER));
    // Checking that the Settler is owned by Red.
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
    }

    // Testing that Red starts with archer on 2,0
    @Test
    public void shouldBeArcherOn2_0OwnedByRed() {
    // Creating the position.
        Position p = GameConstants.RedArcher_Start_Position;
    // Checking that the archer is at position.
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.ARCHER));
    // Checking that the archer is owned by Red.
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
    }

    // Testing that Blue starts with legion 3,2.
    @Test
    public void shouldBeLegionOn3_2OwnedByBlue() {
    // Creating the position.
        Position p = GameConstants.BlueLegion_Start_Position;
    // Checking that the legion is at the position.
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.LEGION));
    // Checking that the legion is owned by Blue.
        assertThat(game.getUnitAt(p).getOwner(), is(Player.BLUE));
    }

    //Testing archer attack is 2
    @Test
    public void archersShouldHave2AtkStrength() {
        assertThat(game.getUnitAt(GameConstants.RedArcher_Start_Position).getAttackingStrength(), is(2));
    }

    //Testing settler attack is 0
    @Test
    public void settlersShouldHave0AtkStrength() {
        assertThat(game.getUnitAt(GameConstants.RedSettler_Start_Position).getAttackingStrength(), is(0));
    }

    //Testing legion defense is 2
    @Test
    public void legionShouldHave2DefStrength() {
        assertThat(game.getUnitAt(GameConstants.BlueLegion_Start_Position).getDefensiveStrength(), is(2));
    }

    //Testing settler defense is 3
    @Test
    public void settlersShouldHave3DefStrength() {
        assertThat(game.getUnitAt(GameConstants.RedSettler_Start_Position).getDefensiveStrength(), is(3));
    }

    // Testing if the unit is moving.
    @Test
    public void shouldMoveFromOnePositionToAnother() {
    // Creating positions.
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = new Position(4, 4);
    // Moving unit
        game.moveUnit(from, to);
    // Testing if position has changed.
        assertThat(game.getUnitAt(to).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(from), is(nullValue()));
    }

    // Testing that the move count is reduced by one after move.
    @Test
    public void shouldReduceMoveCountBy1AfterMove() {
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = new Position(4, 4);
        assertThat(game.getUnitAt(from).getMoveCount(), is(1));
        game.moveUnit(from, to);
        assertThat(game.getUnitAt(to).getMoveCount(), is(0));
    }

    // Testing that the move count is reset after turn / round.
    @Test
    public void shouldResetUnitMoveCounterAfterTurns() {
    // Creating position and unit
        Position p = GameConstants.RedSettler_Start_Position;
        UnitImpl testUnit = (UnitImpl) game.getUnitAt(p);
    //Reducing unit move count
        testUnit.reduceMoveCount();
    //Ending round
        endTurns(2);
    // Checking that move count is 1
        assertThat(testUnit.getMoveCount(), is(1));
    }

    //Testing that Red cannot move blue units
    @Test
    public void redShouldNOTMoveBlueUnits() {
    //Moving blue legion on (3,2)
        Position from = GameConstants.BlueLegion_Start_Position;
        Position to = new Position(3, 3);
        assertThat(game.moveUnit(from, to), is(false));
        assertThat(game.getUnitAt(to), is(nullValue()));
    }

    //Testing that red can move red units
    @Test
    public void redCanMoveRedUnits() {
    //Moving red settler on (4,3)
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = new Position(4, 4);
        assertThat(game.moveUnit(from, to), is(true));
        assertThat(game.getUnitAt(to), is(notNullValue()));
    }

    //Testing units cannot move over Ocean
    @Test
    public void shouldNotBeAbleToMoveUnitsOnOcean() {
        {
            //Moving red archer on (2,0)
            Position from = GameConstants.RedArcher_Start_Position;
            //To ocean tile at (1,0)
            Position to = GameConstants.Ocean_Tile_Position;
            assertThat(game.moveUnit(from, to), is(false));
            assertThat(game.getUnitAt(to), is(nullValue()));
        }
    }

    //Testing units cannot move over Mountain
    @Test
    public void shouldNotBeAbleToMoveUnitsOnMountain() {
        {
            //Switch turn to blue
            game.endOfTurn();
            assertThat(game.getPlayerInTurn(), is(Player.BLUE));
            //Moving blue legion on (3,2)
            Position from = GameConstants.BlueLegion_Start_Position;
            //To mountain tile at (2,2)
            Position to = GameConstants.Mountain_Tile_Position;
            assertThat(game.moveUnit(from, to), is(false));
            assertThat(game.getUnitAt(to), is(nullValue()));
        }
    }

    //Testing units cannot move if they have no available moves
    @Test
    public void shouldNotBeAbleToMoveIfNoMovesAreLeft(){
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = new Position(4, 4);
        UnitImpl testUnit = (UnitImpl) game.getUnitAt(GameConstants.RedSettler_Start_Position);
        testUnit.reduceMoveCount();
        assertThat(game.moveUnit(from, to), is(false));
    }

    /************ TESTS FOR PRODUCING UNITS ************/
    // Testing that you are able to choose production in city.
    @Test
    public void shouldBeAbleToChooseProductionInRedCity() {
        City redCity = game.getCityAt(GameConstants.Red_City_Pos);
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBeAbleToChooseProductionInBlueCity() {
        City redCity = game.getCityAt(GameConstants.Blue_City_Pos);
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.ARCHER);
        assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
    }

    // Testing that units are being spawned after enough production.
    @Test
    public void shouldSpawnUnitAtEnoughProduction() {
        City redCity = game.getCityAt(GameConstants.Red_City_Pos);
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);

        endTurns(4);
        assertThat(game.getUnitAt(GameConstants.Red_City_Pos).getTypeString(), is(GameConstants.ARCHER));
        assertThat(redCity.getTreasury(), is(2));
    }

    @Test
    public void shouldSpawnUnitAtEnoughProductionBlue() {
        City blueCity = game.getCityAt(GameConstants.Blue_City_Pos);
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.ARCHER);

        endTurns(4);
        assertThat(game.getUnitAt(GameConstants.Blue_City_Pos).getTypeString(), is(GameConstants.ARCHER));
        assertThat(blueCity.getTreasury(), is(2));
    }

    /************ TESTS FOR PRODUCTION PLACEMENT ************/
    // Testing first unit placement if red city.
    @Test
    public void shouldBe0_1AtFirstTile() {
        Iterator<Position> neighbours = Utility.get8neighborhoodIterator(GameConstants.Red_City_Pos);
        Position p = neighbours.next();
        assertThat(p, is(new Position(0, 1)));
    }

    // Testing second unit placement if red city
    @Test
    public void shouldBe0_2AtSecondTile() {
        Iterator<Position> neighbours = Utility.get8neighborhoodIterator(GameConstants.Red_City_Pos);
        Position p = neighbours.next();
        p = neighbours.next();
        assertThat(p, is(new Position(0, 2)));
    }

    // Testing the rest of the possible tiles.
    @Test
    public void shouldBeOkAtRemainingTiles() {
        Iterator<Position> neighbours = Utility.get8neighborhoodIterator(GameConstants.Red_City_Pos);
        Position p = neighbours.next();
        p = neighbours.next();

        p = neighbours.next();
        assertThat(p, is(new Position(1, 2)));

        p = neighbours.next();
        assertThat(p, is(new Position(2, 2)));

        p = neighbours.next();
        assertThat(p, is(new Position(2, 1)));

        p = neighbours.next();
        assertThat(p, is(new Position(2, 0)));

        p = neighbours.next();
        assertThat(p, is(new Position(1, 0)));

        p = neighbours.next();
        assertThat(p, is(new Position(0, 0)));

        assertThat(neighbours.hasNext(), is(false));
    }

    //Test that corner tiles have 3 neighbours
    //Testing top left corner
    @Test
    public void shouldOnlyHave3ElementsAround00Position() {
        Iterator<Position> neighbours = Utility.get8neighborhoodIterator(new Position(0, 0));
        Position p = neighbours.next();
        assertThat(p, is(new Position(0, 1)));

        p = neighbours.next();
        assertThat(p, is(new Position(1, 1)));

        p = neighbours.next();
        assertThat(p, is(new Position(1, 0)));

        assertThat(neighbours.hasNext(), is(false));
    }

    //Testing bottom right corner
    @Test
    public void shouldOnlyHave3ElementsAround15_15Position() {
        Iterator<Position> neighbours = Utility.get8neighborhoodIterator(
                new Position(GameConstants.WORLDSIZE - 1, GameConstants.WORLDSIZE - 1));

        Position p = neighbours.next();
        assertThat(p, is(new Position(14, 15)));

        p = neighbours.next();
        assertThat(p, is(new Position(15, 14)));

        p = neighbours.next();
        assertThat(p, is(new Position(14, 14)));

        assertThat(neighbours.hasNext(), is(false));
    }

    //Testing that Iterator is allowed
    @Test
    public void shouldSupportIterable() {
        List<Position> list = new ArrayList<>();
        for (Position p : Utility.get8neighborhoodOf(GameConstants.Red_City_Pos)) {
            list.add(p);
        }
        assertThat(list, hasItems(new Position(0, 1),
                new Position(2, 2)));
        assertThat(list, not(hasItem(new Position(0, 3))));
        assertThat(list.size(), is(8));
    }

    // Testing that unit can't be placed on city tile if unit is already present.
    @Test
    public void shouldNotBeAbleToPlaceUniTInCityIfUnitIsPresent() {
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.ARCHER);
        endTurns(4);
        assertThat(game.getUnitAt(GameConstants.Blue_City_Pos).getTypeString(), is(GameConstants.ARCHER));
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.LEGION);
        endTurns(6);
        assertThat(game.getUnitAt(GameConstants.Blue_City_Pos).getTypeString(), is(GameConstants.ARCHER));
    }

    //Testing that if a unit is already in a city, the following unit will be placed north of that city
    @Test
    public void shouldPlaceUnitNorthOfCityIfItIsOccupied() {
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        endTurns(8);
        assertThat(game.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    // Testing that unit will be placed right of north if the possible tiles
    // before aren't accessible.
    @Test
    public void shouldPlaceUnitOnSecondPossibleTileIfCityAndNorthIsOccupied() {
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        endTurns(12);
        assertThat(game.getUnitAt(new Position(0, 2)).getTypeString(), is(GameConstants.ARCHER));
    }

    // Testing the remainder of the positions.
    @Test
    public void shouldPlaceOnRestOfAdjacentTilesIfNecessary() {
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        endTurns(36);
        assertThat(game.getUnitAt(new Position(0, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(1, 2)).getTypeString(), is(GameConstants.ARCHER));
    }

    // Testing that adjacent tiles will only contain one unit at a time.
    @Test
    public void shouldNotContainMoreThanOneUnitOnAdjacentTiles() {
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        endTurns(36);
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.LEGION);
        endTurns(48);
        assertThat(game.getUnitAt(new Position(0, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(0, 0)).getTypeString(), is(not(GameConstants.LEGION)));
    }

    //Testing that produce unit does not produce on immovable terrain (Oceans, Mountains)
    @Test
    public void shouldNotPlaceUnitsOnImmovableTerrain (){
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        endTurns(20);
        assertThat(game.getUnitAt(GameConstants.Mountain_Tile_Position), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.Ocean_Tile_Position), is(nullValue()));
    }

    /************ TESTS FOR ATTACKS ************/
    // Testing that the attacking player destroys the defending players units.
    // Red attacking blue.
    @Test
    public void shouldWinOverBlueIfRedIsAttacking() {
    // Creating positions.
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = GameConstants.BlueLegion_Start_Position;
    // Move Reds unit.
        game.moveUnit(from, to);
    // Checking that the unit is now owned by red.
        assertThat(game.getUnitAt(to).getOwner(), is(Player.RED));
    }

    // Blue attacking red.
    @Test
    public void shouldWinOverRedIfBlueIsAttacking() {
    // Creating positions
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = GameConstants.BlueLegion_Start_Position;
    // Changing player.
        game.endOfTurn();
    // Move blues unit
        game.moveUnit(from, to);
    // Checking that the unit is now owned by red.
        assertThat(game.getUnitAt(to).getOwner(), is(Player.BLUE));
    }

    //Testing
    @Test
    public void shouldBeAbleToChangeCityOwner() {
        CityImpl redCity = (CityImpl) game.getCityAt(GameConstants.Red_City_Pos);
        redCity.changeOwner(Player.BLUE);
        assertThat(game.getCityAt(GameConstants.Red_City_Pos).getOwner(), is(Player.BLUE));
    }


    //Testing that cities change owners if an opposing unit moves on to its tile
    @Test
    public void shouldChangeCityOwnerIfOpponentUnitEnters() {
        Position from = GameConstants.BlueLegion_Start_Position;
        Position to = GameConstants.Red_City_Pos;
        // Changing player.
        game.endOfTurn();
        // Move blues unit
        game.moveUnit(from, to);
        //Checking that city changed owner
        assertThat(game.getCityAt(GameConstants.Red_City_Pos).getOwner(), is(Player.BLUE));
    }


    /************ ASSISTING METHODS FOR TESTS ************/
    //Helps increment turns by x amount
    private void endTurns(int x) {
        for (int i = 0; i < x; i++) {
            game.endOfTurn();
        }
    }

}
