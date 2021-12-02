package hotciv.brokerClient;

import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.stub.StubGameBrokerClient;
import hotciv.variants.factories.SemiCivFactory;

public class HotcivServerMainSocket {
    private static Thread daemon;

    public static void main(String[] args) throws Exception {
        new HotcivServerMainSocket(args[0]);
    }

    public HotcivServerMainSocket(String host) {
        System.out.println("=== HotCiv server (Socket) (host: " + host + ") ===");
        int port = 37321;

        Game game = new GameImpl(new SemiCivFactory());
        Invoker invoker = new HotCivRootInvoker(game);

        // Configure a socket based server request handler.
        SocketServerRequestHandler ssrh = new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);

        ssrh.start();
    }
}
