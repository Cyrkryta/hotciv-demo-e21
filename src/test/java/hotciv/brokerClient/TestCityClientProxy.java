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

public class TestCityClientProxy {

    City city;

    @BeforeEach
    public void setup() {
        City servant = new stubCityBrokerClient();

        Invoker invoker = new HotCivCityInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        city = new CityProxy(requestor);
    }

    @Test
    public void shouldHaveOwner() {
        assertThat(city.getOwner(), is(Player.YELLOW));
    }

    @Test
    public void shouldHaveSize() {
        assertThat(city.getSize(), is(69));
    }

}