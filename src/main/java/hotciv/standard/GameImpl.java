package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import java.util.*;

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
  HashMap<Position, Tile> worldMap = new HashMap<>();
  // Creating Hashmap for the units.
  HashMap<Position, Unit> unitMap = new HashMap<>();
  // Creating map for the cities.
  Map<Position, City> cityMap = new HashMap<>();

  // Defining the players in turn.
  private Player playerInTurn = Player.RED;
  //Game always starts in 4000 BC
  private int currAge = -4000;

  public GameImpl() {
    createWorld();
    createUnitMap();
    citySetup();
  }

  private void createUnitMap() {
    unitMap.put(GameConstants.Settler_Start_Position, new UnitImpl(GameConstants.SETTLER, Player.RED));
    unitMap.put(GameConstants.Archer_Start_Position, new UnitImpl(GameConstants.ARCHER, Player.RED));
    unitMap.put(GameConstants.Legion_Start_Position, new UnitImpl(GameConstants.LEGION, Player.BLUE));
  }

  // Method for handling the creation of the tiles.
  private void createWorld() {
    for (int i = 0; i <= GameConstants.WORLDSIZE - 1; i++) {
      for (int j = 0; j <= GameConstants.WORLDSIZE - 1; j++) {
        worldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
      }
    }
    // Creation of tile with ocean.
    worldMap.put(GameConstants.Ocean_Tile_Position, new TileImpl(GameConstants.OCEANS));
    // Creation of tile with mountain.
    worldMap.put(GameConstants.Mountain_Tile_Position, new TileImpl(GameConstants.MOUNTAINS));
    // Creation of tile with hills.
    worldMap.put(GameConstants.Hill_Tile_Position, new TileImpl(GameConstants.HILLS));
  }

  private void citySetup() {
    City redCity = new CityImpl(Player.RED);
    City blueCity = new CityImpl(Player.BLUE);
    cityMap.put(GameConstants.Red_City_Pos, redCity);
    cityMap.put(GameConstants.Blue_City_Pos, blueCity);
  }

  public Tile getTileAt(Position p) {
    return worldMap.get(p);
  }

  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

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
    for (Unit currUnit : unitMap.values()) {
      UnitImpl unitImpl = (UnitImpl) currUnit;
      unitImpl.resetMoveCount();
    }
  }

  public Player getWinner() {
    if (currAge == -3000) {
      return Player.RED;
    }
    return null;
  }

  public int getAge() {
    return currAge;
  }

  public boolean moveUnit(Position from, Position to) {
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    if(unit.getOwner() == playerInTurn) {
      if (getUnitAt(to) == null) {
          // Handling illegal moves.
          if (getTileAt(to).getTypeString().equals(GameConstants.HILLS) || getTileAt(to).getTypeString().equals(GameConstants.PLAINS)){
            UnitImpl unitType = (UnitImpl) unitMap.remove(from);
            unitType.reduceMoveCount();
            unitMap.put(to, unitType);
            return true;
          }
      } else if (getUnitAt(to).getOwner() != playerInTurn) {
        handleAttack(from, to);
        return true;
      }
    }
    return false;
  }

  private void handleAttack(Position from, Position to) {
    UnitImpl attackingUnitType = (UnitImpl) unitMap.remove(from);
    attackingUnitType.reduceMoveCount();
    unitMap.remove(to);
    unitMap.put(to, attackingUnitType);
  }

  public void changeWorkForceFocusInCityAt(Position p, String balance) {
  }

  public void changeProductionInCityAt(Position p, String unitType) {
    CityImpl chosenCity = (CityImpl) getCityAt(p);
    if (chosenCity != null) {
      if (Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER) || Objects.equals(unitType, GameConstants.LEGION)) {
        chosenCity.changeProd(unitType);
      }
    }
  }

  public void performUnitActionAt(Position p) {
  }

  private void endOfRound() {
    currAge += 100;
    System.out.print(getAge());

    CityImpl redCity = (CityImpl) cityMap.get(GameConstants.Red_City_Pos);
    redCity.addTreasury(6);

    produceUnits();
  }

  private void produceUnits() {
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      Position cityPos = entry.getKey();
      Player owner = entry.getValue().getOwner();
      CityImpl city = (CityImpl) entry.getValue();
      int cityTreasury = entry.getValue().getTreasury();
      String cityProduction = entry.getValue().getProduction();
      Position placementPos = cityPos;

      if(getUnitAt(cityPos) != null) {
        Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(cityPos);
        for (Iterator<Position> it = listOfNeighbours; it.hasNext(); ) {
          Position pos = it.next();
          if (getUnitAt(pos) == null) {
            placementPos = pos;
            break;
          }
        }
      }

      if (cityProduction == (GameConstants.ARCHER) && cityTreasury >= 10) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.ARCHER, owner));
        city.addTreasury(-10);
      }
      if (cityProduction == (GameConstants.LEGION) && cityTreasury >= 15) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.LEGION, owner));
        city.addTreasury(-15);
      }
      if (cityProduction == (GameConstants.SETTLER) && cityTreasury >= 30) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.SETTLER, owner));
        city.addTreasury(-30);
      }
    }
  }
}
