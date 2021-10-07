package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.epsilonStrategies.EpsilonAttackingStrategy;
import hotciv.variants.epsilonStrategies.EpsilonCivWinningStrategy;
import hotciv.variants.epsilonStrategies.LoadedRollStrategy;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.factories.EpsilonCivTestFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private GameImpl game;
    //These field variables are sent to the EpsilonTestFactory constructor
    //allowing calls to their class method from this test class
    private final LoadedRollStrategy loadedRollStrategy = new LoadedRollStrategy();
    private final WinningStrategy epsilonCivWinningStrategy = new EpsilonCivWinningStrategy();
    //Attacking strategy allows for unit testing of AttackingStrategy
    private final AttackingStrategy epsilonAttackingStrategy = new EpsilonAttackingStrategy(loadedRollStrategy);
    /************  FIXTURE FOR EPSILON CIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new EpsilonCivTestFactory(loadedRollStrategy, epsilonCivWinningStrategy));
    }


    /************  UNIT TESTS FOR ATTACK STRATEGY ************/
    //Testing that a Settler Type unit loses to a Legion Type unit
    @Test
    public void settlersShouldLoseToALegion(){
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = GameConstants.BlueLegion_Start_Position;
        loadedRollStrategy.setDie1And2(2,2);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, game), is(false));
    }

    //Testing that an Archer Unit loses to a Settler Unit if equal
    @Test
    public void archerShouldLoseToSettler(){
        Position from = GameConstants.RedArcher_Start_Position;
        Position to = GameConstants.RedSettler_Start_Position;
        loadedRollStrategy.setDie1And2(2,2);
        assertThat(epsilonAttackingStrategy.calculateAttack(from, to, game), is(false));
    }

    /************  GAME TESTS FOR ATTACK STRATEGY ************/
    @Test
    public void shouldRemoveUnitAfterUnsuccessfulAttack(){
        Position from = GameConstants.RedSettler_Start_Position;
        Position to = GameConstants.BlueLegion_Start_Position;
        loadedRollStrategy.setDie1And2(1,1);
        game.moveUnit(from, to);
        //Asserting that attacking unit is removed, and unit at from is still present
        assertThat(game.getUnitAt(from), is(nullValue()));
        assertThat(game.getUnitAt(to).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void legionShouldLoseIfUnlucky(){
        Position from = GameConstants.BlueLegion_Start_Position;
        Position to = GameConstants.RedSettler_Start_Position;
        game.endOfTurn();
        loadedRollStrategy.setDie1And2(1,5);
        game.moveUnit(from, to);
        assertThat(game.getUnitAt(to).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void redShouldWinIfTheyWin3Battles(){
        //Creating 2 new legions from blue city
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.LEGION);
        endTurns(10);
        //Make extremely lucky archer that always wins
        loadedRollStrategy.setDie1And2(6,1);
        //Use Archer to attack all blues legions
        Position start = GameConstants.RedArcher_Start_Position;
        game.moveUnit(start, new Position(3,1));
        endTurns(2);
        game.moveUnit(new Position(3,1), new Position(3,2));
        endTurns(2);
        game.moveUnit(new Position(3,2), new Position(4,1));
        //Red has won 3 battles and should win
        assertThat(game.getWinner(), is(Player.RED));
    }

    /************ UNIT TESTS FOR WINNING STRATEGY ************/
    @Test
    public void unitTestRedShouldWinIfTheyHaveWon3Battles(){
        incrementPlayerVictoriesByX(Player.RED, 3);
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void unitTestBlueShouldWinIfTheyHaveWon3Battles(){
        incrementPlayerVictoriesByX(Player.BLUE, 3);
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void unitTestNoWinnersIfNoPLayerHasWon3Battles(){
        epsilonCivWinningStrategy.incrementBattlesWonBy(Player.BLUE);
        epsilonCivWinningStrategy.incrementBattlesWonBy(Player.RED);
        assertThat(game.getWinner(), is(nullValue()));
    }


    /************ ASSISTING METHODS FOR TESTS ************/
    //region
    //Helps increment turns by x amount
    private void endTurns(int x) {
        for (int i = 0; i < x; i++) {
            game.endOfTurn();
        }
    }
    private void incrementPlayerVictoriesByX(Player player, int battlesWon){
        for (int i = 0; i < battlesWon; i++) {
            epsilonCivWinningStrategy.incrementBattlesWonBy(player);
        }
    }
    //endregion
}
