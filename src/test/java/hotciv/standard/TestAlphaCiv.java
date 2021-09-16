package hotciv.standard;

import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

   Updated Aug 2020 for JUnit 5 includes
   Updated Oct 2015 for using Hamcrest matchers

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

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
  public void shouldBeBlueAfterRed (){
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  /************ TESTS FOR CITIES ************/
  // Testing that the population of the city is always 1.
  @Test
  public void shouldAlwaysContainPopulationOf_1_InCities() {
    assertThat(redCity.getSize(), is(1));
  }

  // Testing that the city always has a owner.
  @Test
  public void shouldHaveOwnerInCity() {
    assertThat(redCity.getOwner(), is (Player.RED));
  }

  // Testing that there is a red city at 1,1
  @Test
  public void shouldHaveRedCityAt1_1(){
    City redCity = game.getCityAt(GameConstants.Red_City_Pos);
    assertThat(redCity, is(notNullValue()));
    assertThat(redCity.getOwner(), is(Player.RED));

  }

  // Testing that there is a blue city at 4,2
  @Test
  public void shouldHaveBlueCityAt4_1(){
    City blueCity = game.getCityAt(GameConstants.Blue_City_Pos);
    assertThat(blueCity, is(notNullValue()));
    assertThat(blueCity.getOwner(), is(Player.BLUE));
  }

  // Testing that 6 productions is produced after end of each round.
  @Test
  public void shouldProduce6ProductionAtEndOfRound(){
    City redCity = game.getCityAt(GameConstants.Red_City_Pos);
    assertThat(redCity.getTreasury(), is(0));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(redCity.getTreasury(), is(6));
  }

  // Testing that you are able to choose production in city.
  @Test
  public void shouldBeAbleToChooseProductionInCity(){
    City redCity = game.getCityAt(GameConstants.Red_City_Pos);
    game.changeProductionInCityAt(GameConstants.Red_City_Pos,GameConstants.ARCHER);
    assertThat(redCity.getProduction(),is(GameConstants.ARCHER));
  }

  /************ TESTS FOR TIME ************/
  // Testing that game starts in year 4000 BC
  @Test
  public void shouldAlwaysStartIn4000BC(){
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
    assertThat(game.getAge(),is(-3900));
  }

  /************ TESTS FOR WINNER ************/
  // Testing that red is winning in year 3000.
  @Test
  public void shouldRedWinInYear3000BC() {
    assertThat(game.getWinner(), is(nullValue()));
    // Incrementing world age to year 3000.
    while(game.getAge() < -3000) {
      game.endOfTurn();
    }
    // Checking that the winner is RED.
    assertThat(game.getWinner(), is(Player.RED));
  }

  /************ TESTS FOR WORLD ************/
  // Testing that most of the world is plains.
  @Test
  public void shouldMostlyBePlainsInTheWorld() {
    Position p = new Position(0,0);
    // Iteration 1.
    assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
    // Iteration 2.
    p = new Position(7,12);
    assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
    // Reliability.
    p = new Position(GameConstants.WORLDSIZE-1, GameConstants.WORLDSIZE-1);
    assertThat(GameConstants.PLAINS, is(game.getTileAt(p).getTypeString()));
  }

  // Testing that there are ocean at 1,0.
  @Test
  public void shouldBeOceanAtPosition1_0() {
    // Creating position.
    Position p = new Position(1,0);
    // Assertion.
    assertThat(GameConstants.OCEANS, is(game.getTileAt(p).getTypeString()));
  }

  // Testing that there are mountain at 2,2
  @Test
  public void shouldBeMountainAtPosition2_2() {
    // Creating Position
    Position p = new Position(2,2);
    // Assertion.
    assertThat(GameConstants.MOUNTAINS, is(game.getTileAt(p).getTypeString()));
  }

  // Testing that there are hills at 0,1
  @Test
  public void shouldBeMountainAtPosition0_1() {
    // Creating Position
    Position p = new Position(0,1);
    // Assertion.
    assertThat(GameConstants.HILLS, is(game.getTileAt(p).getTypeString()));
  }

  /************ TESTS FOR UNITS ************/
  // Testing that Red starts with settler on 4,3.
  @Test
  public void shouldBeSettlerOn4_3OwnedByRed() {
    // Creating the position.
    Position p = new Position(4,3);
    // Checking that the Settler is at position.
    assertThat(GameConstants.SETTLER, is(game.getUnitAt(p).getTypeString()));
    // Checking that the Settler is owned by Red.
    assertThat(Player.RED, is(game.getUnitAt(p).getOwner()));
  }

  // Testing that Red starts with archer on 2,0
  @Test
  public void shouldBeArcherOn2_0OwnedByRed() {
    // Creating the position.
    Position p = new Position(2,0);
    // Checking that the archer is at position.
    assertThat(GameConstants.ARCHER, is(game.getUnitAt(p).getTypeString()));
    // Checking that the archer is owned by Red.
    assertThat(Player.RED, is(game.getUnitAt(p).getOwner()));
  }

  // Testing that Blue starts with legion 3,2.
  @Test
  public void shouldBeLegionOn3_2OwnedByBlue() {
    // Creating the position.
    Position p = new Position(3,2);
    // Checking that the legion is at the position.
    assertThat(GameConstants.LEGION, is(game.getUnitAt(p).getTypeString()));
    // Checking that the legion is owned by Blue.
    assertThat(Player.BLUE, is(game.getUnitAt(p).getOwner()));
  }

  // Testing move count at start of turn.
  @Test
  public void shouldRefreshMoveCounterAtStartOfTurn() {
    // Creating position
    Position p = new Position(4,3);
    // Checking ownership if the move count is 1
    assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(p).getMoveCount(), is(1));
  }

  // Testing if the unit is moving.
  @Test
  public void shouldMoveFromOnePositionToAnother() {
    // Creating positions.
    Position from = new Position(4,3);
    Position to = new Position(4,4);
    // Moving unit
    game.moveUnit(from, to);
    // Testing if position has changed.
    assertThat(game.getUnitAt(to).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(from), is(nullValue()));
  }

  // Testing that the move count is reduced by one after move.
  @Test
  public void shouldReduceMoveCountBy1AfterMove(){
    Position from = new Position(4,3);
    Position to = new Position(4,4);
    assertThat(game.getUnitAt(from).getMoveCount(), is(1));
    game.moveUnit(from, to);
    assertThat(game.getUnitAt(to).getMoveCount(), is(0));
  }

  // Testing that the move count is reset after turn / round.
  @Test
  public void shouldResetUnitMoveCounterAfterTurns() {
    // Moving and reducing unit count.
    Position from = new Position(4,3);
    Position to = new Position(4,4);
    game.moveUnit(from, to);
    assertThat(game.getUnitAt(to).getMoveCount(), is(0));
    // Going through a round.
    game.endOfTurn();
    game.endOfTurn();
    // checking that the unit count has been reset.
    assertThat(game.getUnitAt(to).getMoveCount(), is(1));
  }

  //Testing that Red cannot move blue units
  @Test
  public void redShouldNOTMoveBlueUnits() {
    Position from = new Position(3,2);
    Position to = new Position(3,3);
    assertThat(game.moveUnit(from, to), is(false));
    assertThat(game.getUnitAt(to), is(nullValue()));
  }

  //Testing that red can move red units
  @Test
  public void redCanMoveRedUnits(){
    //Moving red settler on (4,3)
    Position from = new Position(4,3);
    Position to = new Position(4,4);
    assertThat(game.moveUnit(from, to), is(true));
    assertThat(game.getUnitAt(to), is(notNullValue()));
  }

  //Testing units cannot move over Ocean
  @Test
  public void shouldNotBeAbleToMoveUnitsOnOcean(){
    {
      //Moving red archer on (2,0)
      Position from = new Position(2,0);
      //To ocean tile at (1,0)
      Position to = new Position(1,0);
      assertThat(game.moveUnit(from, to), is(false));
      assertThat(game.getUnitAt(to), is(nullValue()));
    }
  }

  //Testing units cannot move over Mountain
  @Test
  public void shouldNotBeAbleToMoveUnitsOnMountain(){
    {
      //Switch turn to blue
      game.endOfTurn();
      assertThat(game.getPlayerInTurn(), is(Player.BLUE));
      //Moving blue legion on (3,2)
      Position from = new Position(3,2);
      //To mountain tile at (2,2)
      Position to = new Position(2,2);
      assertThat(game.moveUnit(from, to), is(false));
      assertThat(game.getUnitAt(to), is(nullValue()));
    }
  }

  /************ TESTS FOR PRODUCING UNITS ************/
  // Testing that units are being spawned after enough production.
  @Test
  public void shouldSpawnUnitAtEnoughProduction (){
    City redCity = game.getCityAt(GameConstants.Red_City_Pos);
    game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
    assertThat(redCity.getProduction(),is(GameConstants.ARCHER));
    while(game.getAge() < -3800) game.endOfTurn();
    assertThat(game.getUnitAt(GameConstants.Red_City_Pos).getTypeString(),is(GameConstants.ARCHER));
    assertThat(redCity.getTreasury(),is(2));
  }

  /************ TESTS FOR ATTACKS ************/
  // Testing that the attacking player destroys the defending players units.
  // Red attacking blue.
  @Test
  public void shouldWinOverBlueIfRedIsAttacking() {
    // Creating positions.
    Position from = new Position(4,3);
    Position to = new Position(3,2);
    // Move Reds unit.
    game.moveUnit(from,to);
    // Checking that the unit is now owned by red.
    assertThat(game.getUnitAt(to).getOwner(), is(Player.RED));
  }

  // Blue attacking red.
  @Test
  public void shouldWinOverRedIfBlueIsAttacking() {
    // Creating positions
    Position from = new Position(3,2);
    Position to = new Position(4,3);
    // Changing player.
    game.endOfTurn();
    // Move blues unit
    game.moveUnit(from, to);
    // Checking that the unit is now owned by red.
    assertThat(game.getUnitAt(to).getOwner(), is(Player.BLUE));
  }
}
