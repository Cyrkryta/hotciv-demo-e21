package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.stub.StubGameBrokerClient;
import hotciv.stub.StubServants.StubUnitServant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestUnitBrokerClient {
    Unit unit;
    Game game;

    @BeforeEach
    public void setup() {
        Invoker invoker = new HotCivRootInvoker(new StubGameBrokerClient());

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);

        //Get Test unit from game proxy
        unit = game.getUnitAt(new Position(2,0));
    }

    @Test
    public void shouldGetTypeString() {
        assertThat(unit.getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void shouldGetOwner() {
        assertThat(unit.getOwner(), is(Player.GREEN));
    }

    @Test
    public void shouldGetMoveCount() {
        assertThat(unit.getMoveCount(), is(43));
    }

    @Test
    public void shouldGetDefensiveStrength() {
        assertThat(unit.getDefensiveStrength(), is(112));
    }

    @Test
    public void shouldGetAttackingStrength() {
        assertThat(unit.getAttackingStrength(), is(79));
    }
}
