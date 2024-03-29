package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestUnitBrokerClient {
    Unit unit;

    @BeforeEach
    public void setup() {
        Unit servant = new StubUnitBrokerClient();

        Invoker invoker = new HotCivUnitInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        unit = new UnitProxy(requestor);
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
