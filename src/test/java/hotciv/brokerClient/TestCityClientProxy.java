package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.stub.StubGameBrokerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCityClientProxy {

    City city;
    Game game;

    @BeforeEach
    public void setup() {
        Invoker invoker = new HotCivRootInvoker(new StubGameBrokerClient());

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);

        //Get test city from game Proxy
        city = game.getCityAt(new Position(3,2));
    }

    @Test
    public void shouldHaveOwner() {
        assertThat(city.getOwner(), is(Player.YELLOW));
    }

    @Test
    public void shouldHaveSize() {
        assertThat(city.getSize(), is(69));
    }

    @Test
    public void shouldHaveTreasury() {
        assertThat(city.getTreasury(), is(420));
    }

    @Test
    public void shouldHaveProduction() {
        assertThat(city.getProduction(), is("FISK"));
    }

    @Test
    public void shouldHaveFocus() {
        assertThat(city.getWorkforceFocus(), is("RØDE_PØLSER"));
    }

}