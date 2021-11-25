package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.Position;

import java.io.IOException;

public class HotcivManualClientTest {

    Position from = new Position(2,0);
    Position to = new Position(3,3);

    public static void main(String[] args) {
        new HotcivManualClientTest(args[0]);
    }

    public HotcivManualClientTest(String host) {
        System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host: " + host + ") ===");
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(host, 37321);
        Requestor requestor = new StandardJSONRequestor(crh);
        testSimpleMethods(new GameProxy(requestor));
    }

    private void testSimpleMethods(Game game) {
        System.out.println("=== Testing simple methods ===");
        System.out.println(" -> Game age    " + game.getAge());
        System.out.println(" -> Game winner " + game.getWinner());
        System.out.println(" -> Game PIT    " + game.getPlayerInTurn());
        System.out.println(" -> Game move   " + game.moveUnit(from, to));
        game.endOfTurn();
        System.out.println(" -> Now PIT after endOfTurn " + game.getPlayerInTurn());
    }
}
