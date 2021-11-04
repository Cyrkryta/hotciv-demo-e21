package hotciv.decorators;

import hotciv.Utility.Utility;
import hotciv.framework.*;
import hotciv.standard.*;

import hotciv.variants.decorators.GameLogDecorator;
import hotciv.variants.factories.AlphaCivFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestGameLogDecorator {
    private Game game;
    private GameImpl innerGame = new GameImpl(new AlphaCivFactory());

    @BeforeEach
    public void setUp() {
        game = new GameLogDecorator(innerGame);
    }

    @Test
    public void moveUnitPrintsCorrectMove() {
        System.out.println("moveUnitPrintsCorrectMove:");
        //Should print: RED moves Archer from (2,0) to (3,1)
        game.moveUnit(GameConstants.RedArcher_Start_Position, new Position(3,1));
    }

    @Test
    public void changeCityProductionPrintsCorrectProduction() {
        System.out.println("changeCityProductionPrintsCorrectProduction:");
        //Should print: RED changes production in city at (1,1) to Legion.
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.LEGION);
    }

    @Test
    public void endTurnPrintsCorrectly() {
        System.out.println("endTurnPrintsCorrectly");
        //Should print: RED ends turn
        game.endOfTurn();
        //Should print: BLUE ends turn
        game.endOfTurn();
    }

    @Test
    public void changeWorkForceFocusPrintsCorrectly() {
        game.endOfTurn();
        System.out.println("changeWorkForceFocusPrintsCorrectly");
        //Should print: BLUE changes work force focus in city at (4,1) to food focus.
        game.changeWorkForceFocusInCityAt(GameConstants.Blue_City_Pos, GameConstants.foodFocus);
    }

    @Test
    public void performUnitActionPrintsCorrectly() {
        System.out.println("performUnitActionPrintsCorrectly");
        //Should print: RED performs archer action at (2,0)
        game.performUnitActionAt(GameConstants.RedArcher_Start_Position);
    }

    /************ ASSISTING METHODS FOR TESTS ************/
    //region
    //Helps increment turns by x amount
    private void endTurns(int x) {
        for (int i = 0; i < x; i++) {
            game.endOfTurn();
        }
    }
    //endregion



}
