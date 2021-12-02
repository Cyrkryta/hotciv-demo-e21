package hotciv.brokerClient;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.variants.factories.SemiCivFactory;
import hotciv.view.CivDrawing;
import hotciv.view.tool.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivClient {

    public static void main(String[] args) {
        String host = args[0];
        System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host: " + host + ") ===");
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(host, 37321);

        Requestor requestor = new StandardJSONRequestor(crh);
        Requestor r = new StandardJSONRequestor(crh);

        Game game = new GameProxy(r);

        DrawingEditor editor =
                new MiniDrawApplication( "SemiCiv Demonstration",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Execute different command to examine the functional SemiCiv game.");

        editor.setTool(new CompositionTool(editor, game));
    }

}
