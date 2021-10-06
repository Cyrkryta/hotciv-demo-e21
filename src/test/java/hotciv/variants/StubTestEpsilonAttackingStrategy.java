package hotciv.variants;

import hotciv.framework.*;

import hotciv.variants.Epsilon.DieRollStrategy;
import hotciv.variants.Epsilon.EpsilonAttackingStrategy;
import hotciv.variants.Epsilon.LoadedRollStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class StubTestEpsilonAttackingStrategy {
    private Game gameStub;
    private final LoadedRollStrategy loadedRollStrategy = new LoadedRollStrategy();
    private final AttackingStrategy epsilonAttackingStrategy = new EpsilonAttackingStrategy(loadedRollStrategy);

    /************  FIXTURE FOR EPSILON ATTACK STRATEGY TESTING ************/
    @BeforeEach
    public void setUp() {
        gameStub = new GameStubForAttackStrategyTesting(epsilonAttackingStrategy, loadedRollStrategy);
    }

    /*********  STUB TESTS FOR EPSILON ATTACK STRATEGY **********/
    //SEE TEST STUBS BELOW FOR EXPLANATIONS OF POSITIONS!
    //Testing that archers with hill terrain advantage defeat archers without advantage
    @Test
    public void archerOnHillShouldBeatArcherOnPlains(){
        Position from = new Position(0,0);
        Position to = new Position( 4,0);
        loadedRollStrategy.setDie1And2(1,1);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(true));
    }

    //Testing that archers with forest terrain advantage defeat archers without advantage
    @Test
    public void archerInForestShouldBeatArcherOnPlains(){
        Position from = new Position(1,0);
        Position to = new Position( 3,0);
        loadedRollStrategy.setDie1And2(1,1);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(true));
    }

    //Testing that archers with support advantage defeat archers without advantage
    @Test
    public void archerWithSupportAdvantageShouldDefeatLoneArcher(){
        Position from = new Position(3,3);
        Position to = new Position( 4,4);
        loadedRollStrategy.setDie1And2(1,1);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(true));
    }

    //Testing that legions defeat archers with no advantage
    @Test
    public void legionShouldDefeatArcher(){
        Position from = new Position(2,1);
        Position to = new Position( 3,0);
        loadedRollStrategy.setDie1And2(1,1);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(true));
    }

    //Archer in city defeats archer outside city
    @Test
    public void archerInCityDefeatsArcherOutsideCity(){
        Position from = new Position(1,1);
        Position to = new Position( 4,0);
        loadedRollStrategy.setDie1And2(1,1);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(true));
    }

    //Testing that legions making low rolls lose to archers making high rolls
    @Test
    public void legionLosesToLuckyArcher(){
        Position from = new Position(2,1);
        Position to = new Position( 3,0);
        loadedRollStrategy.setDie1And2(1,5);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(false));
    }

    //Archer with advantage loses to lucky archer
    @Test
    public void archerWithAdvantageLosesToLuckyArcher(){
        Position from = new Position(0,0);
        Position to = new Position( 4,0);
        loadedRollStrategy.setDie1And2(1,5);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, gameStub), is(false));
    }
}

/********  TEST STUBS FOR EPSILON ATTACK STRATEGY **********/
/** A test stub for testing battle calculations in Epsilon Attack Strategy
 * The layout is as seen below RA = Red Archer BA = Blue Archer BLE = Blue Legion:
 * +---+-----------+--------+-----+-----+-----+
 * |   |     0     |   1    |  2  |  3  |  4  |
 * +---+-----------+--------+-----+-----+-----+
 * | 0 | RA FOREST |        |     |     |     |
 * | 1 | BA HILLS  |   RA   |     |     |     |
 * | 2 |           |   BLE  |     |  RA |     |
 * | 3 |    RA     |        |  RA |  RA |     |
 * | 4 |    BA     |        |     |     |  BA |
 * +---+-----------+--------+-----+-----+-----+
 */
class GameStubForAttackStrategyTesting implements Game {
    private AttackingStrategy attackingStrategy;
    private DieRollStrategy loadedRollStrategy;

    public GameStubForAttackStrategyTesting(AttackingStrategy attackingStrategy, LoadedRollStrategy rollStrategy){
        this.attackingStrategy = attackingStrategy;
        this.loadedRollStrategy = rollStrategy;
    }
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
        if ( p.getRow() == 0 && p.getColumn() == 0 ||
                p.getRow() == 1 && p.getColumn() == 1 ||
                p.getRow() == 3 && p.getColumn() == 0 ||
                p.getRow() == 2 && p.getColumn() == 3 ||
                p.getRow() == 3 && p.getColumn() == 2 ||
                p.getRow() == 3 && p.getColumn() == 3 ) {
            return new StubUnit(GameConstants.ARCHER, Player.RED);
        }
        if ( p.getRow() == 1 && p.getColumn() == 0 ||
                p.getRow() == 4 && p.getColumn() == 0 ||
                p.getRow() == 4 && p.getColumn() == 4 ) {
            return new StubUnit(GameConstants.ARCHER, Player.BLUE);
        }
        if ( p.getRow() == 2 && p.getColumn() == 1) {
            return new StubUnit(GameConstants.LEGION, Player.BLUE);
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

    public int getDefensiveStrength() {
        switch (type) {
            case GameConstants.ARCHER:
                return GameConstants.ARCHER_DEF;
            case GameConstants.LEGION:
                return GameConstants.LEGION_DEF;
            case GameConstants.SETTLER:
                return GameConstants.SETTLER_DEF;
        }
        return 0;
    }

    public int getAttackingStrength() {
        switch (type) {
            case GameConstants.ARCHER:
                return GameConstants.ARCHER_ATK;
            case GameConstants.LEGION:
                return GameConstants.LEGION_ATK;
            case GameConstants.SETTLER:
                return GameConstants.SETTLER_ATK;
        }
        return 0;
    }

    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 0; }
}