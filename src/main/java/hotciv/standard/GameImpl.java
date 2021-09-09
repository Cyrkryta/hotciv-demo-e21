package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

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
  //Static coordinates for players starting cities
  public static Position Blue_City_Pos = new Position(4, 1);
  public static Position Red_City_Pos = new Position(1, 1);
  // Creating HashMap for the world.
  HashMap<Position, Tile> worldMap = new HashMap<>();
  // Creating Hashmap for the units.
  HashMap<Position, Unit> unitMap = new HashMap<>();
  // Defining the players in turn.
  private Player playerInTurn = Player.RED;

  public GameImpl() {
    createWorld();
    createUnitMap();
    citySetup();
  }

  private void createUnitMap() {
    unitMap.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED));
    unitMap.put(new Position(2, 0), new UnitImpl(GameConstants.ARCHER, Player.RED));
    unitMap.put(new Position(3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
  }

  // Method for handling the creation of the tiles.
  private void createWorld() {
    for (int i = 0; i <= GameConstants.WORLDSIZE - 1; i++) {
      for (int j = 0; j <= GameConstants.WORLDSIZE - 1; j++) {
        worldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
      }
    }
    // Creation of tile with ocean.
    worldMap.put(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
    // Creation of tile with mountain.
    worldMap.put(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));
    // Creation of tile with hills.
    worldMap.put(new Position(0, 1), new TileImpl(GameConstants.HILLS));
  }

  private void citySetup() {
    City redCity = new CityImpl(Player.RED);
    City blueCity = new CityImpl(Player.BLUE);
    cityMap.put(Red_City_Pos, redCity);
    cityMap.put(Blue_City_Pos, blueCity);
  }


  public Tile getTileAt(Position p) {
    return worldMap.get(p);
  }

  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

  // Creating map for the cities.
  Map<Position, City> cityMap = new HashMap<>();

  public City getCityAt(Position p) {
    return cityMap.get(p);
  }

  public Player getPlayerInTurn() {
    return playerInTurn;
  }

  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      endOfRound();
      resetUnitsMoveCount();
    }
  }

  private void resetUnitsMoveCount() {
    for (Unit u : unitMap.values()) {
      UnitImpl unitImpl = (UnitImpl) u;
      ((UnitImpl) u).resetMoveCount();
    }
  }

  public Player getWinner() {
    if (currAge == 3000) {
      return Player.RED;
    }
    return null;
  }

  //Game always starts in 4000 BC
  private int currAge = 4000;

  public int getAge() {
    return currAge;
  }

  public boolean moveUnit(Position from, Position to) {
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    if(unit != null && getUnitAt(to) == null) {
      if (unit.getOwner() == playerInTurn) {
        UnitImpl unitType = (UnitImpl) unitMap.remove(from);
        unitType.reduceMoveCount();
        unitMap.put(to, unitType);
        return true;
      }
    }
    return false;
  }

  public void changeWorkForceFocusInCityAt(Position p, String balance) {
  }

  public void changeProductionInCityAt(Position p, String unitType) {
    CityImpl C = (CityImpl) getCityAt(p);
    if (C != null) {
      if (Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER) || Objects.equals(unitType, GameConstants.LEGION)) {
        C.changeProd(unitType);
      }
    }
  }

  public void performUnitActionAt(Position p) {
  }

  private void endOfRound() {
    currAge -= 100;
    System.out.print(getAge());

    CityImpl redCity = (CityImpl) cityMap.get(Red_City_Pos);
    redCity.addTreasury(6);

    produceUnits();
  }

  private void produceUnits() {
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      Position pos = entry.getKey();
      Player owner = entry.getValue().getOwner();
      CityImpl city = (CityImpl) entry.getValue();
      int cityTreasury = entry.getValue().getTreasury();
      String cityProduction = entry.getValue().getProduction();

      if (cityProduction == (GameConstants.ARCHER) && cityTreasury >= 10) {
        unitMap.put(pos, new UnitImpl(GameConstants.ARCHER, owner));
        city.addTreasury(-10);
      }
    }
  }
}
