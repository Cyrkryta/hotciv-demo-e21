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
  HashMap<Position, Tile> worldMap;
  // Creating Hashmap for the units.
  HashMap<Position, Unit> unitMap;
  // Creating map for the cities.
  Map<Position, City> cityMap;

  // Defining the players in turn.
  private Player playerInTurn = Player.RED;
  //Sets game age to start age
  private int currentAge = GameConstants.Start_Age;
  // Starting point for game winner.
  private Player winner = null;
  // Implements the aging strategy for the game
  private final AgeStrategy ageStrategy;
  // Implements the world strategy for the game
  private final WorldLayoutStrategy worldLayoutStrategy;
  // Implements the winning strategy for the game.
  private final WinningStrategy winningStrategy;
  // Implements the world layout strategy for the game.
  private final UnitActionStrategy unitActionStrategy;

  private final AttackingStrategy attackingStrategy;
  private int battlesWon;


  public GameImpl(GameFactory gameFactory) {
    this.ageStrategy = gameFactory.createAgeStrategy();
    this.winningStrategy = gameFactory.createWinningStrategy();
    this.unitActionStrategy = gameFactory.createUnitActionStrategy();
    this.worldLayoutStrategy = gameFactory.createWorldLayoutStrategy();
    this.attackingStrategy = gameFactory.createAttackingStrategy();

    this.unitMap = worldLayoutStrategy.placeUnits();
    this.cityMap = worldLayoutStrategy.placeCities();
    this.worldMap = worldLayoutStrategy.createWorld();

  }

  /************ Accessor Methods ************/
  //region
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

  public ArrayList<City> getCityList(){
    return new ArrayList<>(cityMap.values());
  }

  public Player getWinner() {
  winner = winningStrategy.calculateWinner(this);
  return winner;
  }

  public int getAge() {
    return currentAge;
  }
  //endregion

  /************ Turn and Round methods ************/
  //region
  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      endOfRound();
      resetUnitsMoveCount();
    }
  }

  private void endOfRound() {
    //Increments game age according to age strategy
    currentAge = ageStrategy.calculateAge(currentAge);
    //Iterates through all cities on the map and allocates them 6 production at end of round
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      CityImpl currCity = (CityImpl) entry.getValue();
      currCity.addTreasury(6);
    }
    produceUnits();
    winningStrategy.incrementRoundsPlayed();
  }

  private void resetUnitsMoveCount() {
    for (Unit currUnit : unitMap.values()) {
      UnitImpl unitImpl = (UnitImpl) currUnit;
      unitImpl.resetMoveCount();
    }
  }
  //endregion

  /************ moveUnit and related methods ************/
  //region
  public boolean moveUnit(Position from, Position to) {
    if(!moveIsPossible(from,to)) return false;

    boolean movingIntoEnemyCity = getCityAt(to) != null && getCityAt(to).getOwner() != getUnitAt(from).getOwner();
    if(movingIntoEnemyCity) {
      attackCity(to);
    }

    UnitImpl unitType = (UnitImpl) unitMap.remove(from);
    unitType.reduceMoveCount();
    unitMap.put(to, unitType);
    return true;
  }

  private boolean moveIsPossible(Position from, Position to){
    //Checks tile conditions
    if (getUnitAt(from) == null) return false;
    if (from == to) return false;
    if (!movesToNeighbourTile(from, to)) return false;

    //Checks Unit conditions
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    if (unit.getOwner() != playerInTurn) return false;
    if (!isMovableTerrain(to)) return false;
    if (!(unit.getMoveCount() > 0)) return false;

    boolean ownUnitAtTo = getUnitAt(to) != null && getUnitAt(to).getOwner() == playerInTurn;
    if (ownUnitAtTo) return false;

    //Handles combat if moving onto tile occupied by enemy unit
    boolean attackingEnemyUnit = getUnitAt(to) != null && getUnitAt(to).getOwner() != getUnitAt(from).getOwner();
    if (attackingEnemyUnit){
      return handleAttack(from, to);
    }

    return true;
  }

  //Handle attacks made while moving units, returns boolean for result of combat
  private boolean handleAttack(Position from, Position to){
    boolean attackIsSuccessful = attackingStrategy.calculateAttack(from, to, this);
    if(!attackIsSuccessful){
      unitMap.remove(from);
      return false;
    }
    getWinner();
    winningStrategy.incrementBattlesWonBy(getUnitAt(from).getOwner());

    return true;
  }

  //Tests that the tile at the given position movable
  private boolean isMovableTerrain(Position position){
    String tileType = getTileAt(position).getTypeString();
    return GameConstants.movableTerrain.contains(tileType);
  }

  private void attackCity(Position cityPos){
    CityImpl attackedCity = (CityImpl) getCityAt(cityPos);
    attackedCity.changeOwner(playerInTurn);
  }

  private boolean movesToNeighbourTile(Position from, Position to){
    List<Position> neighbourList = new ArrayList<>();
    Iterator<Position> neighbourIterator = Utility.get8neighborhoodIterator(from);
    while (neighbourIterator.hasNext()) {
      neighbourList.add(neighbourIterator.next());
    }
    return neighbourList.contains(to);
  }
  //endregion

  /************ City Methods ************/
  //region
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

  // Function for city creation in GammaCiv.
  public void createCity(Position p) {
    cityMap.put(p, new CityImpl(getUnitAt(p).getOwner()));
    unitMap.remove(p);
  }
  //endregion

  /************ Unit Production Methods ************/
  //region
  private void produceUnits() {
    for (Map.Entry<Position, City> entry : cityMap.entrySet()) {
      Position cityPos = entry.getKey();
      CityImpl city = (CityImpl) entry.getValue();
      String unitType = getCityAt(cityPos).getProduction();

      executeUnitProduction(unitType, city, cityPos);
    }
  }

  private void executeUnitProduction(String unitType, City city, Position cityPos) {
    CityImpl currentCity = (CityImpl) city;
    int cityTreasury = city.getTreasury();
    Position placementPos = getPlacementPosition(cityPos);
    if(unitType != null && getUnitCost(unitType) <= cityTreasury) {
      unitMap.put(placementPos, new UnitImpl(unitType, city.getOwner()));
      currentCity.addTreasury(-getUnitCost(unitType));
    }
  }

  private int getUnitCost(String unitType) {
    int cost = 0;
    switch(unitType) {
      case GameConstants.ARCHER:
        cost = GameConstants.ARCHER_COST;
        break;
      case GameConstants.LEGION:
        cost = GameConstants.LEGION_COST;
        break;
      case GameConstants.SETTLER:
        cost = GameConstants.SETTLER_COST;
        break;
    }
    return cost;
  }

  private Position getPlacementPosition(Position cityPosition) {
    Position placementPosition = cityPosition;
    if(getUnitAt(cityPosition) != null) {
      Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(cityPosition);
      while (listOfNeighbours.hasNext()) {
        Position position = listOfNeighbours.next();
        if(isMovableTerrain(position)){
          if (getUnitAt(position) == null) {
            placementPosition = position;
            break;
          }
        }
      }
    }
    return placementPosition;
  }
  //endregion

  public void performUnitActionAt(Position p) {
    unitActionStrategy.performAction(p, this);
  }

  public int getBattlesWon() {
    return battlesWon;
  }
}
