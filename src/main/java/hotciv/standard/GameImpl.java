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
  static Position Red_City_Pos = new Position(1,1);


  //Constructor for GameImpl
  public GameImpl(){
    citySetup();
  }

  // Creating map for the cities.
  Map<Position, City> cityMap = new HashMap<>();

  private void citySetup(){
    City redCity = new CityImpl(Player.RED);
    cityMap.put(Red_City_Pos,redCity);
  }
  public Tile getTileAt(Position p ) {
    return null;
  }

  public Unit getUnitAt( Position p ) { return null; }



  public City getCityAt( Position p ) { return cityMap.get(p); }

  // Defining the players in turn.
  private Player playerInTurn = Player.RED;
  public Player getPlayerInTurn() { return playerInTurn;}

  public void endOfTurn() {
    if(playerInTurn == Player.RED){
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      endOfRound();
    }
  }

  public Player getWinner() {
    if(currAge == 3000) {
      return Player.RED;
    }
    return null;
  }

  //Game always starts in 4000 BC
  private int currAge = 4000;
  public int getAge() { return currAge; }

  private void endOfRound() {
    currAge -= 100;
    System.out.print(getAge());
  }

  public boolean moveUnit( Position from, Position to ) {
    return false;
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {}

  public void performUnitActionAt( Position p ) {}
}
