package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;

import hotciv.stub.StubGameBrokerClient;
import org.junit.jupiter.api.*;

import java.nio.channels.Pipe;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerClient {
    Game game;

    //TEMPORARY
    NameService storage = new InMemoryNameService();
    Gson gson = new Gson();



    Position from = new Position(3,3);
    Position to = new Position(3,4);
    Position fakeCityPosition = new Position(3,5);

    @BeforeEach
    public void setup() {
        Game servant = new StubGameBrokerClient();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivRootInvoker(new StubGameBrokerClient());

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        game.addObserver(nullObserver);
    }

    @Test
    public void shouldHaveWinner() {
        assertThat(game.getWinner(), is(Player.YELLOW));
    }

    @Test
    public void shouldHaveAge() {
        assertThat(game.getAge(), is(53));
    }

    @Test
    public void shouldHaveMoveUnit() {
        assertThat(game.moveUnit(from, to), is(false));
    }

    @Test
    public void shouldHavePlayerInTurn() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void shouldExecuteEndOfTurnStory() {
        System.out.println("=== Testing simple methods ===");
        System.out.println(" -> Game PlayerInTurn     : " + game.getPlayerInTurn());
        game.endOfTurn();
        System.out.println(" -> Now PlayerInTurn after endOfTurn: " + game.getPlayerInTurn());
    }

    @Test
    public void shouldExecuteChangeWorkForceFocusInCityAt() {
        game.changeWorkForceFocusInCityAt(fakeCityPosition, GameConstants.foodFocus);
    }

    @Test
    public void shouldExecuteChangeProductionInCityAt() {
        game.changeProductionInCityAt(fakeCityPosition, GameConstants.SETTLER);
    }

    @Test
    public void shouldExecutePerformUnitActionAt() {
        game.performUnitActionAt(fakeCityPosition);
    }


    @Test
    public void shouldExecuteSetTileFocus() {
        game.setTileFocus(fakeCityPosition);
    }

    @Test
    public void shouldReturnCity() {
        assertThat(game.getCityAt(new Position(3,2)), is(notNullValue()));
        CityProxy city = (CityProxy) game.getCityAt(new Position(3,2));
        System.out.println("ID is: " + city.getId());
    }

    @Test
    public void shouldReturnUnit() {
        assertThat(game.getUnitAt(new Position(2,0)), is(notNullValue()));
        UnitProxy unit = (UnitProxy) game.getUnitAt(new Position(2,0));
        System.out.println("ID is: " + unit.getId());
    }

    @Test
    public void shouldReturnTile() {
        assertThat(game.getTileAt(new Position(4,6)), is(notNullValue()));
        TileProxy tile = (TileProxy) game.getTileAt(new Position(4,6));
        System.out.println("ID is: " + tile.getId());
    }

    private class NullObserver implements GameObserver {
        @Override
        public void worldChangedAt(Position pos) {}

        @Override
        public void turnEnds(Player nextPlayer, int age) {}

        @Override
        public void tileFocusChangedAt(Position position) {}

        @Override
        public void cityWorkFocusChanges(String focus) {}

        @Override
        public void cityProductionChanged(String unitType) {}
    }
}
