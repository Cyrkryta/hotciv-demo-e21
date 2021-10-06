package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.Epsilon.DieRollStrategy;
import hotciv.variants.Epsilon.EpsilonAttackingStrategy;
import hotciv.variants.Epsilon.EpsilonCivWinningStrategy;
import hotciv.variants.Epsilon.LoadedRollStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private Game game;
    private final AgeStrategy linearAgeStrategy = new LinearAgeStrategy();
    private final WinningStrategy alphaCivWinningStrategy = new EpsilonCivWinningStrategy();
    private final UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();
    private final WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
    private final DieRollStrategy loadedRollStrategy = new LoadedRollStrategy();
    private final AttackingStrategy alphaAttackingStrategy = new EpsilonAttackingStrategy(loadedRollStrategy);

    /************  FIXTURE FOR ALPHACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, alphaCivWinningStrategy, alphaUnitActionStrategy, alphaWorldLayoutStrategy, alphaAttackingStrategy);
    }

    @Test
    public void settlersShouldLoseToAnArcher(){

    }
}
/*********  TEST STUBS **********/
class StubTile implements Tile {
    private String type;
    public StubTile(String type, int r, int c) { this.type = type; }
    public String getTypeString() { return type; }
}

class StubUnit implements Unit {
    private String type; private Player owner;
    public StubUnit(String type, Player owner) {
        this.type = type; this.owner = owner;
    }
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 0; }
    public int getDefensiveStrength() { return 0; }
    public int getAttackingStrength() { return 0; }
}

/** A test stub for testing the battle calculation methods in
 * Utility. The terrains are
 * 0,0 - forest;
 * 1,0 - hill;
 * 0,1 - plain;
 * 1,1 - city.
 *
 * Red has units on 2,3; 3,2; 3,3; blue one on 4,4
 */
class GameStubForBattleTesting implements Game {
    public Tile getTileAt(Position p) {
        if ( p.getRow() == 0 && p.getColumn() == 0 ) {
            return new StubTile(GameConstants.FOREST, 0, 0);
        }
        if ( p.getRow() == 1 && p.getColumn() == 0 ) {
            return new StubTile(GameConstants.HILLS, 1, 0);
        }
        return new StubTile(GameConstants.PLAINS, 0, 1);
    }
    public Unit getUnitAt(Position p) {
        if ( p.getRow() == 2 && p.getColumn() == 3 ||
                p.getRow() == 3 && p.getColumn() == 2 ||
                p.getRow() == 3 && p.getColumn() == 3 ) {
            return new StubUnit(GameConstants.ARCHER, Player.RED);
        }
        if ( p.getRow() == 4 && p.getColumn() == 4 ) {
            return new StubUnit(GameConstants.ARCHER, Player.BLUE);
        }
        return null;
    }
    public City getCityAt(Position p) {
        if ( p.getRow() == 1 && p.getColumn() == 1 ) {
            return new City() {
                public Player getOwner() { return Player.RED; }
                public int getSize() { return 1; }
                public int getTreasury() {
                    return 0;
                }
                public String getProduction() {
                    return null;
                }
                public String getWorkforceFocus() {
                    return null;
                }
            };
        }
        return null;
    }

    // the rest is unused test stub methods...
    public void changeProductionInCityAt(Position p, String unitType) {}
    public void changeWorkForceFocusInCityAt(Position p, String balance) {}
    public void endOfTurn() {}
    public Player getPlayerInTurn() {return null;}
    public Player getWinner() {return null;}
    public int getAge() { return 0; }
    public boolean moveUnit(Position from, Position to) {return false;}
    public void performUnitActionAt( Position p ) {}
}

