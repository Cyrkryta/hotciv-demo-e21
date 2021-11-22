package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;
import java.util.*;

/**
 * Skeleton implementation of HotCiv.
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

public class GameImpl implements Game {
    // Creating HashMap for the world.
    HashMap<Position, Tile> worldMap;
    // Creating Hashmap for the units.
    public HashMap<Position, Unit> unitMap;
    // Creating map for the cities.
    Map<Position, City> cityMap;

    // Defining the players in turn.
    public Player playerInTurn = Player.RED;
    //Sets game age to start age
    private int currentAge = GameConstants.Start_Age;
    // Starting point for game winner.
    private Player winner = null;
    //Position currently in focus
    Position positionInFocus;

    // Implements the aging strategy for the game
    private final AgeStrategy ageStrategy;
    // Implements the world strategy for the game
    private final WorldLayoutStrategy worldLayoutStrategy;
    // Implements the winning strategy for the game.
    private final WinningStrategy winningStrategy;
    // Implements the world layout strategy for the game.
    private final UnitActionStrategy unitActionStrategy;
    // Implements the move unit strategy for the gamer.
    private final ValidMoveStrategy validMoveStrategy;

    private final AttackingStrategy attackingStrategy;
    //Implements game observing functionality
    protected ArrayList<GameObserver> gameObservers = new ArrayList<>();

    public GameImpl(GameFactory gameFactory) {
        this.ageStrategy = gameFactory.createAgeStrategy();
        this.winningStrategy = gameFactory.createWinningStrategy();
        this.unitActionStrategy = gameFactory.createUnitActionStrategy();
        this.worldLayoutStrategy = gameFactory.createWorldLayoutStrategy();
        this.attackingStrategy = gameFactory.createAttackingStrategy();
        this.validMoveStrategy = gameFactory.createValidMoveStrategy();
        this.unitMap = worldLayoutStrategy.placeUnits();
        this.cityMap = worldLayoutStrategy.placeCities();
        this.worldMap = worldLayoutStrategy.createWorld();

    }

    /************ Observer Methods ************/
    public void addObserver(GameObserver observer){
        gameObservers.add(observer);
    }

    public void setTileFocus(Position position) {
        positionInFocus = position;
        for (GameObserver gameObserver: gameObservers) {
            gameObserver.tileFocusChangedAt(position);
        }
    }

    public void worldChangeUpdateObserver(Position pos) {
        for (GameObserver gameObserver: gameObservers) {
            gameObserver.worldChangedAt(pos);
        }
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

    public ArrayList<City> getCityList() {
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
        for (GameObserver gameObserver: gameObservers) {
            gameObserver.turnEnds(playerInTurn, getAge());
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
        if (!validMoveStrategy.moveIsPossible(from, to, this)) return false;
        if (!validMoveStrategy.isMovableTerrain(from, to, this)) return false;
        //if (!validMoveStrategy.movesToNeighbourTile(from, to)) return false;

        boolean movingIntoEnemyCity = getCityAt(to) != null && getCityAt(to).getOwner() != getUnitAt(from).getOwner();
        if (movingIntoEnemyCity) {
            attackCity(to);
        }

        UnitImpl unitType = (UnitImpl) unitMap.remove(from);
        unitType.reduceMoveCount();
        unitMap.put(to, unitType);
        worldChangeUpdateObserver(from);
        worldChangeUpdateObserver(to);
        return true;
    }

    //Handle attacks made while moving units, returns boolean for result of combat
    public boolean handleAttack(Position from, Position to) {
        boolean attackIsSuccessful = attackingStrategy.calculateAttack(from, to, this);
        if (!attackIsSuccessful) {
            unitMap.remove(from);
            return false;
        }
        getWinner();
        winningStrategy.incrementBattlesWonBy(getUnitAt(from).getOwner());
        return true;
    }

    public void attackCity(Position cityPos) {
        CityImpl attackedCity = (CityImpl) getCityAt(cityPos);
        attackedCity.changeOwner(playerInTurn);
    }

    /************ City Methods ************/
    //region
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        if(getCityAt(p).getOwner() != getPlayerInTurn()) return;

        CityImpl chosenCity = (CityImpl) getCityAt(p);
        if (chosenCity == null) return;
        if (balance.equals(GameConstants.foodFocus) || balance.equals(GameConstants.productionFocus)) {
            chosenCity.changeWorkForceFocus(balance);
            for (GameObserver gameObserver: gameObservers) {
                gameObserver.cityWorkFocusChanges(balance);
            }
        }

    }

    public void changeProductionInCityAt(Position p, String unitType) {
        if (getCityAt(p).getOwner() != getPlayerInTurn()) return;
        CityImpl chosenCity = (CityImpl) getCityAt(p);
        if (chosenCity != null) {
            if (Objects.equals(unitType, GameConstants.ARCHER) || Objects.equals(unitType, GameConstants.SETTLER) ||
                    Objects.equals(unitType, GameConstants.LEGION) || Objects.equals(unitType, GameConstants.SANDWORM)) {
                chosenCity.changeProd(unitType);
                for (GameObserver gameObserver : gameObservers) {
                    gameObserver.cityProductionChanged(unitType);
                }
            }
        }
    }


    // Function for city creation in GammaCiv.
    public void createCity(Position p) {
        cityMap.put(p, new CityImpl(getUnitAt(p).getOwner()));
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
        int cityTreasury = currentCity.getTreasury();
        Position placementPos = getPlacementPosition(cityPos);
        if (unitType != null && getUnitCost(unitType) <= cityTreasury) {
            unitMap.put(placementPos, new UnitImpl(unitType, city.getOwner()));
            currentCity.addTreasury(-getUnitCost(unitType));
        }
    }

    private int getUnitCost(String unitType) {
        return GameConstants.unitConstants.get(unitType)[GameConstants.COST_INDEX];
    }

    private Position getPlacementPosition(Position cityPosition) {
        Position placementPosition = cityPosition;
        if (getUnitAt(cityPosition) != null) {
            Iterator<Position> listOfNeighbours = Utility.get8neighborhoodIterator(cityPosition);
            while (listOfNeighbours.hasNext()) {
                Position position = listOfNeighbours.next();
                if (validMoveStrategy.isMovableTerrain(cityPosition, position, this)) {
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
        //worldChangeUpdateObserver(p);
    }
}
