package hotciv.stub;

import hotciv.Utility.Utility;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;
import minidraw.framework.RubberBandSelectionStrategy;

import java.util.*;

/** FakeObject implementation for Game. Base your
 * development of Tools and CivDrawing on this test double,
 * and gradually add EVIDENT TEST = simple code
 * to it, to support your development of all features
 * necessary for a complete CivDrawing and your suite
 * of Tools.
 *
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

public class FakeObjectGame implements Game {

  private Map<Position, StubUnit> unitMap;
  private Map<Position, City> cityMap;

  //Roundcount keeps track of what round the game is in
  private int roundCount = 1;

  public FakeObjectGame() {
    defineWorld();
    // Put some units into play
    unitMap = new HashMap<>();
    unitMap.put(new Position(2,0), new StubUnit( GameConstants.ARCHER, Player.RED ));
    unitMap.put(new Position(2,1), new StubUnit( GameConstants.ARCHER, Player.RED ));
    unitMap.put(new Position(3,2), new StubUnit( GameConstants.LEGION, Player.BLUE ));
    unitMap.put(new Position(4,2), new StubUnit( GameConstants.SETTLER, Player.RED ));
    unitMap.put(new Position(6,3), new StubUnit( ThetaConstants.SANDWORM, Player.RED ));
    inTurn = Player.RED;

    cityMap = new HashMap<>();
    cityMap.put((new Position(3,2)), new StubCity(Player.BLUE));
    cityMap.put((new Position(8,8)), new StubCity(Player.RED));
  }

  /** define the world.
   */
  protected void defineWorld() {
    world = new HashMap<Position,Tile>();
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        Position p = new Position(r,c);
        world.put( p, new StubTile(GameConstants.PLAINS));
      }
    }
    // Create a little area around the theta unit of special terrain
    world.put(new Position(5,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,2), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,3), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,5), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,3), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,5), new StubTile(ThetaConstants.DESERT));
  }

  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

  public boolean moveUnit(Position from, Position to) {
    // Using print statements to aid in debugging and development
    System.out.println("-- FakeObjectGame / moveUnit called: " + from + "->" + to);
    if (getUnitAt(from) == null) return false;
    if (from == to) return false;

    //Checks Unit conditions
    StubUnit unit = (StubUnit) getUnitAt(from);
    if (unit.getOwner() != getPlayerInTurn()) return false;
    if (!(unit.getMoveCount() > 0)) return false;
    if (!movesToNeighbourTile(from , to)) return false;

    boolean ownUnitAtTo = getUnitAt(to) != null && getUnitAt(to).getOwner() == getPlayerInTurn();
    if (ownUnitAtTo) return false;

    System.out.println("-- moveUnit found unit at: " + from);
    // Remember to inform game observer on any change on the tiles
    unitMap.remove(from);
    gameObserver.worldChangedAt(from);
    unit.reduceMoveCount();

    unitMap.put(to, unit);
    gameObserver.worldChangedAt(to);
    return true;
  }

  public boolean movesToNeighbourTile(Position from, Position to) {
    List<Position> neighbourList = new ArrayList<>();
    Iterator<Position> neighbourIterator = Utility.get8neighborhoodIterator(from);
    while (neighbourIterator.hasNext()) {
      neighbourList.add(neighbourIterator.next());
    }
    return neighbourList.contains(to);
  }

  // === Turn handling ===
  private Player inTurn;
  public Player getPlayerInTurn() { return inTurn; }

  // === Observer handling ===
  protected GameObserver gameObserver;
  // observer list is fake code, only having a single
  // one, suffices for development.
  public void addObserver(GameObserver observer) {
    gameObserver = observer;
  }

  public void worldChangeUpdateObserver(Position pos) {
      gameObserver.worldChangedAt(pos);
  }

  // A simple implementation to draw the map of DeltaCiv
  protected Map<Position,Tile> world; 
  public Tile getTileAt( Position p ) { return world.get(p); }

  public City getCityAt( Position p ) { return cityMap.get(p); }

  public Player getWinner() { return null; }

  public int getAge() { return -4000+(roundCount-1)*100; }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {
    StubCity city = (StubCity) cityMap.get(p);
    if(city != null) {
      city.setWorkForceFocus(balance);
    }
    gameObserver.worldChangedAt(p);
  }
  public void changeProductionInCityAt( Position p, String unitType ) {
    System.out.println("-- FakeObjectGame / changeProductionInCityAt called. Position: " + p +
            ", Production unit: " + unitType);
    StubCity city = (StubCity) cityMap.get(p);
    if(city != null) {
      city.setProduction(unitType);
    }
    gameObserver.worldChangedAt(p);
  }
  public void performUnitActionAt( Position p ) {
    if (getUnitAt(p).getTypeString().equals(GameConstants.SETTLER)) {
      createCity(p);
      unitMap.remove(p);
      worldChangeUpdateObserver(p);
    }
  }

  public void createCity(Position p) {
    cityMap.put(p, new CityImpl(getUnitAt(p).getOwner()));
  }

  public void endOfTurn() {
    System.out.println( "-- FakeObjectGame / endOfTurn called." );
    if (inTurn == Player.RED) {
      inTurn = Player.BLUE;
    } else {
      inTurn = Player.RED;
      endOfRound();
    }
    // no age increments implemented...
    gameObserver.turnEnds(inTurn, getAge());
  }

  private void endOfRound() {
    roundCount++;
    resetUnitsMoveCount();
  }

  private void resetUnitsMoveCount() {
    for (StubUnit currUnit : unitMap.values()) {
        currUnit.resetMoveCount();
        System.out.println(currUnit.getTypeString()+"has "+currUnit.getMoveCount()+" moves left");
    }
  }

  public void produceUnits(Position cityPosition) {
    System.out.println("-- FakeObjectGame / produceUnits called.");
    unitMap.put(cityPosition, new StubUnit(GameConstants.ARCHER, getCityAt(cityPosition).getOwner()));
    gameObserver.worldChangedAt(cityPosition);
  }

  public void setTileFocus(Position position) {
    System.out.println("-- FakeObjectGame / setTileFocus called.");
    gameObserver.tileFocusChangedAt(position);
  }
}

class StubUnit implements  Unit {
  private String type;
  private Player owner;
  private int moveCount = 1;
  public StubUnit(String type, Player owner) {
    this.type = type;
    this.owner = owner;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() { return moveCount; }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }
  public void reduceMoveCount() {
    moveCount --;
  }

  public void resetMoveCount() {moveCount = 1;}
}

class StubCity implements City {
  private Player owner;
  private String cityProduction;
  private String workForceFocus = GameConstants.foodFocus;
  private int treasury;
  public StubCity (Player owner){
    this.owner = owner;
  }

  @Override
  public Player getOwner() {return owner;}

  @Override
  public int getSize() {
    return 1;
  }

  @Override
  public int getTreasury() {return treasury;}

  @Override
  public String getProduction() {
    return cityProduction;
  }

  @Override
  public String getWorkforceFocus() {
    return workForceFocus;
  }

  public void setProduction(String production) {
     cityProduction = production;
  }

  public void setWorkForceFocus(String focus){
    workForceFocus = focus;
  }

  public void addTreasury(int amount) {
    treasury += amount;
  }
}
