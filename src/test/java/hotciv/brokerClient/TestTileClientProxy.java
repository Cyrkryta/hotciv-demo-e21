package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.stub.StubGameBrokerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTileClientProxy {
    Game game;

    @BeforeEach
    public void setup() {
        Invoker invoker = new HotCivRootInvoker(new StubGameBrokerClient());

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);

    }

    @Test
    public void shouldHaveTypePlainsAt11() {
        Tile tile = game.getTileAt(new Position(1,1));
        assertThat(tile.getTypeString(),is(GameConstants.HILLS));
    }

    @Test
    public void shouldHaveTypePlainsAt12() {
        Tile tile = game.getTileAt(new Position(1,2));
        assertThat(tile.getTypeString(),is(GameConstants.FOREST));
    }

    @Test
    public void shouldHaveTypePlainsAt13() {
        Tile tile = game.getTileAt(new Position(1,3));
        assertThat(tile.getTypeString(),is(GameConstants.MOUNTAINS));
    }

    @Test
    public void shouldHaveTypePlainsAt14() {
        Tile tile = game.getTileAt(new Position(1,4));
        assertThat(tile.getTypeString(),is(GameConstants.OCEANS));
    }


    @Test
    public void shouldHaveTypePlainsAt44() {
        Tile tile = game.getTileAt(new Position(4,4));
        assertThat(tile.getTypeString(),is(GameConstants.PLAINS));
    }

    @Test
    public void shouldHaveTypeGhettoAt1515() {
        Tile tile = game.getTileAt(new Position(15,15));
        assertThat(tile.getTypeString(),is("GHETTO"));
    }
}
