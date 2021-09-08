package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/** Skeleton implementation of HotCiv.
 
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

public class GameImpl implements Game {
  // Creating HashMap for the world.
  HashMap<Position,Tile> worldMap = new HashMap<>();
  //Retreiving TileImpl.
  TileImpl tileImpl;

  public GameImpl() {
    createWorld();
  }

  // Method for handling the creation of the tiles.
  private void createWorld() {
    for (int i=0; i<=GameConstants.WORLDSIZE-1; i++) {
      for (int j=0; j<=GameConstants.WORLDSIZE-1; j++){
        worldMap.put(new Position(i,j),new TileImpl(GameConstants.PLAINS));
      }
    }
  }

  public Tile getTileAt( Position p ) {
    return this.worldMap.get(p);
  }
  
  public Unit getUnitAt( Position p ) { return null; }
  
  // Creating map for the cities.
  Map<Position, City> cityMap = new HashMap<>();
  public City getCityAt( Position p ) { return cityMap.get(p); }

  // Creating map for the tiles.

  // Defining the players in turn.
  private Player playerInTurn = Player.RED;
  public Player getPlayerInTurn() { return playerInTurn;}

  // Changing player for every move.
  public void endOfTurn() {
    if(playerInTurn == Player.RED){
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      endOfRound();
    }
  }

  // Checking whether or not there is a winner.
  public Player getWinner() {
    if(currAge == 3000) {
      return Player.RED;
    }
    return null;
  }

  //Game always starts in 4000 BC
  private int currAge = 4000;
  public int getAge() { return currAge; }

  public boolean moveUnit( Position from, Position to ) {
    return false;
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {}

  public void performUnitActionAt( Position p ) {}

  private void endOfRound() {
    currAge -= 100;
    System.out.print(getAge());
  }
}
