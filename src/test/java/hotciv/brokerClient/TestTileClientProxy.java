package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTileClientProxy {

    Tile tile;

    @BeforeEach
    public void setup() {
        Tile servant = new stubTileBrokerClient();

        Invoker invoker = new HotCivTileInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        tile = new TileProxy(requestor);
    }

    @Test
    public void shouldHaveTypeString() {
        assertThat(tile.getTypeString(),is("GHETTO"));
    }
}
