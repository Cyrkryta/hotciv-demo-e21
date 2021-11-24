package hotciv.brokerClient;

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
    Position from = new Position(3,3);
    Position to = new Position(3,4);


    @BeforeEach
    public void setup() {
        Game servant = new StubGameBrokerClient();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivGameInvoker(servant);

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
        System.out.println(" -> Game age              : " + game.getAge());
        System.out.println(" -> Game winner           : " + game.getWinner());
        System.out.println(" -> Game PlayerInTurn     : " + game.getPlayerInTurn());
        System.out.println(" -> Game move (3,3)-(3,4) : " + game.moveUnit(from, to));
        game.endOfTurn();
        System.out.println(" -> Now PlayerInTurn after endOfTurn: " + game.getPlayerInTurn());
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
