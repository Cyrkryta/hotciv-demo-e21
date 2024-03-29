package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import hotciv.variants.factories.AlphaCivFactory;
import hotciv.variants.factories.GammaCivFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestGameObserver {
    private Game game;
    private GameObserverSpy gameObserver;

    @BeforeEach
    public void setUp() {
        game = new GameImpl(new GammaCivFactory());
        gameObserver = new GameObserverSpy();
        game.addObserver(gameObserver);
    }

    @Test
    public void gameObserverRegisterEndTurn() {
        game.endOfTurn();
        assertThat(gameObserver.lastMethodCalled(), is("nextPlayer is: BLUE the year is: -4000"));
        game.endOfTurn();
        assertThat(gameObserver.lastMethodCalled(), is("nextPlayer is: RED the year is: -3900"));
    }

    @Test
    public void gameObserverRegistersMoveUnit() {
        game.moveUnit(GameConstants.RedArcher_Start_Position, new Position(2,1));
        assertThat(gameObserver.previousMethodCalled(), is("World changed at (2,0)"));
        assertThat(gameObserver.lastMethodCalled(), is("World changed at (2,1)"));
    }

    @Test
    public void gameObserverRegistersTileFocusChange() {
        game.setTileFocus(new Position(1,1));
        assertThat(gameObserver.lastMethodCalled(), is("tile focus changed at (1,1)"));
    }

    @Test
    public void gameObserverRegistersSettlerUnitActions() {
        game.performUnitActionAt(GameConstants.RedSettler_Start_Position);
        assertThat(gameObserver.lastMethodCalled(), is("World changed at (4,3)"));
    }

    @Test
    public void gameObserverRegistersUnitProductionChange() {
        game.changeProductionInCityAt(GameConstants.Red_City_Pos, GameConstants.ARCHER);
        assertThat(gameObserver.lastMethodCalled(), is("City production changed to: archer"));
    }

    @Test
    public void gameObserverRegistersFocusProductionChange() {
        game.changeWorkForceFocusInCityAt(GameConstants.Red_City_Pos, GameConstants.productionFocus);
        assertThat(gameObserver.lastMethodCalled(), is("City focus changed to: hammer"));
    }
}

class GameObserverSpy implements GameObserver {
    String previousMethodCalled;
    String lastMethodCalled;
    @Override
    public void worldChangedAt(Position pos) {
        previousMethodCalled = lastMethodCalled;
        lastMethodCalled = "World changed at "+"("+pos.getRow()+","+pos.getColumn()+")";
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        lastMethodCalled = "nextPlayer is: "+nextPlayer+" the year is: "+age;
    }

    @Override
    public void tileFocusChangedAt(Position pos) {
        lastMethodCalled = "tile focus changed at "+"("+pos.getRow()+","+pos.getColumn()+")";
    }

    @Override
    public void cityWorkFocusChanges(String focus) {
        lastMethodCalled = "City focus changed to: "+focus;
    }

    @Override
    public void cityProductionChanged(String unitType) {
        lastMethodCalled = "City production changed to: "+unitType;
    }

    public String previousMethodCalled() {
        return previousMethodCalled;
    }

    public String lastMethodCalled() {
        return lastMethodCalled;
    }
}

