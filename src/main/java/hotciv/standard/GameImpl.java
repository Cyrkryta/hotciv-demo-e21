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
  //Sets game age to start age
  private int currentAge = GameConstants.Start_Age;
  // Starting point for game winner.
  private Player winner = null;
  // Implements the aging strategy for the game
  private AgeStrategy ageStrategy;
  // Implements the world strategy for the game
  private WorldLayoutStrategy worldLayoutStrategy;
  // Implements the winning strategy for the game.
  private WinningStrategy winningStrategy;
  // Implements the world layout strategy for the game.
  private UnitActionStrategy unitActionStrategy;


  public GameImpl(AgeStrategy ageStrategy, WinningStrategy winningStrategy, UnitActionStrategy unitActionStrategy, WorldLayoutStrategy worldLayoutStrategy) {
    this.ageStrategy = ageStrategy;
    this.winningStrategy = winningStrategy;
    this.unitActionStrategy = unitActionStrategy;
    this.worldLayoutStrategy = worldLayoutStrategy;
    createUnitMap();
    citySetup();
    this.worldMap = worldLayoutStrategy.createWorld();
  }

  private void createUnitMap() {
    unitMap.put(GameConstants.RedSettler_Start_Position, new UnitImpl(GameConstants.SETTLER, Player.RED));
    unitMap.put(GameConstants.RedArcher_Start_Position, new UnitImpl(GameConstants.ARCHER, Player.RED));
    unitMap.put(GameConstants.BlueLegion_Start_Position, new UnitImpl(GameConstants.LEGION, Player.BLUE));
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
    //getWinner();
  }

  private void resetUnitsMoveCount() {
    for (Unit currUnit : unitMap.values()) {
      UnitImpl unitImpl = (UnitImpl) currUnit;
      unitImpl.resetMoveCount();
    }
  }

  public Player getWinner() {
    ArrayList<City> listOfCities = new ArrayList<>(cityMap.values());
    winner = winningStrategy.calculateWinner(getAge(), listOfCities);
    return winner;
  }

  public int getAge() {
    return currentAge;
  }

  public boolean moveUnit(Position from, Position to) {
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    if(getNeighbourList(from).contains(to)){
      if(unit.getMoveCount() > 0){
        if(unit.getOwner() == playerInTurn) {
          if (getUnitAt(to) == null) {
            // Handling illegal moves.
            if (getTileAt(to).getTypeString().equals(GameConstants.HILLS) || getTileAt(to).getTypeString().equals(GameConstants.PLAINS)){
              if(getCityAt(to) != null && getCityAt(to).getOwner() != unit.getOwner()){
                CityImpl attackedCity = (CityImpl) getCityAt(to);
                attackedCity.changeOwner(playerInTurn);
              }
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
    CityImpl chosenCity = (CityImpl) getCityAt(p);
    if(balance.equals(GameConstants.foodFocus) || balance.equals(GameConstants.productionFocus))
    {chosenCity.changeWorkForceFocus(balance);}
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
    unitActionStrategy.performAction(p, this);
  }

  private void endOfRound() {
    //Increments game age according to age strategy
    currentAge = ageStrategy.calculateAge(currentAge);
    System.out.print(getAge());
    //Iterates through all cities on the map and allocates them 6 production at end of round
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      CityImpl currCity = (CityImpl) entry.getValue();
      currCity.addTreasury(6);
    }
    produceUnits();
  }

  private void produceUnits() {
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      Position cityPos = entry.getKey();
      Player cityOwner = entry.getValue().getOwner();
      CityImpl city = (CityImpl) entry.getValue();
      int cityTreasury = entry.getValue().getTreasury();
      String cityProduction = entry.getValue().getProduction();
      Position placementPos = cityPos;

      if(getUnitAt(cityPos) != null) {
        Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(cityPos);
        for (; listOfNeighbours.hasNext(); ) {
          Position position = listOfNeighbours.next();
          if(getTileAt(position).getTypeString().equals(GameConstants.HILLS) || getTileAt(position).getTypeString().equals(GameConstants.PLAINS)){
            if (getUnitAt(position) == null) {
              placementPos = position;
              break;
            }
          }

        }
      }

      if (Objects.equals(cityProduction, GameConstants.ARCHER) && cityTreasury >= GameConstants.ARCHER_COST) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.ARCHER, cityOwner));
        city.addTreasury(-GameConstants.ARCHER_COST);
      }
      if (Objects.equals(cityProduction, GameConstants.LEGION) && cityTreasury >= GameConstants.LEGION_COST) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.LEGION, cityOwner));
        city.addTreasury(-GameConstants.LEGION_COST);
      }
      if (Objects.equals(cityProduction, GameConstants.SETTLER) && cityTreasury >= GameConstants.SETTLER_COST) {
        unitMap.put(placementPos, new UnitImpl(GameConstants.SETTLER, cityOwner));
        city.addTreasury(-GameConstants.SETTLER_COST);
      }
    }

  }
  private List<Position> getNeighbourList(Position pos){
    List<Position> neighbourList = new ArrayList<>();
    Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(pos);
    while (listOfNeighbours.hasNext()) {
      neighbourList.add(listOfNeighbours.next());
  }
    return neighbourList;
  }

  // Function for city creation in GammaCiv.
  public void gammaCivCreateCity(Position p) {
    cityMap.put(p, new CityImpl(getUnitAt(p).getOwner()));
    unitMap.remove(p);
  }
}
