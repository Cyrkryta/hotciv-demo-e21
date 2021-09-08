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

  /** Fixture for alphaciv testing. */
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

  /************ TESTS FOR TIME ************/
  // Testing that game starts in year 4000 BC
  @Test
  public void shouldAlwaysStartIn4000BC(){
    assertThat(game.getAge(), is(4000));
  }

  // Testing that the time is incremented with 100 years after every round.
  @Test
  public void shouldIncrementTimeWith100AfterEveryRound() {
    // Checking for start at year 4000 BC.
    assertThat(game.getAge(), is(4000));
    // Changing to Player blue.
    game.endOfTurn();
    // Checking that the time stays at year 4000 BC.
    assertThat(game.getAge(), is(4000));
    // Changing to player Red.
    game.endOfTurn();
    // Checking if the time has been incremented.
    assertThat(game.getAge(),is(3900));
  }

  /************ TESTS FOR WINNER ************/
  @Test
  public void shouldRedWinInYear3000BC() {
    // Checking for start at year 4000 BC and no winner.
    assertThat(game.getAge(), is(4000));
    assertThat(game.getWinner(), is(nullValue()));
    // Incrementing world age to year 3000.
    while(game.getAge() > 3000) {
      game.endOfTurn();
    }
    // Checking that the winner is RED.
    assertThat(game.getWinner(), is(Player.RED));
  }

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

}
