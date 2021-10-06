package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.Epsilon.EpsilonAttackingStrategy;
import hotciv.variants.Epsilon.EpsilonCivWinningStrategy;
import hotciv.variants.Epsilon.LoadedRollStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private GameImpl game;
    private final AgeStrategy linearAgeStrategy = new LinearAgeStrategy();
    private final WinningStrategy epsilonCivWinningStrategy = new EpsilonCivWinningStrategy();
    private final UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();
    private final WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
    private final LoadedRollStrategy loadedRollStrategy = new LoadedRollStrategy();
    private final AttackingStrategy epsilonAttackingStrategy = new EpsilonAttackingStrategy(loadedRollStrategy);

    /************  FIXTURE FOR EPSILON CIV TESTING ************/
    @BeforeEach
    public void setUp() {
        game = new GameImpl(linearAgeStrategy, epsilonCivWinningStrategy, alphaUnitActionStrategy, alphaWorldLayoutStrategy, epsilonAttackingStrategy);
    }

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


}