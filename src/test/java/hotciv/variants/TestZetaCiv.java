package hotciv.variants;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.variants.epsilonStrategies.EpsilonAttackingStrategy;
import hotciv.variants.epsilonStrategies.LoadedRollStrategy;
import hotciv.variants.factories.ZetaCivFactory;
import org.junit.jupiter.api.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import hotciv.standard.GameImpl;
import org.junit.jupiter.api.io.TempDir;

public class TestZetaCiv {
    private GameImpl game;
    private final LoadedRollStrategy loadedRollStrategy = new LoadedRollStrategy();

    /************  FIXTURE FOR BETACIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
    }

    // Test that checks if player wins with all cities before 20 rounds.
    @Test
    public void shouldWinWithAllCitiesBeforeRound20() {
        CityImpl blueCity = (CityImpl) game.getCityAt(GameConstants.Blue_City_Pos);
        assertThat(game.getWinner(), is(nullValue()));
        endOfRound(10);
        blueCity.changeOwner(Player.RED);
        assertThat(game.getWinner(), is(Player.RED));
    }

    // Test that checks if player doesn't win if all cities after 20 rounds.
    @Test
    public void shouldNotWinWithAllCitiesAfterRound20() {
        CityImpl blueCity = (CityImpl) game.getCityAt(GameConstants.Blue_City_Pos);
        assertThat(game.getWinner(), is(nullValue()));
        endOfRound(23);
        blueCity.changeOwner(Player.RED);
        assertThat(game.getWinner(), is(nullValue()));
    }

    // Test that checks that player doesn't win after three victories before round 20.
    @Test
    public void shouldNotWinAfterThreeAttackingVictoriesBeforeRound20() {
        //Creating 2 new legions from blue city
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.LEGION);
        endOfRound(8);
        //Make extremely lucky archer that always wins
        loadedRollStrategy.setDie1And2(6,1);
        //Use Archer to attack all blues legions
        Position start = GameConstants.RedArcher_Start_Position;
        game.moveUnit(start, new Position(3,1));
        endOfRound(1);
        game.moveUnit(new Position(3,1), new Position(3,2));
        endOfRound(1);
        game.moveUnit(new Position(3,2), new Position(4,2));
        //Red has won 3 battles but should not win.
        assertThat(game.getWinner(), is(nullValue()));
    }

    // Test that checks that player wins after three victories after 20 rounds.
    @Test
    public void shouldWinAfterThreeAttackingVictoriesAfterRound20() {
        game.endOfTurn();
        //Creating 2 new legions from blue city
        game.changeProductionInCityAt(GameConstants.Blue_City_Pos, GameConstants.LEGION);
        endOfRound(22);
        //Make extremely lucky archer that always wins
        loadedRollStrategy.setDie1And2(6,1);
        assertThat(game.getUnitAt(new Position(3,1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(4,2)).getTypeString(), is(GameConstants.LEGION));

        game.endOfTurn();
        //Use Archer to attack all blues legions
        Position start = GameConstants.RedArcher_Start_Position;
        game.moveUnit(start, new Position(3,1));
        endOfRound(1);
        game.moveUnit(new Position(3,1), new Position(3,2));
        endOfRound(1);
        game.moveUnit(new Position(3,2), new Position(4,2));
        assertThat(game.getUnitAt(new Position(4,2)).getTypeString(), is(GameConstants.ARCHER));
        //Red has won 3 battles but should not win.
        assertThat(game.getWinner(), is(Player.RED));
    }

    /********** HELPER METHODS ************/
    public void endOfRound(int rounds) {
        for (int i = 0; i < rounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }
}



