package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;

import hotciv.stub.StubGameBrokerClient;
import org.junit.Before;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerClient {

    Game game;

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
