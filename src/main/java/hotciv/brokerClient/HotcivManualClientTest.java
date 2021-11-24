package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.ipc.socket.SocketClientRequestHandler;

public class HotcivManualClientTest {
    public static void main(String[] args) throws Exception {
        new HotcivManualClientTest(args[0]);
    }

    public HotcivManualClientTest(String host) {
        System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host: " + host + ") ===");

        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(host, 37321);
    }
}
